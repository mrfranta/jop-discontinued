package cz.zcu.kiv.jop.strategy;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.populator.PropertyPopulatorException;
import cz.zcu.kiv.jop.populator.PropertyPopulatorInvoker;
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

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(DefaultStrategy.class);

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

  /**
   * Lookups for populating annotations (annotations for property populators) of given property. If
   * there is no populating annotation present, tries to invoke the default property populator which
   * should lookup for value generator annotations. If no value generator annotation is present, the
   * value of property should not be changed.
   */
  public void applyStrategy(Property<?> property, PopulatingContext context) throws PopulatingStrategyException {
    logger.debug("Applying " + getStrategyName() + " to property: " + property);

    if (property == null) {
      throw new PopulatingStrategyException("Property cannot be null");
    }

    if (context == null) {
      throw new PopulatingStrategyException("Populating context cannot be null");
    }

    try {
      propertyPopulatorInvoker.populate(property, context.getCurrentObject());
    }
    catch (PropertyPopulatorException exc) {
      throw new PopulatingStrategyException("Cannot apply " + getStrategyName(), exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Interlayer (invoker) for property populators. */
  protected PropertyPopulatorInvoker propertyPopulatorInvoker;

  /**
   * Sets (injects) interlayer (invoker) for property populators.
   *
   * @param propertyPopulatorInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setPropertyPopulatorInvoker(PropertyPopulatorInvoker propertyPopulatorInvoker) {
    this.propertyPopulatorInvoker = propertyPopulatorInvoker;
  }

}
