package cz.zcu.kiv.jop.context;

import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.strategy.PopulatingStrategy;
import cz.zcu.kiv.jop.strategy.PopulatingStrategyException;

/**
 * Helper class which serves as holder of lazy populating strategies invocations. It contains all
 * required informations which will be used for invocation of lazy populating strategy for which it
 * was created.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
class LazyPopulatingStrategyInvocation {

  /** The property on which will be applied lazy populating strategy. */
  protected final Property<?> property;
  /** The bean descriptor of property owner. */
  protected final Bean owner;
  /** The lazy populating strategy. */
  protected final PopulatingStrategy populatingStrategy;

  /**
   * Constructs new invocation of given lazy populating strategy.
   *
   * @param property the property on which will be applied this populating strategy.
   * @param owner the bean descriptor of property owner.
   * @param populatingStrategy the lazy populating strategy.
   */
  public LazyPopulatingStrategyInvocation(Property<?> property, Bean owner, PopulatingStrategy populatingStrategy) {
    this.property = property;
    this.owner = owner;
    this.populatingStrategy = populatingStrategy;
  }

  /**
   * Applies lazy populating strategy.
   *
   * @param context the populating context which contains already populated (generated) objects
   *          which may be obtained; also supports enqueuing classes for population.
   * @throws PopulatingStrategyException If some error occurs during applying of populating strategy
   *           to given property.
   */
  public void applyStrategy(PopulatingContext context) throws PopulatingStrategyException {
    populatingStrategy.applyStrategy(property, context);
  }

  /**
   * Returns the property on which will be applied invoked lazy populating strategy.
   *
   * @return The property on which will be applied lazy populating strategy.
   */
  public Property<?> getProperty() {
    return property;
  }

  /**
   * Returns the bean descriptor of property owner.
   *
   * @return The bean descriptor of property owner.
   */
  public Bean getOwner() {
    return owner;
  }

  /**
   * The lazy populating strategy.
   *
   * @return The lazy populating strategy.
   */
  public PopulatingStrategy getPopulatingStrategy() {
    return populatingStrategy;
  }

}
