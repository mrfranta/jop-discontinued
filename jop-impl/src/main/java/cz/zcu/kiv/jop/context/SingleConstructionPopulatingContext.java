package cz.zcu.kiv.jop.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.strategy.PopulatingStrategy;
import cz.zcu.kiv.jop.strategy.PopulatingStrategyException;

/**
 * Extension of {@link SinglePopulatingContext} which is used for construction of new instances and
 * which doesn't support population of dependencies of bean.
 * <p>
 * This context is created for some bean descriptor of object which will be constructed and which is
 * returned by method {@link #getCurrentBean()} (and instance of object for which was created the
 * bean descriptor is returned by method {@link #getCurrentObject()}).
 * <p>
 * The construction populating context doesn't support lazy populating strategies and all strategies
 * which are passed to method {@link #addLazyPopulatingStrategyInvocation} are invoked immediately.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class SingleConstructionPopulatingContext extends SinglePopulatingContext {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(SingleConstructionPopulatingContext.class);

  /** Instance of current bean which is constructed. */
  protected final Bean currentBean;

  /**
   * Constructs new populating context for construction which doesn't support population of
   * dependencies.
   *
   * @param bean bean descriptor for which was created this populating context.
   */
  protected SingleConstructionPopulatingContext(Bean bean) {
    super(null); // queue is not used

    this.currentBean = bean;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Bean getCurrentBean() {
    return currentBean;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getCurrentObject() {
    return currentBean.getInstance();
  }

  /**
   * The construction populating context doesn't support lazy invocations of populating strategies.
   * Because of that are all strategies invoked immediately.
   *
   * @param property the property for which will be performed invocation.
   * @param strategy the lazy populating strategy to invoke.
   * @throws PopulatingStrategyException If some error occurs during adding or invocation of
   *           strategy.
   */
  @Override
  public void addLazyPopulatingStrategyInvocation(Property<?> property, PopulatingStrategy strategy) throws PopulatingStrategyException {
    logger.debug("Lazy populating strategy is not supported for this context");
    logger.debug("Added lazy populating strategy: " + strategy.getClass().getName() + "; for property: " + property);
    strategy.applyStrategy(property, this); // lazy strategies are not available
  }
}
