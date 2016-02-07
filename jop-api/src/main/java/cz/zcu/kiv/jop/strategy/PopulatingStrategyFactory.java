package cz.zcu.kiv.jop.strategy;

import cz.zcu.kiv.jop.factory.BindingFactory;

/**
 * The interface for factory which serves for creation of {@link PopulatingStrategy
 * PropertyStrategies}. Populating strategies for properties should be created primary by this
 * factory.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingStrategyFactory extends BindingFactory<PopulatingStrategy> {

}
