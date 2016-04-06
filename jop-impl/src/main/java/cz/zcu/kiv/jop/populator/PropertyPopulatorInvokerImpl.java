package cz.zcu.kiv.jop.populator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.parameters.CustomParameters;
import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;
import cz.zcu.kiv.jop.annotation.populator.CustomPropertyPopulator;
import cz.zcu.kiv.jop.annotation.populator.PropertyPopulatorAnnotation;
import cz.zcu.kiv.jop.annotation.populator.PropertyPopulatorsOrder;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.ioc.NamedScopes;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.PropertyException;
import cz.zcu.kiv.jop.property.VirtualProperty;
import cz.zcu.kiv.jop.util.AnnotationUtils;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Implementation of property populator invoker (interlayer between the property populators and
 * another parts of this library including chained property populators).
 * <p>
 * Implementation of the {@link #populate} method analyzes the annotations of given property and
 * then chooses and invokes some property populator which populates the value of given property.
 * <p>
 * Implementation of the {@link #invokeNextPopulator} method analyzes the annotations of given
 * property and then chooses and invokes next (chained) property populator and then returns the
 * value of invocation.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class PropertyPopulatorInvokerImpl implements PropertyPopulatorInvoker {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(PropertyPopulatorInvokerImpl.class);

  /** Constant for class type of {@link Property}. */
  @SuppressWarnings("unchecked")
  protected static final Class<Property<?>> PROPERTY_CLASS = (Class<Property<?>>)(Class<?>)Property.class;

  /** Constant for name of invocable method by this invoker. */
  protected static final String INVOCABLE_METHOD_NAME = "populate";

  /**
   * {@inheritDoc}
   */
  public boolean isAnnotationPresent(Property<?> property) {
    return AnnotationUtils.isAnnotatedAnnotationPresent(property, PropertyPopulatorAnnotation.class);
  }

  /**
   * {@inheritDoc}
   */
  public void populate(Property<?> property, Object owner) throws PropertyPopulatorException {
    if (property == null) {
      throw new PropertyPopulatorException("Property cannot be null");
    }

    List<Annotation> annotations = getOrderedAnnotations(property);
    if (annotations == null || annotations.isEmpty()) {
      String populatorClassName = defaultPropertyPopulator.getClass().getName();
      if (defaultPropertyPopulator.supports(property)) {
        logger.debug("Invoking default property populator: " + populatorClassName + "; for property: " + property);
        defaultPropertyPopulator.populate(property, owner, null); // no parameters
      }
      else {
        logger.warn("Default property populator " + populatorClassName + " doesn't support property (it won't be populated): " + property);
      }
      return; // default property populator invoked
    }

    Annotation params = annotations.get(0);
    PropertyPopulator<Annotation> propertyPopulator = null;
    if (AnnotationUtils.isAnnotatedAnnotation(params, CustomAnnotation.class)) {
      // custom property populator handling
      propertyPopulator = getCustomPropertyPopulator(params);
      params = getCustomPropertyPopulatorParams(property, propertyPopulator.getClass(), params.annotationType());
    }
    else {
      // property populator bound in property populator factory
      propertyPopulator = getBoundPropertyPopulator(params);
    }

    // no property populator
    if (propertyPopulator == null) {
      throw new PropertyPopulatorException("No such property populator");
    }

    // class name of property populator
    String populatorClassName = propertyPopulator.getClass().getName();

    // check whatever the populator supports the given property
    if (!propertyPopulator.supports(property)) {
      throw new PropertyPopulatorException("Property populator " + populatorClassName + " doesn't support property: " + property);
    }

    logger.debug("Invoking property populator: " + populatorClassName + "; with parameters: " + params + "; for property: " + property);

    propertyPopulator.populate(property, owner, params);
  }

  /**
   * Returns instance of property populator for given custom annotation. There is supported only
   * {@link CustomPropertyPopulator} annotation.
   *
   * @param annotation the custom property populator annotation.
   * @return Instance of custom property populator.
   * @throws PropertyPopulatorException If the given annotation isn't supported or if some problem
   *           occurs during custom property populator creation.
   */
  @SuppressWarnings("unchecked")
  protected PropertyPopulator<Annotation> getCustomPropertyPopulator(Annotation annotation) throws PropertyPopulatorException {
    if (!(annotation instanceof CustomPropertyPopulator)) {
      throw new PropertyPopulatorException("Unsupported custom annotation: " + annotation.annotationType().getName());
    }

    Class<? extends PropertyPopulator<?>> customPropertyPopulator = ((CustomPropertyPopulator)annotation).value();
    try {
      return (PropertyPopulator<Annotation>)propertyPopulatorFactory.createInstance(customPropertyPopulator);
    }
    catch (FactoryException exc) {
      throw new PropertyPopulatorException(exc);
    }
  }

  /**
   * Analyzes the annotations of given property and returns annotation for custom parameters of
   * custom property populator.
   *
   * @param property the property for which will be returned custom parameters.
   * @param customPropertyPopulator the class type of custom property populator.
   * @param customAnnotation the class type of custom property populator annotation.
   * @return The annotation for custom parameters of property populator.
   * @throws PropertyPopulatorException If custom parameters for custom property populator cannot be
   *           obtained.
   */
  protected Annotation getCustomPropertyPopulatorParams(Property<?> property, Class<?> customPropertyPopulator, Class<?> customAnnotation) throws PropertyPopulatorException {
    // empty parameters

    if (ReflectionUtils.getMethod(customPropertyPopulator, INVOCABLE_METHOD_NAME, EmptyParameters.class) != null) {
      return AnnotationUtils.getAnnotationProxy(EmptyParameters.class, null);
    }

    // custom parameters

    Annotation[] customParameters = AnnotationUtils.getAnnotatedAnnotations(property, CustomParameters.class);
    if (customParameters.length == 0) {
      throw new PropertyPopulatorException("Missing custom parameters for property populator: " + customPropertyPopulator.getName());
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

      // no matching parameters found
      if (customParams == null) {
        for (int i = 0; i < customParameters.length; i++) {
          if (ReflectionUtils.getMethod(customPropertyPopulator, INVOCABLE_METHOD_NAME, PROPERTY_CLASS, Object.class, customParameters[i].annotationType()) != null) {
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
    if (ReflectionUtils.getMethod(customPropertyPopulator, INVOCABLE_METHOD_NAME, PROPERTY_CLASS, Object.class, customParamsType) != null) {
      return customParams;
    }
    else {
      throw new PropertyPopulatorException("Incompatible custom parameters: " + customParamsType.getName() + " for property populator: " + customPropertyPopulator.getName());
    }
  }

  /**
   * Returns instance of property populator bound for given annotation in
   * {@link PropertyPopulatorFactory}.
   *
   * @param annotation the parameters annotation for which will be returned the property populator.
   * @return Bound property populator for given annotation.
   * @throws PropertyPopulatorException If some error occurs during obtaining of bound property
   *           populator.
   */
  protected PropertyPopulator<Annotation> getBoundPropertyPopulator(Annotation annotation) throws PropertyPopulatorException {
    try {
      return propertyPopulatorFactory.createInstance(annotation);
    }
    catch (FactoryException exc) {
      throw new PropertyPopulatorException(exc);
    }
  }

  /**
   * {@inheritDoc}
   */
  public <T> T invokeNextPopulator(Property<?> property, Class<T> targetClassType) throws PropertyPopulatorException {
    if (property == null) {
      throw new PropertyPopulatorException("Property cannot be null");
    }

    List<Annotation> propertyAnnotations = getOrderedAnnotations(property);
    if (propertyAnnotations == null || propertyAnnotations.isEmpty()) {
      throw new PropertyPopulatorException("Property " + property + " has no more property populator annotations");
    }

    // remove actual property populator annotation
    propertyAnnotations.remove(0);

    // add rest of annotations
    Annotation[] annotations = property.getAnnotations();
    for (Annotation annotation : annotations) {
      // do not add annotations for property populators
      if (AnnotationUtils.isAnnotatedAnnotation(annotation, PropertyPopulatorAnnotation.class)) {
        continue;
      }

      // do not add annotation for populators order
      if (annotation.annotationType() == PropertyPopulatorsOrder.class) {
        continue;
      }

      propertyAnnotations.add(annotation);
    }

    // prepare virtual property
    VirtualProperty<T> virtualProperty = new VirtualProperty<T>(property.getName(), targetClassType, propertyAnnotations);

    // invoke next populator
    populate(virtualProperty, virtualProperty);

    try {
      // return populated value
      return virtualProperty.getGetter().get(null);
    }
    catch (PropertyException exc) {
      throw new PropertyPopulatorException("Cannot obtain populated value", exc);
    }
  }

  /**
   * Returns list of ordered property populator annotations for given property.
   *
   * @param property the property which property populator annotations will be analyzed and ordered.
   * @return List of ordered property populator annotations.
   * @throws PropertyPopulatorException If annotation for property populators order contains invalid
   *           annotations.
   */
  protected static List<Annotation> getOrderedAnnotations(Property<?> property) throws PropertyPopulatorException {
    Annotation[] annotations = AnnotationUtils.getAnnotatedAnnotations(property, PropertyPopulatorAnnotation.class);
    if (annotations == null || annotations.length == 0) {
      return new ArrayList<Annotation>(); // no annotations
    }

    // check order annotation
    PropertyPopulatorsOrder order = property.getAnnotation(PropertyPopulatorsOrder.class);
    if (order == null) {
      return new ArrayList<Annotation>(Arrays.asList(annotations)); // no specific order
    }

    // prepare map for mapping of ordered annotations
    Map<Class<? extends Annotation>, Annotation> orderedAnnotations = new LinkedHashMap<Class<? extends Annotation>, Annotation>();

    logger.debug("Ordering populator annotations for property: " + property);

    // check order of annotations
    Class<? extends Annotation>[] annotationsOrder = order.value();
    for (int i = 0; i < annotationsOrder.length; i++) {
      Class<? extends Annotation> annotation = annotationsOrder[i];
      if (!AnnotationUtils.isAnnotatedAnnotation(annotation, PropertyPopulatorAnnotation.class)) {
        throw new PropertyPopulatorException("Order of property populators contains invalid annotation: " + annotation.getName());
      }

      if (orderedAnnotations.get(annotation) != null) {
        throw new PropertyPopulatorException("Order of property populators contains duplicate annotation: " + annotation.getName());
      }

      Annotation propertyAnnotation = null;
      for (int j = 0; j < annotations.length; j++) {
        if (annotations[j].annotationType() == annotation) {
          propertyAnnotation = annotations[j];
        }
      }

      if (propertyAnnotation == null) {
        throw new PropertyPopulatorException("Order of property populators contains annotation which is not present on property: " + annotation.getName());
      }

      orderedAnnotations.put(annotation, propertyAnnotation);
    }

    if (orderedAnnotations.size() < annotations.length) {
      logger.warn("Order of property populators contains less annotations than property: " + property);
    }

    return new ArrayList<Annotation>(orderedAnnotations.values());
  }

  //----- Injection part ------------------------------------------------------

  /** Binding factory for creating/providing instances of property populators. */
  protected PropertyPopulatorFactory propertyPopulatorFactory;
  /**
   * Default implementation of property populator (default populator which should invoke value
   * generator).
   */
  protected PropertyPopulator<?> defaultPropertyPopulator;

  /**
   * Sets (injects) binding factory for creating/providing instances of populating strategies.
   *
   * @param propertyPopulatorFactory the property populator factory to set (inject).
   */
  @Inject
  public final void setPropertyPopulatorFactory(PropertyPopulatorFactory propertyPopulatorFactory) {
    this.propertyPopulatorFactory = propertyPopulatorFactory;
  }

  /**
   * Sets (injects) default implementation of property populator.
   *
   * @param defaultPropertyPopulator default strategy to set (inject).
   */
  @Inject
  public final void setDefaultPropertyPopulator(@Named(NamedScopes.DEFAULT_IMPL) PropertyPopulator<?> defaultPropertyPopulator) {
    this.defaultPropertyPopulator = defaultPropertyPopulator;
  }
}
