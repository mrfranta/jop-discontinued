package cz.zcu.kiv.jop.class_provider;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.class_provider.ClassProviderAnnotation;
import cz.zcu.kiv.jop.annotation.class_provider.CustomClassProvider;
import cz.zcu.kiv.jop.annotation.parameters.CustomParameters;
import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.util.AnnotationUtils;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Implementation of class provider invoker (interlayer between another layers and class providers)
 * which analyzes the annotations of property and then choose and invoke some class provider. After
 * invocation is returned the result of chosen class provider. There is also support of multiple
 * invocations of same class provider.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ClassProviderInvokerImpl implements ClassProviderInvoker {

  /** Constant for name of invocable method by this invoker. */
  protected static final String INVOCABLE_METHOD_NAME = "get";

  /**
   * {@inheritDoc}
   */
  public boolean isAnnotationPresent(Property<?> property) {
    return AnnotationUtils.isAnnotatedAnnotationPresent(property, ClassProviderAnnotation.class);
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> get(Property<?> property) throws ClassProviderException {
    return get(property, 1)[0]; // returns only one provided class
  }

  /**
   * {@inheritDoc}
   */
  public Class<?>[] get(Property<?> property, int count) throws ClassProviderException {
    if (property == null) {
      throw new ClassProviderException("Property cannot be null");
    }

    if (count <= 0) {
      throw new ClassProviderException("Number of invocations has to be greater than 0");
    }

    Annotation[] annotations = AnnotationUtils.getAnnotatedAnnotations(property, ClassProviderAnnotation.class);
    if (annotations == null || annotations.length == 0) {
      throw new ClassProviderException("No such class provider annotation");
    }

    if (annotations.length > 1) {
      throw new ClassProviderException("Too many class provider annotations");
    }

    Annotation params = annotations[0];
    ClassProvider<Annotation> classProvider = null;
    if (AnnotationUtils.isAnnotatedAnnotation(params, CustomAnnotation.class)) {
      // custom class provider handling
      classProvider = getCustomClassProvider(params);
      params = getCustomClassProviderParams(property, classProvider.getClass(), params.annotationType());
    }
    else {
      // class providers bound in class provider factory
      classProvider = getBoundClassProvider(params);
    }

    Class<?>[] classes = new Class<?>[count];
    for (int i = 0; i < count; i++) {
      classes[i] = classProvider.get(params);
    }

    return classes;
  }

  /**
   * Returns instance of class provider for given custom annotation. There is supported only
   * {@link CustomClassProvider} annotation.
   *
   * @param annotation the custom class provider annotation.
   * @return Instance of custom class provider.
   * @throws ClassProviderException If the given annotation isn't supported or if some problem
   *           occurs during custom class provider creation.
   */
  @SuppressWarnings("unchecked")
  protected ClassProvider<Annotation> getCustomClassProvider(Annotation annotation) throws ClassProviderException {
    if (!(annotation instanceof CustomClassProvider)) {
      throw new ClassProviderException("Unsupported custom annotation: " + annotation.annotationType().getName());
    }

    Class<? extends ClassProvider<?>> customClassProvider = ((CustomClassProvider)annotation).value();
    try {
      return (ClassProvider<Annotation>)classProviderFactory.createInstance(customClassProvider);
    }
    catch (FactoryException exc) {
      throw new ClassProviderException(exc);
    }
  }

  /**
   * Analyzes the annotations of given property and returns annotation for custom parameters of
   * custom class provider.
   *
   * @param property the property for which will be returned custom parameters.
   * @param customClassProvider the class type of custom class provider.
   * @param customAnnotation the class type of custom class provider annotation.
   * @return The annotation for custom parameters of class provider.
   * @throws ClassProviderException If custom parameters for custom class provider cannot be
   *           obtained.
   */
  protected Annotation getCustomClassProviderParams(Property<?> property, Class<?> customClassProvider, Class<?> customAnnotation) throws ClassProviderException {
    // empty parameters
    if (ReflectionUtils.getMethod(customClassProvider, INVOCABLE_METHOD_NAME, EmptyParameters.class) != null) {
      return AnnotationUtils.getAnnotationProxy(EmptyParameters.class, null);
    }

    // custom parameters

    Annotation[] customParameters = AnnotationUtils.getAnnotatedAnnotations(property, CustomParameters.class);
    if (customParameters.length == 0) {
      throw new ClassProviderException("Missing custom parameters for class provider: " + customClassProvider.getName());
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
          if (ReflectionUtils.getMethod(customClassProvider, INVOCABLE_METHOD_NAME, customParameters[i].annotationType()) != null) {
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
    if (ReflectionUtils.getMethod(customClassProvider, INVOCABLE_METHOD_NAME, customParamsType) != null) {
      return customParams;
    }
    else {
      throw new ClassProviderException("Incompatible custom parameters: " + customParamsType.getName() + " for class provider: "
          + customClassProvider.getName());
    }
  }

  /**
   * Returns instance of class provider bound for given annotation in {@link ClassProviderFactory}.
   *
   * @param annotation the parameters annotation for which will be returned the class provider.
   * @return Bound class provider for given annotation.
   * @throws ClassProviderException If some error occurs during obtaining of bound class provider.
   */
  protected ClassProvider<Annotation> getBoundClassProvider(Annotation annotation) throws ClassProviderException {
    try {
      return classProviderFactory.createInstance(annotation);
    }
    catch (FactoryException exc) {
      throw new ClassProviderException(exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Binding factory for creating/providing instances of class providers. */
  protected ClassProviderFactory classProviderFactory;

  /**
   * Sets (injects) binding factory for creating/providing instances of class providers.
   *
   * @param classProviderFactory the class provider factory to set (inject).
   */
  @Inject
  public final void setClassProviderFactory(ClassProviderFactory classProviderFactory) {
    this.classProviderFactory = classProviderFactory;
  }
}
