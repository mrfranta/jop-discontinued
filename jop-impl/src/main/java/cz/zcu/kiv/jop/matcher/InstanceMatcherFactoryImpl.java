package cz.zcu.kiv.jop.matcher;

import java.lang.annotation.Annotation;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.matcher.InstanceMatcherAnnotation;
import cz.zcu.kiv.jop.factory.AbstractBindingFactory;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.factory.binding.BindingException;
import cz.zcu.kiv.jop.util.AnnotationUtils;

/**
 * Implementation of {@link InstanceMatcherFactory} which extends the abstract implementation of
 * binding factory ({@link AbstractBindingFactory}). This factory may be extended for new bindings
 * or for overriding of default bindings. That may be done in method {@link #customConfigure()}.
 * <p>
 * This factory uses extended method {@link #checkAnnotation(Class)} for checking of annotations for
 * which are created bindings. This method is not final but it's not recommended to override it
 * completely but on another hand the checking of annotations in this method may be extended.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class InstanceMatcherFactoryImpl extends AbstractBindingFactory<InstanceMatcher<?>> implements InstanceMatcherFactory {

  /**
   * Check conditions for given annotation type. The annotation type cannot be <code>null</code> and
   * has to be marked by annotation {@link InstanceMatcherAnnotation}. This method also checks
   * whatever the given annotation is not marked by annotation {@link CustomAnnotation} because the
   * binding of annotation for custom instance matcher is not allowed.
   *
   * @param annotation the annotation type to check.
   * @throws BindingException If given annotation isn't valid.
   */
  @Override
  protected void checkAnnotation(Class<? extends Annotation> annotation) throws BindingException {
    super.checkAnnotation(annotation);

    if (!AnnotationUtils.isAnnotatedAnnotation(annotation, InstanceMatcherAnnotation.class)) {
      throw new BindingException("Annotation has to be marked by annotation: " + InstanceMatcherAnnotation.class.getName());
    }

    if (AnnotationUtils.isAnnotatedAnnotation(annotation, CustomAnnotation.class)) {
      throw new BindingException("Cannot create binding for custom annotation");
    }
  }

  /**
   * Configures default binding between annotations for instance matchers and class types of
   * instance matchers. This method is final and for customization of factory can be used method
   * {@link #customConfigure()} in which may be some bindings re-binded.
   *
   * @throws BindingException If some error occurs during configuration of binding factory.
   */
  @Override
  protected final void configure() throws BindingException {
    // No default bindings
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <A extends Annotation> InstanceMatcher<A> createInstance(A annotation) throws FactoryException {
    InstanceMatcher<?> instanceMatcher = super.createInstance(annotation);
    try {
      instanceMatcher.getClass().getMethod("matches", Object.class, annotation.annotationType());
    }
    catch (Exception exc) {
      throw new FactoryException("Invalid instance matcher was bound for annotation: " + annotation.annotationType().getName());
    }

    return (InstanceMatcher<A>)instanceMatcher;
  }
}
