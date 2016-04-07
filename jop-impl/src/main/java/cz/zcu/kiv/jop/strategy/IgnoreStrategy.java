package cz.zcu.kiv.jop.strategy;

import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.strategy.Ignore;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;

/**
 * Implementation of populating strategy for {@link Ignore} annotation which allows to forbid the
 * populating of referred object (field). This is default strategy - if the field has no another
 * populating strategy annotation, this strategy will be used.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class IgnoreStrategy implements PopulatingStrategy {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(IgnoreStrategy.class);

  /**
   * Returns name of this strategy.
   *
   * @return The name of {@link Ignore} strategy.
   */
  protected String getStrategyName() {
    return "@" + Ignore.class.getSimpleName() + " strategy";
  }

  /**
   * {@inheritDoc}
   * <p>
   * This strategy returns always <code>false</code> because it isn't lazy.
   */
  public boolean isLazyStrategy() {
    return false;
  }

  /**
   * {@inheritDoc}
   * <p>
   * This method returns always <code>true</code> because this strategy may be applied to all
   * properties.
   */
  public boolean supports(Property<?> property) {
    return true; // we can ignore all types of properties
  }

  /**
   * This method does nothing because this strategy skips given property so no value is populated
   * into that field and value of the property won't be changed.
   *
   * @param property the property on which will be applied this populating strategy.
   * @param context the populating context which contains already populated (generated) objects
   *          which may be obtained; also supports enqueuing classes for population.
   */
  public void applyStrategy(Property<?> property, PopulatingContext context) {
    logger.debug("Applying " + getStrategyName() + " to property: " + property);

    // NOOP

  }

}
