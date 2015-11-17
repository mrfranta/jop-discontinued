package cz.zcu.kiv.jop.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.util.Preconditions;
import cz.zcu.kiv.jop.util.ReflectionException;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Abstract implementation of {@link Factory} interface. This class uses
 * {@link ReflectionUtils} for creation of instances of given classes. If the
 * {@link Singleton} annotation is present the factory stores instance of
 * annotated class into {@link ConcurrentHashMap}.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of interface which instances will be created by this factory.
 */
public abstract class AbstractFactory<T> implements Factory<T> {

  /** Mapped singleton instances created by this factory. */
  protected final Map<Class<? extends T>, T> instances;

  /**
   * Constructs a new empty factory.
   */
  public AbstractFactory() {
    instances = new ConcurrentHashMap<Class<? extends T>, T>();
  }

  /**
   * {@inheritDoc}
   */
  public T createInstance(Class<? extends T> clazz) throws FactoryException {
    Preconditions.checkArgumentNotNull(clazz, "Class type cannot be null");

    T instance = instances.get(clazz);
    if (instance == null) {
      try {
        instance = ReflectionUtils.createInstance(clazz);
      }
      catch (ReflectionException exc) {
        throw new FactoryException(exc);
      }

      // put singleton into map of instances
      if (clazz.isAnnotationPresent(Singleton.class)) {
        instances.put(clazz, instance);
      }
    }

    return instance;
  }

}
