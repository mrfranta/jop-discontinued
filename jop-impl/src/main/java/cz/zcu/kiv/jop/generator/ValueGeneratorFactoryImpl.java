package cz.zcu.kiv.jop.generator;

import java.lang.annotation.Annotation;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClass;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClassForName;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClass;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForName;
import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;
import cz.zcu.kiv.jop.annotation.generator.bool.RandomBoolean;
import cz.zcu.kiv.jop.annotation.generator.number.BinomialGenerator;
import cz.zcu.kiv.jop.annotation.generator.number.CategoricalGenerator;
import cz.zcu.kiv.jop.annotation.generator.number.DiscreteUniformGenerator;
import cz.zcu.kiv.jop.annotation.generator.number.ExponentialGenerator;
import cz.zcu.kiv.jop.annotation.generator.number.GaussianGenerator;
import cz.zcu.kiv.jop.annotation.generator.number.PoissonGenerator;
import cz.zcu.kiv.jop.annotation.generator.number.UniformGenerator;
import cz.zcu.kiv.jop.factory.AbstractBindingFactory;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.factory.binding.BindingException;
import cz.zcu.kiv.jop.generator.bool.RandomBooleanGenerator;
import cz.zcu.kiv.jop.generator.clazz.RandomClassForNameGenerator;
import cz.zcu.kiv.jop.generator.clazz.RandomClassGenerator;
import cz.zcu.kiv.jop.generator.clazz.TargetClassForNameGenerator;
import cz.zcu.kiv.jop.generator.clazz.TargetClassGenerator;
import cz.zcu.kiv.jop.generator.number.BinomialNumberGenerator;
import cz.zcu.kiv.jop.generator.number.CategoricalNumberGenerator;
import cz.zcu.kiv.jop.generator.number.DiscreteUniformNumberGenerator;
import cz.zcu.kiv.jop.generator.number.ExponentialNumberGenerator;
import cz.zcu.kiv.jop.generator.number.GaussianNumberGenerator;
import cz.zcu.kiv.jop.generator.number.PoissonNumberGenerator;
import cz.zcu.kiv.jop.generator.number.UniformNumberGenerator;
import cz.zcu.kiv.jop.util.AnnotationUtils;

/**
 * Implementation of {@link ValueGeneratorFactory} which extends the abstract implementation of
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
public class ValueGeneratorFactoryImpl extends AbstractBindingFactory<ValueGenerator<?, ?>> implements ValueGeneratorFactory {

  /**
   * Check conditions for given annotation type. The annotation type cannot be <code>null</code> and
   * has to be marked by annotation {@link GeneratorAnnotation}. This method also checks whatever
   * the given annotation is not marked by annotation {@link CustomAnnotation} because the binding
   * of annotation for custom value generator is not allowed.
   *
   * @param annotation the annotation type to check.
   * @throws BindingException if entered annotation isn't valid.
   */
  @Override
  protected void checkAnnotation(Class<? extends Annotation> annotation) throws BindingException {
    super.checkAnnotation(annotation);

    if (!AnnotationUtils.isAnnotatedAnnotation(annotation, GeneratorAnnotation.class)) {
      throw new BindingException("Annotation has to be marked by annotation: " + GeneratorAnnotation.class.getName());
    }

    if (AnnotationUtils.isAnnotatedAnnotation(annotation, CustomAnnotation.class)) {
      throw new BindingException("Cannot create binding for custom annotation");
    }
  }

  /**
   * Configures default binding between annotations for value generators and class types of value
   * generators. This method is final and for customization of factory can be used method
   * {@link #customConfigure()} in which may be some bindings re-binded.
   *
   * @throws BindingException If some error occurs during configuration of binding factory.
   */
  @Override
  protected final void configure() throws BindingException {
    // boolean value generators
    bind(RandomBoolean.class).to(RandomBooleanGenerator.class);

    // class type generators
    bind(RandomClass.class).to(RandomClassGenerator.class);
    bind(RandomClassForName.class).to(RandomClassForNameGenerator.class);
    bind(TargetClass.class).to(TargetClassGenerator.class);
    bind(TargetClassForName.class).to(TargetClassForNameGenerator.class);

    // number generators
    bind(BinomialGenerator.class).to(BinomialNumberGenerator.class);
    bind(CategoricalGenerator.class).to(CategoricalNumberGenerator.class);
    bind(DiscreteUniformGenerator.class).to(DiscreteUniformNumberGenerator.class);
    bind(ExponentialGenerator.class).to(ExponentialNumberGenerator.class);
    bind(GaussianGenerator.class).to(GaussianNumberGenerator.class);
    bind(PoissonGenerator.class).to(PoissonNumberGenerator.class);
    bind(UniformGenerator.class).to(UniformNumberGenerator.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <A extends Annotation> ValueGenerator<?, A> createInstance(A annotation) throws FactoryException {
    ValueGenerator<?, ?> valueGenerator = super.createInstance(annotation);
    try {
      valueGenerator.getClass().getDeclaredMethod("getValue", annotation.annotationType());
    }
    catch (Exception exc) {
      throw new FactoryException("Invalid value generator was bound for annotation: " + annotation.annotationType().getName());
    }

    return (ValueGenerator<?, A>)valueGenerator;
  }

}
