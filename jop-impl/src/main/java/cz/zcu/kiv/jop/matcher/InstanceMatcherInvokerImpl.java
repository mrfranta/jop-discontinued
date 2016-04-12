package cz.zcu.kiv.jop.matcher;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.matcher.CustomInstanceMatcher;
import cz.zcu.kiv.jop.annotation.matcher.InstanceMatcherAnnotation;
import cz.zcu.kiv.jop.annotation.parameters.CustomParameters;
import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.util.AnnotationUtils;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Implementation of instance matcher invoker (interlayer between the instance matchers and another
 * parts of this library) which analyzes the annotations of given property and then should choose
 * some instance matcher. Then a chosen instance matcher is used for finding matching object from
 * all already populated (generated) objects.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class InstanceMatcherInvokerImpl implements InstanceMatcherInvoker {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(InstanceMatcherInvokerImpl.class);

  /** Constant for name of invocable method by this invoker. */
  protected static final String INVOCABLE_METHOD_NAME = "matches";

  /**
   * {@inheritDoc}
   */
  public boolean isAnnotationPresent(Property<?> property) {
    return AnnotationUtils.isAnnotatedAnnotationPresent(property, InstanceMatcherAnnotation.class);
  }

  /**
   * {@inheritDoc}
   */
  public Object match(Property<?> property, PopulatingContext context) throws InstanceMatcherException {
    if (property == null) {
      throw new InstanceMatcherException("Property cannot be null");
    }

    Annotation[] annotations = AnnotationUtils.getAnnotatedAnnotations(property, InstanceMatcherAnnotation.class);
    if (annotations == null || annotations.length == 0) {
      throw new InstanceMatcherException("No such instance matcher annotation");
    }

    if (annotations.length > 1) {
      throw new InstanceMatcherException("Too many instance matcher annotations");
    }

    Annotation params = annotations[0];
    InstanceMatcher<Annotation> instanceMatcher = null;
    if (AnnotationUtils.isAnnotatedAnnotation(params, CustomAnnotation.class)) {
      // custom instance matcher handling
      instanceMatcher = getCustomInstanceMatcher(params);
      params = getCustomInstanceMatcherParams(property, instanceMatcher.getClass(), params.annotationType());
    }
    else {
      // instance matchers bound in instance matcher factory
      instanceMatcher = getBoundInstanceMatcher(params);
    }

    // no class provider
    if (instanceMatcher == null) {
      throw new InstanceMatcherException("No such instance matcher");
    }

    logger.debug("Invoking instance matcher: " + instanceMatcher.getClass().getName() + "; with parameters: " + params + "; for property: " + property);

    for (Object obj : context.getPopulatedInstances(true)) {
      if (!instanceMatcher.supports(obj.getClass())) {
        continue;
      }

      if (instanceMatcher.matches(obj, params)) {
        return obj;
      }
    }

    return null;
  }

  /**
   * Returns instance of instance matcher for given custom annotation. There is supported only
   * {@link CustomInstanceMatcher} annotation.
   *
   * @param annotation the custom instance matcher annotation.
   * @return Instance of custom instance matcher.
   * @throws InstanceMatcherException If the given annotation isn't supported or if some problem
   *           occurs during custom instance matcher creation.
   */
  @SuppressWarnings("unchecked")
  protected InstanceMatcher<Annotation> getCustomInstanceMatcher(Annotation annotation) throws InstanceMatcherException {
    if (!(annotation instanceof CustomInstanceMatcher)) {
      throw new InstanceMatcherException("Unsupported custom annotation: " + annotation.annotationType().getName());
    }

    Class<? extends InstanceMatcher<?>> customInstanceMatcher = ((CustomInstanceMatcher)annotation).value();
    try {
      return (InstanceMatcher<Annotation>)instanceMatcherFactory.createInstance(customInstanceMatcher);
    }
    catch (FactoryException exc) {
      throw new InstanceMatcherException(exc);
    }
  }

  /**
   * Analyzes the annotations of given property and returns annotation for custom parameters of
   * custom instance matcher.
   *
   * @param property the property for which will be returned custom parameters.
   * @param customInstanceMatcher the class type of custom instance matcher.
   * @param customAnnotation the class type of custom instance matcher annotation.
   * @return The annotation for custom parameters of instance matcher.
   * @throws InstanceMatcherException If custom parameters for custom instance matcher cannot be
   *           obtained.
   */
  protected Annotation getCustomInstanceMatcherParams(Property<?> property, Class<?> customInstanceMatcher, Class<?> customAnnotation) throws InstanceMatcherException {
    // empty parameters

    if (ReflectionUtils.getMethod(customInstanceMatcher, INVOCABLE_METHOD_NAME, Object.class, EmptyParameters.class) != null) {
      return AnnotationUtils.getAnnotationProxy(EmptyParameters.class, null);
    }

    // custom parameters

    Annotation[] customParameters = AnnotationUtils.getAnnotatedAnnotations(property, CustomParameters.class);
    if (customParameters.length == 0) {
      throw new InstanceMatcherException("Missing custom parameters for instance matcher: " + customInstanceMatcher.getName());
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
          if (ReflectionUtils.getMethod(customInstanceMatcher, INVOCABLE_METHOD_NAME, Object.class, customParameters[i].annotationType()) != null) {
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
    if (ReflectionUtils.getMethod(customInstanceMatcher, INVOCABLE_METHOD_NAME, Object.class, customParamsType) != null) {
      return customParams;
    }
    else {
      throw new InstanceMatcherException("Incompatible custom parameters: " + customParamsType.getName() + " for instance matcher: " + customInstanceMatcher.getName());
    }
  }

  /**
   * Returns instance of instance matcher bound for given annotation in
   * {@link InstanceMatcherFactory}.
   *
   * @param annotation the parameters annotation for which will be returned the instance matcher.
   * @return Bound instance matcher for given annotation.
   * @throws InstanceMatcherException If some error occurs during obtaining of bound instance
   *           matcher.
   */
  protected InstanceMatcher<Annotation> getBoundInstanceMatcher(Annotation annotation) throws InstanceMatcherException {
    try {
      return instanceMatcherFactory.createInstance(annotation);
    }
    catch (FactoryException exc) {
      throw new InstanceMatcherException(exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Binding factory for creating/providing instances of instance matchers. */
  protected InstanceMatcherFactory instanceMatcherFactory;

  /**
   * Sets (injects) binding factory for creating/providing instances of instance matchers.
   *
   * @param instanceMatcherFactory the instance matcher factory to set (inject).
   */
  @Inject
  public final void setInstanceMatcherFactory(InstanceMatcherFactory instanceMatcherFactory) {
    this.instanceMatcherFactory = instanceMatcherFactory;
  }
}
