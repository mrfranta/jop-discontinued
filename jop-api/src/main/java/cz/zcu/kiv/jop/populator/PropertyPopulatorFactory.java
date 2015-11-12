package cz.zcu.kiv.jop.populator;

import java.lang.annotation.Annotation;

import cz.zcu.kiv.jop.factory.BindingFactory;
import cz.zcu.kiv.jop.factory.FactoryException;

/**
 * The interface for factory which serves for creation of
 * {@link TypedPropertyPopulator TypedPropertyPopulators}. Property populators
 * should be created primary by this factory.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface PropertyPopulatorFactory extends BindingFactory<TypedPropertyPopulator<?, ?>> {

  /**
   * Returns created instance of bound implementation of property populator for
   * given annotation. If the given class is annotated by
   * {@link javax.inject.Singleton Singleton} annotation it will be cached so
   * the returned instances will be singletons. Otherwise the factory always
   * creates new instance for each call of this method.
   * <p>
   * In case that there is no bound implementation of class property populator
   * for given annotation the exception may be thrown - the <code>null</code>
   * value should not be returned.
   *
   * @param annotation the annotation for which will be created bound instance
   *          of property populator implementation.
   * @return Created instance of property populator implementation which was
   *         bound to given annotation.
   * @throws FactoryException if some error occurs during creation of (new)
   *           instance of given class or if no implementation was bound to
   *           given annotation.
   */
  public <A extends Annotation> TypedPropertyPopulator<?, A> createInstance(A annotation) throws FactoryException;

}
