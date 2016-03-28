package cz.zcu.kiv.jop.generator;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.generator.CustomValueGenerator;
import cz.zcu.kiv.jop.annotation.generator.ValueGeneratorAnnotation;
import cz.zcu.kiv.jop.annotation.parameters.CustomParameters;
import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.util.AnnotationUtils;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Implementation of value generator invoker (interlayer between another layers and value
 * generators) which analyzes the annotations of property and then choose and invoke some value
 * generator. After invocation is returned the result of chosen value generator. There is also
 * support of multiple invocations of same value generator.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ValueGeneratorInvokerImpl implements ValueGeneratorInvoker {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(ValueGeneratorInvokerImpl.class);

  /** Constant for name of invocable method by this invoker. */
  protected static final String INVOCABLE_METHOD_NAME = "getValue";

  /**
   * {@inheritDoc}
   */
  public boolean isAnnotationPresent(Property<?> property) {
    return AnnotationUtils.isAnnotatedAnnotationPresent(property, ValueGeneratorAnnotation.class);
  }

  /**
   * {@inheritDoc}
   */
  public Object getValue(Property<?> property) throws ValueGeneratorException {
    return getValue(property, 1)[0]; // returns only one generated value
  }

  /**
   * {@inheritDoc}
   */
  public Object[] getValue(Property<?> property, int count) throws ValueGeneratorException {
    if (property == null) {
      throw new ValueGeneratorException("Property cannot be null");
    }

    if (count <= 0) {
      throw new ValueGeneratorException("Number of invocations has to be greater than 0");
    }

    Annotation[] annotations = AnnotationUtils.getAnnotatedAnnotations(property, ValueGeneratorAnnotation.class);
    if (annotations == null || annotations.length == 0) {
      throw new ValueGeneratorException("No such value generator annotation");
    }

    if (annotations.length > 1) {
      throw new ValueGeneratorException("Too many value generator annotations");
    }

    Annotation params = annotations[0];
    ValueGenerator<?, Annotation> valueGenerator = null;
    if (AnnotationUtils.isAnnotatedAnnotation(params, CustomAnnotation.class)) {
      // custom value generator handling
      valueGenerator = getCustomValueGenerator(params);
      params = getCustomValueGeneratorParams(property, valueGenerator.getClass(), params.annotationType());
    }
    else {
      // value generators bound in value generator factory
      valueGenerator = getBoundValueGenerator(params);
    }

    // no value generator
    if (valueGenerator == null) {
      throw new ValueGeneratorException("No such value generator");
    }

    logger.debug("Invoking value generator: " + valueGenerator.getClass().getName() + "; with parameters: " + params + "; for property: " + property + "; for "
        + count + " time(s)");

    Object[] values = new Object[count];
    for (int i = 0; i < count; i++) {
      values[i] = valueGenerator.getValue(params);
    }

    return values;
  }

  /**
   * Returns instance of value generator for given custom annotation. There is supported only
   * {@link CustomValueGenerator} annotation.
   *
   * @param annotation the custom value generator annotation.
   * @return Instance of custom value generator.
   * @throws ValueGeneratorException If the given annotation isn't supported or if some problem
   *           occurs during custom value generator creation.
   */
  @SuppressWarnings("unchecked")
  protected ValueGenerator<?, Annotation> getCustomValueGenerator(Annotation annotation) throws ValueGeneratorException {
    if (!(annotation instanceof CustomValueGenerator)) {
      throw new ValueGeneratorException("Unsupported custom annotation: " + annotation.annotationType().getName());
    }

    Class<? extends ValueGenerator<?, ?>> customValueGenerator = ((CustomValueGenerator)annotation).value();
    try {
      return (ValueGenerator<?, Annotation>)valueGeneratorFactory.createInstance(customValueGenerator);
    }
    catch (FactoryException exc) {
      throw new ValueGeneratorException(exc);
    }
  }

  /**
   * Analyzes the annotations of given property and returns annotation for custom parameters of
   * custom value generator.
   *
   * @param property the property for which will be returned custom parameters.
   * @param customValueGenerator the class type of custom value generator.
   * @param customAnnotation the class type of custom value generator annotation.
   * @return The annotation for custom parameters of value generator.
   * @throws ValueGeneratorException If custom parameters for custom value generator cannot be
   *           obtained.
   */
  protected Annotation getCustomValueGeneratorParams(Property<?> property, Class<?> customValueGenerator, Class<?> customAnnotation) throws ValueGeneratorException {
    // empty parameters
    if (ReflectionUtils.getMethod(customValueGenerator, INVOCABLE_METHOD_NAME, EmptyParameters.class) != null) {
      return AnnotationUtils.getAnnotationProxy(EmptyParameters.class, null);
    }

    // custom parameters

    Annotation[] customParameters = AnnotationUtils.getAnnotatedAnnotations(property, CustomParameters.class);
    if (customParameters.length == 0) {
      throw new ValueGeneratorException("Missing custom parameters for value generator: " + customValueGenerator.getName());
    }

    Annotation customParams = null;

    // we have to find correct parameters
    if (customParameters.length > 1) {
      for (int i = 0; i < customParameters.length; i++) {
        // find matching custom parameters via CustomParameters#value()
        CustomParameters customParamsMarker = customParameters[i].annotationType().getAnnotation(CustomParameters.class);
        Class<? extends Annotation> target = customParamsMarker.value();
        if (target.equals(customAnnotation)) {
          if (customParams != null) { // too many annotations
            customParams = null;
            break;
          }
          customParams = customParameters[i];
        }
      }

      // no matching parameters not found
      if (customParams == null) {
        for (int i = 0; i < customParameters.length; i++) {
          if (ReflectionUtils.getMethod(customValueGenerator, INVOCABLE_METHOD_NAME, customParameters[i].annotationType()) != null) {
            // we can easily break in this point because we cannot have same annotation multiple times
            return customParameters[i];
          }
        }
      }
    }
    else {
      customParams = customParameters[0];
    }

    // checks compatibility of custom parameters
    Class<?> customParamsType = customParams.annotationType();
    if (ReflectionUtils.getMethod(customValueGenerator, INVOCABLE_METHOD_NAME, customParamsType) != null) {
      return customParams;
    }
    else {
      throw new ValueGeneratorException("Incompatible custom parameters: " + customParamsType.getName() + " for value generator: " + customValueGenerator.getName());
    }
  }

  /**
   * Returns instance of value generator bound for given annotation in {@link ValueGeneratorFactory}
   * .
   *
   * @param annotation the parameters annotation for which will be returned the value generator.
   * @return Bound value generator for given annotation.
   * @throws ValueGeneratorException If some error occurs during obtaining of bound value generator.
   */
  protected ValueGenerator<?, Annotation> getBoundValueGenerator(Annotation annotation) throws ValueGeneratorException {
    try {
      return valueGeneratorFactory.createInstance(annotation);
    }
    catch (FactoryException exc) {
      throw new ValueGeneratorException(exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Binding factory for creating/providing instances of value generators. */
  protected ValueGeneratorFactory valueGeneratorFactory;

  /**
   * Sets (injects) binding factory for creating/providing instances of value generators.
   *
   * @param valueGeneratorFactory the value generator factory to set (inject).
   */
  @Inject
  public final void setValueGeneratorFactory(ValueGeneratorFactory valueGeneratorFactory) {
    this.valueGeneratorFactory = valueGeneratorFactory;
  }
}
