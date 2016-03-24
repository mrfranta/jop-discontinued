package cz.zcu.kiv.jop.strategy;

import java.lang.annotation.Annotation;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.strategy.Ignore;
import cz.zcu.kiv.jop.annotation.strategy.NewInstance;
import cz.zcu.kiv.jop.annotation.strategy.NullValue;
import cz.zcu.kiv.jop.annotation.strategy.PopulatingStrategyAnnotation;
import cz.zcu.kiv.jop.annotation.strategy.SearchInstance;
import cz.zcu.kiv.jop.factory.AbstractBindingFactory;
import cz.zcu.kiv.jop.factory.binding.BindingException;
import cz.zcu.kiv.jop.util.AnnotationUtils;

/**
 * Implementation of {@link PopulatingStrategyFactory} which extends the abstract implementation of
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
public class PopulatingStrategyFactoryImpl extends AbstractBindingFactory<PopulatingStrategy> implements PopulatingStrategyFactory {

  /**
   * Check conditions for given annotation type. The annotation type cannot be <code>null</code> and
   * has to be marked by annotation {@link PopulatingStrategyAnnotation}. This method also checks
   * whatever the given annotation is not marked by annotation {@link CustomAnnotation} because the
   * binding of annotation for custom populating strategy is not allowed.
   *
   * @param annotation the annotation type to check.
   * @throws BindingException If given annotation isn't valid.
   */
  @Override
  protected void checkAnnotation(Class<? extends Annotation> annotation) throws BindingException {
    super.checkAnnotation(annotation);

    if (!AnnotationUtils.isAnnotatedAnnotation(annotation, PopulatingStrategyAnnotation.class)) {
      throw new BindingException("Annotation has to be marked by annotation: " + PopulatingStrategyAnnotation.class.getName());
    }

    if (AnnotationUtils.isAnnotatedAnnotation(annotation, CustomAnnotation.class)) {
      throw new BindingException("Cannot create binding for custom annotation");
    }
  }

  /**
   * Configures default binding between annotations for populating strategies and class types of
   * strategies implementation. This method is final and for customization of factory can be used
   * method {@link #customConfigure()} in which may be some bindings re-binded.
   *
   * @throws BindingException If some error occurs during configuration of binding factory.
   */
  @Override
  protected final void configure() throws BindingException {
    bind(Ignore.class).to(IgnoreStrategy.class);
    bind(NewInstance.class).to(NewInstanceStrategy.class);
    bind(NullValue.class).to(NullValueStrategy.class);
    bind(SearchInstance.class).to(SearchInstanceStrategy.class);
  }

}
