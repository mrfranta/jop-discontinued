package cz.zcu.kiv.jop.strategy;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;

/**
 * Implementation of default populating strategy which lookups for populating annotations
 * (annotations for property populators) of given property. If there is no populating annotation
 * present, tries to invoke the default property populator which should lookup for value generator
 * annotations. If no value generator annotation is present, the value of property should not be
 * changed (it will be same like applying of {@link IgnoreStrategy}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class DefaultStrategy implements PopulatingStrategy {

  /**
   * Returns name of this strategy.
   *
   * @return The name of default strategy.
   */
  protected String getStrategyName() {
    return "Default strategy";
  }

  /**
   * {@inheritDoc}
   * <p>
   * This method returns always <code>true</code> because this strategy may be applied to all
   * properties.
   */
  public boolean supports(Property<?> property) {
    return true; // this is default strategy for all properties.
  }

  public void applyStrategy(Property<?> property, PopulatingContext context) throws PopulatingStrategyException {
    // TODO: Implement me
  }

}
