package cz.zcu.kiv.jop.factory;

/**
 * Common interface for factory which serves for creation of instances of implementations for
 * generic (interface) type <code>T</code>. Implementations of this interface should be created
 * primary by this factory. If a implementation is annotated by {@link javax.inject.Singleton
 * Singleton} annotation the created instances will be cached so returned instances will be
 * singletons. Otherwise the factory always creates new instance for each creation of instance for
 * implementation.
 *
 * @see <a href="https://cs.wikipedia.org/wiki/Singleton">Singleton</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Type of interface which instances will be created by this factory.
 */
public interface Factory<T> {

  /**
   * Returns created instance of given class. If the given class is annotated by
   * {@link javax.inject.Singleton Singleton} annotation it will be cached so the returned instances
   * will be singletons. Otherwise the factory always creates new instance for each call of this
   * method.
   *
   * @param clazz the class type for implementation of interface <code>T</code> which instance will
   *          be (created and) returned.
   * @return Created instance of given class.
   * @throws FactoryException if some error occurs during creation of (new) instance of given class.
   */
  public T createInstance(Class<? extends T> clazz) throws FactoryException;

}
