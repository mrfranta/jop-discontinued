package cz.zcu.kiv.jop.strategy;

import cz.zcu.kiv.jop.factory.BindingFactory;

/**
 * The interface for factory which serves for creation of
 * {@link GeneratingStrategy PropertyStrategies}. Generating strategies for
 * properties should be created primary by this factory.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface GeneratingStrategyFactory extends BindingFactory<GeneratingStrategy> {

}
