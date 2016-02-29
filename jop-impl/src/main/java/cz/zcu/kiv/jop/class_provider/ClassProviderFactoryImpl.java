package cz.zcu.kiv.jop.class_provider;

import java.lang.annotation.Annotation;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.class_provider.ClassProviderAnnotation;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClass;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClassForName;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClass;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForName;
import cz.zcu.kiv.jop.factory.AbstractBindingFactory;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.factory.binding.BindingException;
import cz.zcu.kiv.jop.util.AnnotationUtils;

/**
 * Implementation of {@link ClassProviderFactory} which extends the abstract implementation of
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
public class ClassProviderFactoryImpl extends AbstractBindingFactory<ClassProvider<?>> implements ClassProviderFactory {

  /**
   * Check conditions for given annotation type. The annotation type cannot be <code>null</code> and
   * has to be marked by annotation {@link ClassProviderAnnotation}. This method also checks
   * whatever the given annotation is not marked by annotation {@link CustomAnnotation} because the
   * binding of annotation for custom class provider is not allowed.
   *
   * @param annotation the annotation type to check.
   * @throws BindingException if entered annotation isn't valid.
   */
  @Override
  protected void checkAnnotation(Class<? extends Annotation> annotation) throws BindingException {
    super.checkAnnotation(annotation);

    if (!AnnotationUtils.isAnnotatedAnnotation(annotation, ClassProviderAnnotation.class)) {
      throw new BindingException("Annotation has to be marked by annotation: " + ClassProviderAnnotation.class.getName());
    }

    if (AnnotationUtils.isAnnotatedAnnotation(annotation, CustomAnnotation.class)) {
      throw new BindingException("Cannot create binding for custom annotation");
    }
  }

  /**
   * Configures default binding between annotations for class providers and class types of class
   * providers. This method is final and for customization of factory can be used method
   * {@link #customConfigure()} in which may be some bindings re-binded.
   *
   * @throws BindingException If some error occurs during configuration of binding factory.
   */
  @Override
  protected final void configure() throws BindingException {
    bind(RandomClass.class).to(RandomClassProvider.class);
    bind(RandomClassForName.class).to(RandomClassForNameProvider.class);
    bind(TargetClass.class).to(TargetClassProvider.class);
    bind(TargetClassForName.class).to(TargetClassForNameProvider.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  // actually checked
  @SuppressWarnings("unchecked")
  public <A extends Annotation> ClassProvider<A> createInstance(A annotation) throws FactoryException {
    ClassProvider<?> classProvider = super.createInstance(annotation);
    try {
      classProvider.getClass().getDeclaredMethod("get", annotation.annotationType());
    }
    catch (Exception exc) {
      throw new FactoryException("Invalid class provider was bound for annotation: " + annotation.annotationType().getName());
    }

    return (ClassProvider<A>)classProvider;
  }
}
