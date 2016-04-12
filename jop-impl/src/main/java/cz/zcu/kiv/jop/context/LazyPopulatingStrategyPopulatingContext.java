package cz.zcu.kiv.jop.context;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.strategy.PopulatingStrategy;
import cz.zcu.kiv.jop.strategy.PopulatingStrategyException;

/**
 * The implementation of {@link PopulatingContext} which serves for invocations of lazy population
 * strategies which cannot construct new objects or enqueue dependencies into populating queue.
 * Because of that the method {@link #canPopulate(Class)} returns always <code>false</code> and
 * methods {@link #populate(Bean)} and {@link #populate(Object)} has no effect and returns always
 * the <code>false</code>.
 * <p>
 * Also this populating context doesn't support lazy populating strategies and all strategies which
 * are passed to method {@link #addLazyPopulatingStrategyInvocation} are invoked immediately.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class LazyPopulatingStrategyPopulatingContext extends AbstractPopulatingContext {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(LazyPopulatingStrategyPopulatingContext.class);

  /** Instance of current bean for which applied lazy populating strategy. */
  protected Bean currentBean;

  /**
   * Constructs new populating context for lazy populating strategies.
   *
   * @param bean the current bean
   * @param globalInstances the list of populated instances out of this populating context or
   *          sub-context.
   * @param session the instance of local session of populated (generated) objects.
   */
  public LazyPopulatingStrategyPopulatingContext(Bean bean, List<Object> globalInstances, PopulatingContextSession session) {
    super(globalInstances, session);
    this.currentBean = bean;
  }

  /**
   * {@inheritDoc}
   */
  public Object getCurrentObject() {
    Bean currentBean = getCurrentBean();

    return currentBean == null ? null : currentBean.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  public Bean getCurrentBean() {
    return currentBean;
  }

  /**
   * Sets bean descriptor for currently populated object.
   *
   * @param currentBean bean descriptor for currently populated object.
   */
  void setCurrentBean(Bean currentBean) {
    this.currentBean = currentBean;
  }

  /**
   * Returns always <code>false</code> because this context doesn't support population of
   * dependencies.
   *
   * @param dependency the dependency of current bean (object) to check.
   * @return Always <code>false</code>.
   */
  public boolean canPopulate(Class<?> dependency) {
    return false;
  }

  /**
   * This method has no effect and returns always <code>false</code> because this context doesn't
   * support population of dependencies.
   *
   * @param dependency the dependency of current bean (object) to populate.
   * @return Always <code>false</code>.
   */
  public boolean populate(Object dependency) {
    return false;
  }

  /**
   * This method has no effect and returns always <code>false</code> because this context doesn't
   * support population of dependencies.
   *
   * @param dependency the bean descriptor of dependency of current bean (object) to populate.
   * @return Always <code>false</code>.
   */
  public boolean populate(Bean dependency) {
    return false;
  }

  /**
   * This context doesn't support lazy invocations of populating strategies. Because of that are all
   * strategies invoked immediately.
   *
   * @param property the property for which will be performed invocation.
   * @param strategy the lazy populating strategy to invoke.
   * @throws PopulatingStrategyException If some error occurs during adding or invocation of
   *           strategy.
   */
  public void addLazyPopulatingStrategyInvocation(Property<?> property, PopulatingStrategy strategy) throws PopulatingStrategyException {
    logger.debug("Lazy populating strategy is not supported for this context");
    logger.debug("Added lazy populating strategy: " + strategy.getClass().getName() + "; for property: " + property);
    strategy.applyStrategy(property, this); // lazy strategies are not available
  }

  /**
   * The construction context is not allowed for this context and because of that this method throws
   * {@link UnsupportedOperationException} for each call of this method.
   *
   * @throws UnsupportedOperationException For each call of this method.
   */
  public PopulatingContext createConstructionContext(Bean bean) {
    throw new UnsupportedOperationException("Construction context is not allowed for this context");
  }
}
