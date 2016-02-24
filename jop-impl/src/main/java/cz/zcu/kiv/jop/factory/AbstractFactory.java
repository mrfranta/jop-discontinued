package cz.zcu.kiv.jop.factory;

import cz.zcu.kiv.jop.ioc.Injector;
import cz.zcu.kiv.jop.ioc.InjectorException;
import cz.zcu.kiv.jop.ioc.InjectorManager;

/**
 * Abstract implementation of {@link Factory} interface which contains implementation of method
 * {@link #createInstance}. That uses {@link Injector} for creation of instances of given classes.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of interface which instances will be created by this factory.
 */
public abstract class AbstractFactory<T> implements Factory<T> {

  /**
   * {@inheritDoc}
   */
  public T createInstance(Class<? extends T> clazz) throws FactoryException {
    if (clazz == null) {
      throw new FactoryException("Class type cannot be null");
    }

    try {
      Injector injector = InjectorManager.getInstance().get();

      return injector.getInstance(clazz);
    }
    catch (InjectorException exc) {
      throw new FactoryException("Cannot create new instance of " + clazz.getName(), exc);
    }
  }

}
