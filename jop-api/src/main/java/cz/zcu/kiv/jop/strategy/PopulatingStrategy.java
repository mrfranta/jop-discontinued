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
   * Returns information whether the populating strategy is lazy. Lazy strategies are usually
   * invoked in the end of poulating process but it depends on implementation of actual
   * {@link PopulatingContext}. Lazy strategies cannot enqueue new objects (dependencies) for
   * population.
   *
   * @return <code>true</code> if populating strategy is lazy; <code>false</code> otherwise.
   */
  public boolean isLazyStrategy();

  /**
   * Returns information whether populating strategy may be {@link #applyStrategy applied} to given
   * property.
   *
   * @param property the property to check.
   * @return <code>true</code> if populating strategy may be {@link #applyStrategy applied} to given
   *         property; <code>false</code> otherwise.
   */
  public boolean supports(Property<?> property);

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
