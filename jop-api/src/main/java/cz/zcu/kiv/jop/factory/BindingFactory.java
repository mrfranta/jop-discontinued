package cz.zcu.kiv.jop.factory;

import java.lang.annotation.Annotation;

/**
 * Common interface for binding factory which extends {@link Factory} interface
 * and adds method which returns created instance of implementation of interface
 * <code>T</code> bound to some annotation. So this factory can contains some
 * {@link cz.zcu.kiv.jop.binding.Binding Bindings} between annotations and
 * classes. Method {@link #createInstance(Annotation)} provides created
 * instances of bound class to given annotation as parameter.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of interface which instances will be created by this factory.
 */
public interface BindingFactory<T> extends Factory<T> {

  /**
   * Returns created instance of bound implementation for given annotation. If
   * the given class is annotated by {@link javax.inject.Singleton Singleton}
   * annotation it will be cached so the returned instances will be singletons.
   * Otherwise the factory always creates new instance for each call of this
   * method.
   * <p>
   * In case that there is no bound implementation of interface <code>T</code>
   * for given annotation the exception may be thrown - the <code>null</code>
   * value should not be returned.
   *
   * @param annotation the annotation for which will be created bound instance
   *          of interface <code>T</code> implementation.
   * @return Created instance of interface <code>T</code> implementation which
   *         was bound to given annotation.
   * @throws FactoryException if some error occurs during creation of (new)
   *           instance of given class or if no implementation was bound to
   *           given annotation.
   */
  public <A extends Annotation> T createInstance(A annotation) throws FactoryException;

}
