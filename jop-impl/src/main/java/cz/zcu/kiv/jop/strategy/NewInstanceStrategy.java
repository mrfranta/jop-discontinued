package cz.zcu.kiv.jop.strategy;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.construction.CustomConstructionStrategy;
import cz.zcu.kiv.jop.annotation.strategy.NewInstance;
import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.class_provider.ClassProviderException;
import cz.zcu.kiv.jop.class_provider.ClassProviderInvoker;
import cz.zcu.kiv.jop.construction.ConstructionStrategy;
import cz.zcu.kiv.jop.construction.ConstructionStrategyInvoker;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.Setter;

/**
 * Implementation of populating strategy for {@link NewInstance} annotation allows to assign new
 * instance into property or parameter.
 * <p>
 * This strategy cooperates with {@link cz.zcu.kiv.jop.class_provider.ClassProvider class providers}
 * which may provides the target class type of new instance. Also cooperates with
 * {@link ConstructionStrategy construction strategies} which arranges construction of new instance.
 * <p>
 * The created instance is enqueued for population of properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
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
   * This strategy returns always <code>false</code> because it isn't lazy.
   */
  public boolean isLazyStrategy() {
    return false;
  }

  /**
   * Lookups for class provider annotation and in case that some annotation is present on given
   * property the proper {@link cz.zcu.kiv.jop.class_provider.ClassProvider class provider} in
   * invoked. In case that no annotation is present the declared class type of property is used for
   * construction of new instance.
   * <p>
   * In case that new instance for chosen class type cannot be instanced it stops populating.
   * Otherwise lookups for {@link CustomConstructionStrategy} annotation which may specify custom
   * {@link ConstructionStrategy} which arranges construction of new instance.
   * <p>
   * The created instance is populated into property and it's enqueued for population of properties.
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
      logger.info("New instance of " + clazz.getName() + " cannot be populated into property: " + property);
      return;
    }

    try {
      // lookup for custom construction strategy annotation on property
      CustomConstructionStrategy customConstructionStrategy = property.getAnnotation(CustomConstructionStrategy.class);
      Bean bean = constructionStrategyInvoker.constructObject(clazz, customConstructionStrategy, context);

      // enqueue bean for population
      if (!context.populate(bean)) {
        logger.warn("Instance of " + bean + " cannot be enqueued for population");
      }

      // store instance into property
      Setter<Object> setter = (Setter<Object>)property.getSetter();
      setter.set(context.getCurrentObject(), bean.getInstance());
    }
    catch (Exception exc) {
      throw new PopulatingStrategyException("Cannot apply " + getStrategyName(), exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Interlayer (invoker) for class providers. */
  protected ClassProviderInvoker classProviderInvoker;
  /** Interlayer (invoker) for construction strategies. */
  protected ConstructionStrategyInvoker constructionStrategyInvoker;

  /**
   * Sets (injects) interlayer (invoker) for class providers.
   *
   * @param classProviderInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setClassProviderInvoker(ClassProviderInvoker classProviderInvoker) {
    this.classProviderInvoker = classProviderInvoker;
  }

  /**
   * Sets (injects) interlayer (invoker) for construction strategies.
   *
   * @param constructionStrategyInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setConstructionStrategyInvoker(ConstructionStrategyInvoker constructionStrategyInvoker) {
    this.constructionStrategyInvoker = constructionStrategyInvoker;
  }
}
