package cz.zcu.kiv.jop.strategy;

import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.strategy.NullValue;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.Setter;
import cz.zcu.kiv.jop.util.Defaults;

/**
 * Implementation of populating strategy for {@link NullValue} annotation which allows to forbid
 * populating of referred object (field). In such case the reference will be set on
 * <code>null</code> and the dependency will not be created. Also the value of the field will be
 * &quot;replaced&quot;. This annotation can be also used for primitive types for which will be used
 * default value (for example 0 for numeric types).
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class NullValueStrategy implements PopulatingStrategy {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(NullValueStrategy.class);

  /**
   * {@inheritDoc}
   * <p>
   * This method returns always <code>true</code> because this strategy may be applied to all
   * properties.
   */
  public boolean supports(Property<?> property) {
    return true; // we can "reset" all properties
  }

  /**
   * Returns name of this strategy.
   *
   * @return The name of {@link NullValue} strategy.
   */
  protected String getStrategyName() {
    return "@" + NullValue.class.getSimpleName() + " strategy";
  }

  /**
   * Poupulates given property by <code>null</code> value in case of property for non-primitive
   * types. In case that property is for primitive type this method fills it by default value.
   * Generally this strategy uses the call of {@link Defaults#getDefaultValue(Class)} method.
   *
   * @throws PopulatingStrategyException In case that <code>null</code> or default value cannot be
   *           populated into property.
   */
  @SuppressWarnings("unchecked")
  public void applyStrategy(Property<?> property, PopulatingContext context) throws PopulatingStrategyException {
    logger.debug("Applying " + getStrategyName() + " to property: " + property);

    try {
      Setter<Object> setter = (Setter<Object>)property.getSetter();
      Class<?> propertyType = setter.getPropertyType();
      setter.set(context.getCurrentObject(), Defaults.getDefaultValue(propertyType));
    }
    catch (Exception exc) {
      throw new PopulatingStrategyException("Cannot apply " + getStrategyName(), exc);
    }
  }

}
