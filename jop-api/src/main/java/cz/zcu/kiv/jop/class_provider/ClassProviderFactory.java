package cz.zcu.kiv.jop.class_provider;

import java.lang.annotation.Annotation;

import cz.zcu.kiv.jop.factory.BindingFactory;
import cz.zcu.kiv.jop.factory.FactoryException;

/**
 * The interface for factory which serves for creation of {@link ClassProvider ClassProviders}.
 * Class providers should be created primary by this factory.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ClassProviderFactory extends BindingFactory<ClassProvider<?>> {

  /**
   * Returns created instance of bound implementation of class provider for given annotation. If the
   * given class is annotated by {@link javax.inject.Singleton Singleton} annotation it will be
   * cached so the returned instances will be singletons. Otherwise the factory always creates new
   * instance for each call of this method.
   * <p>
   * In case that there is no bound implementation of class provider for given annotation the
   * exception may be thrown - the <code>null</code> value should not be returned.
   *
   * @param annotation the annotation for which will be created bound instance of class provider
   *          implementation.
   * @return Created instance of class provider implementation which was bound to given annotation.
   * @throws FactoryException if some error occurs during creation of (new) instance of given class
   *           or if no implementation was bound to given annotation.
   */
  public <A extends Annotation> ClassProvider<A> createInstance(A annotation) throws FactoryException;

}
