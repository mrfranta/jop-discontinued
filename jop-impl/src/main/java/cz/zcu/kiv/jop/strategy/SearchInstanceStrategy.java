package cz.zcu.kiv.jop.strategy;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.strategy.SearchInstance;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.matcher.InstanceMatcherException;
import cz.zcu.kiv.jop.matcher.InstanceMatcherInvoker;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.PropertyException;
import cz.zcu.kiv.jop.property.Setter;

/**
 * Implementation of populating strategy for {@link SearchInstance} annotation which allows to use
 * already existing instance which will be searched in list of already generated objects. This
 * strategy uses {@link InstanceMatcherInvoker} for searching of instance to populate.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class SearchInstanceStrategy implements PopulatingStrategy {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(SearchInstanceStrategy.class);

  /**
   * {@inheritDoc}
   * <p>
   * This strategy doesn't supports property which declared class type is:
   * <ul>
   * <li>Annotation</li>
   * <li>Array</li>
   * <li>Enumeration</li>
   * <li>Primitive</li>
   * <li>Synthetic</li>
   * </ul>
   */
  public boolean supports(Property<?> property) {
    Class<?> propertyType = property.getType();

    // @formatter:off
    return (!propertyType.isAnnotation() // annotation type is not supported
        && !propertyType.isArray() // array is not supported for version 1.0.0
        && !propertyType.isEnum() // enumeration is not supported for version 1.0.0
        && !propertyType.isPrimitive() // primitive types cannot be instanced
        && !propertyType.isSynthetic() // synthetic class types are not supported
    );
    // @formatter:on
  }

  /**
   * {@inheritDoc}
   * <p>
   * This strategy returns always <code>true</code> because it is lazy
   */
  public boolean isLazyStrategy() {
    return true;
  }

  /**
   * Returns name of this strategy.
   *
   * @return The name of {@link SearchInstance} strategy.
   */
  protected String getStrategyName() {
    return "@" + SearchInstance.class.getSimpleName() + " strategy";
  }

  /**
   * Lookups for instance matcher annotation which annotates the given property. If annotation is
   * present, uses the {@link InstanceMatcherInvoker} for searching of matching instance which will
   * be populated into property. If no annotation was found then it's searched for first assignable
   * instance in {@link PopulatingContext#getPopulatedInstances list of populated instances}.
   */
  @SuppressWarnings("unchecked")
  public void applyStrategy(Property<?> property, PopulatingContext context) throws PopulatingStrategyException {
    logger.debug("Applying " + getStrategyName() + " to property: " + property);

    if (property == null) {
      throw new PopulatingStrategyException("Property cannot be null");
    }

    if (context == null) {
      throw new PopulatingStrategyException("Populating context cannot be null");
    }

    Object instance = null;
    if (instanceMatcherInvoker.isAnnotationPresent(property)) {
      // use the instance matcher invoker
      try {
        instance = instanceMatcherInvoker.match(property, context);
      }
      catch (InstanceMatcherException exc) {
        throw new PopulatingStrategyException("Cannot apply " + getStrategyName(), exc);
      }
    }
    else {
      // use first compatible instance
      Class<?> propertyType = property.getType();
      logger.info("No instance matcher annotation present. Searching for first instance of: " + propertyType.getName());
      for (Object obj : context.getPopulatedInstances(true)) {
        if (obj != null && propertyType.isAssignableFrom(obj.getClass())) {
          instance = obj;
          break;
        }
      }
    }

    if (instance == null) {
      logger.warn("No instance found for property: " + property);
    }

    try {
      Setter<Object> setter = (Setter<Object>)property.getSetter();
      setter.set(context.getCurrentObject(), instance);
    }
    catch (PropertyException exc) {
      throw new PopulatingStrategyException("Cannot apply " + getStrategyName(), exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Interlayer (invoker) for instance matchers. */
  protected InstanceMatcherInvoker instanceMatcherInvoker;

  /**
   * Sets (injects) interlayer (invoker) for instance matchers.
   *
   * @param instanceMatcherInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setInstanceMatcherInvoker(InstanceMatcherInvoker instanceMatcherInvoker) {
    this.instanceMatcherInvoker = instanceMatcherInvoker;
  }

}
