package cz.zcu.kiv.jop.strategy;

import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;

/**
 * The common interface for all populating strategies for properties. The strategy is applied to
 * some property and it may populate some value into that property or enqueue class into populating
 * context to populate. Strategies can be used in combination with class providers or instance
 * matchers.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingStrategy {

  /**
   * Applies populating strategy to given property. It may populate some value into property or
   * enqueue class into populating context to populate.
   *
   * @param property the property on which will be applied this populating strategy.
   * @param context the populating context which contains already populated (generated) objects
   *          which may be obtained; also supports enqueuing classes for population.
   * @throws PopulatingStrategyException If some error occurs during applying of populating strategy
   *           to given property.
   */
  public void applyStrategy(Property<?> property, PopulatingContext context) throws PopulatingStrategyException;

}
