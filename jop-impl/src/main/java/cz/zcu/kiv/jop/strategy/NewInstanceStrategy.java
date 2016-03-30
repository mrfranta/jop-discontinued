package cz.zcu.kiv.jop.strategy;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.strategy.NewInstance;
import cz.zcu.kiv.jop.class_provider.ClassProviderException;
import cz.zcu.kiv.jop.class_provider.ClassProviderInvoker;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.PropertyException;
import cz.zcu.kiv.jop.property.Setter;

@Singleton
public class NewInstanceStrategy implements PopulatingStrategy {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(NewInstanceStrategy.class);

  /**
   * Returns name of this strategy.
   *
   * @return The name of {@link NewInstance} strategy.
   */
  protected String getStrategyName() {
    return "@" + NewInstance.class.getSimpleName() + " strategy";
  }

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

    return (!propertyType.isAnnotation() // annotation type is not supported
        && !propertyType.isArray() // array is not supported for version 1.0.0
        && !propertyType.isEnum() // enumeration is not supported for version 1.0.0
        && !propertyType.isPrimitive() // primitive types cannot be instanced
        && !propertyType.isSynthetic() // synthetic class types are not supported
    );
  }

  @SuppressWarnings("unchecked")
  public void applyStrategy(Property<?> property, PopulatingContext context) throws PopulatingStrategyException {
    logger.debug("Applying " + getStrategyName() + " to property: " + property);

    if (property == null) {
      throw new PopulatingStrategyException("Property cannot be null");
    }

    if (context == null) {
      throw new PopulatingStrategyException("Populating context cannot be null");
    }

    Class<?> clazz = null;
    if (classProviderInvoker.isAnnotationPresent(property)) {
      try {
        clazz = classProviderInvoker.get(property);
      }
      catch (ClassProviderException exc) {
        throw new PopulatingStrategyException("Cannot apply " + getStrategyName(), exc);
      }
    }
    else {
      clazz = property.getType();
      logger.info("No class provider annotation present. Using declared class type: " + clazz.getName());
    }

    if (!context.canPopulate(clazz)) {
      logger.info("New instance of: " + clazz.getName() + " cannot be populated into property: " + property);
      return;
    }

    // TODO: Check instanceability & try to prepare instance of class

    Object instance = null;

    try {
      Setter<Object> setter = (Setter<Object>)property.getSetter();
      setter.set(context.getCurrentObject(), instance);
    }
    catch (PropertyException exc) {
      throw new PopulatingStrategyException("Cannot apply " + getStrategyName(), exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Interlayer (invoker) for class providers. */
  protected ClassProviderInvoker classProviderInvoker;

  /**
   * Sets (injects) interlayer (invoker) for class providers.
   *
   * @param classProviderInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setClassProviderInvoker(ClassProviderInvoker classProviderInvoker) {
    this.classProviderInvoker = classProviderInvoker;
  }

}
