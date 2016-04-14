package cz.zcu.kiv.jop.populator;

import java.lang.annotation.Annotation;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.populator.ArrayValue;
import cz.zcu.kiv.jop.annotation.populator.NumberValue;
import cz.zcu.kiv.jop.annotation.populator.PropertyPopulatorAnnotation;
import cz.zcu.kiv.jop.annotation.populator.StringValue;
import cz.zcu.kiv.jop.factory.AbstractBindingFactory;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.factory.binding.BindingException;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.util.AnnotationUtils;

/**
 * Implementation of {@link PropertyPopulatorFactory} which extends the abstract implementation of
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
public class PropertyPopulatorFactoryImpl extends AbstractBindingFactory<PropertyPopulator<?>> implements PropertyPopulatorFactory {

  /**
   * Check conditions for given annotation type. The annotation type cannot be <code>null</code> and
   * has to be marked by annotation {@link PropertyPopulatorAnnotation}. This method also checks
   * whatever the given annotation is not marked by annotation {@link CustomAnnotation} because the
   * binding of annotation for custom property populator is not allowed.
   *
   * @param annotation the annotation type to check.
   * @throws BindingException if entered annotation isn't valid.
   */
  @Override
  protected void checkAnnotation(Class<? extends Annotation> annotation) throws BindingException {
    super.checkAnnotation(annotation);

    if (!AnnotationUtils.isAnnotatedAnnotation(annotation, PropertyPopulatorAnnotation.class)) {
      throw new BindingException("Annotation has to be marked by annotation: " + PropertyPopulatorAnnotation.class.getName());
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
    bind(ArrayValue.class).to(ArrayValuePopulator.class);
    bind(NumberValue.class).to(NumberValuePopulator.class);
    bind(StringValue.class).to(StringValuePopulator.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <A extends Annotation> PropertyPopulator<A> createInstance(A annotation) throws FactoryException {
    PropertyPopulator<?> propertyPopulator = super.createInstance(annotation);

    try {
      propertyPopulator.getClass().getMethod("populate", Property.class, Object.class, annotation.annotationType());
    }
    catch (Exception exc) {
      throw new FactoryException("Invalid property populator was bound for annotation: " + annotation.annotationType().getName());
    }

    return (PropertyPopulator<A>)propertyPopulator;
  }
}
