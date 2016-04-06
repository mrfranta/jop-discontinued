package cz.zcu.kiv.jop.construction;

import cz.zcu.kiv.jop.factory.Factory;

/**
 * The interface for factory which serves for creation of {@link ConstructionStrategy
 * ConstructionStrategies}. Instances of construction strategies should be created primary by this
 * factory.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ConstructionStrategyFactory extends Factory<ConstructionStrategy> {

  /**
   * Returns default implementation of construction strategy.
   *
   * @return Default implementation of construction strategy.
   */
  public ConstructionStrategy getDefaultConstructionStrategy();

}
