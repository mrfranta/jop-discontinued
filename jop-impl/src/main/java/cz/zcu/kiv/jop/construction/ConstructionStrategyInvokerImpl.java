package cz.zcu.kiv.jop.construction;

import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.construction.CustomConstructionStrategy;
import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.bean.JopBean;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Implementation of interface {@link ConstructionStrategyInvoker} (interlayer between the
 * construction strategies and other layers of this library). This implementation chooses and
 * invokes {@link ConstructionStrategy} according to given {@link CustomConstructionStrategy}
 * annotation or according to annotation which annotates the type for construction and return
 * constructed bean descriptor for constructed instance.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ConstructionStrategyInvokerImpl implements ConstructionStrategyInvoker {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(ConstructionStrategyInvokerImpl.class);

  /**
   * {@inheritDoc}
   */
  public Bean constructObject(Class<?> clazz, PopulatingContext context) throws ConstructionStrategyException {
    return constructObject(clazz, null, context);
  }

  /**
   * {@inheritDoc}
   */
  public Bean constructObject(Class<?> clazz, CustomConstructionStrategy customStrategy, PopulatingContext context) throws ConstructionStrategyException {
    if (clazz == null) {
      throw new ConstructionStrategyException("Class type cannot be null");
    }

    if (context == null) {
      throw new ConstructionStrategyException("Populating context cannot be null");
    }

    JopBean bean = null;

    try {
      // prepare skeleton of bean
      bean = ReflectionUtils.createInstance(JopBean.class, clazz);

      // lookup for custom construction strategy which annotates given object
      if (customStrategy == null) {
        customStrategy = clazz.getAnnotation(CustomConstructionStrategy.class);
      }

      ConstructionStrategy constructionStrategy = null;
      if (customStrategy != null) {
        // creates construction strategy for
        constructionStrategy = constructionStrategyFactory.createInstance(customStrategy.value());
      }
      else {
        // default construction strategy
        constructionStrategy = constructionStrategyFactory.getDefaultConstructionStrategy();
      }

      // no construction strategy
      if (constructionStrategy == null) {
        throw new ConstructionStrategyException("No such construction strategy");
      }

      logger.debug("Invoking construction strategy: " + constructionStrategy.getClass());

      Object instance = constructionStrategy.constructObject(clazz, context.createConstructionContext(bean));

      // store instance into skeleton of bean
      Field instanceField = ReflectionUtils.getDeclaredField(JopBean.class, "instance");
      if (instanceField != null) {
        instanceField.set(bean, instance);
      }
    }
    catch (Exception exc) {
      if (!(exc instanceof ConstructionStrategyException)) {
        throw new ConstructionStrategyException("Cannot create new instance of class: " + clazz.getName(), exc);
      }
      else {
        throw (ConstructionStrategyException)exc;
      }
    }

    return bean;
  }

  //----- Injection part ------------------------------------------------------

  /** Factory for construction strategies. */
  protected ConstructionStrategyFactory constructionStrategyFactory;

  /**
   * Sets (injects) factory for construction strategies.
   *
   * @param constructionStrategyFactory the factory to set (inject).
   */
  @Inject
  public final void setConstructionStrategyFactory(ConstructionStrategyFactory constructionStrategyFactory) {
    this.constructionStrategyFactory = constructionStrategyFactory;
  }
}
