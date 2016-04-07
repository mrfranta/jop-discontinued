package cz.zcu.kiv.jop.construction;

import cz.zcu.kiv.jop.annotation.construction.CustomConstructionStrategy;
import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.context.PopulatingContext;

/**
 * Interface which serves as interlayer between the construction strategies and other layers of this
 * library. Implementation of this interface should choose and invoke {@link ConstructionStrategy}
 * according to given {@link CustomConstructionStrategy} annotation or according to annotation which
 * annotates the type for construction and return constructed bean descriptor for constructed
 * instance.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ConstructionStrategyInvoker {

  /**
   * Analyzes annotations of given class type and invokes default or custom implementation of
   * {@link ConstructionStrategy} which should construct new instance of given class type. Bean
   * descriptor of newly constructed instance is returned.
   * <p>
   * During construction may be used given populating context for obtaining already created
   * instances, etc.
   *
   * @param clazz the target class type of new instance.
   * @param context the populating context which may be used for object construction.
   * @return Bean descriptor of newly constructed instance of given class type.
   * @throws ConstructionStrategyException If some error occurs during construction of new instance.
   */
  public Bean constructObject(Class<?> clazz, PopulatingContext context) throws ConstructionStrategyException;

  /**
   * If given custom construction strategy is <code>null</code> this method analyzes annotations of
   * given class type and invokes default or custom implementation of {@link ConstructionStrategy}
   * which should construct new instance of given class type. Bean descriptor of newly constructed
   * instance is returned.
   * <p>
   * During construction may be used given populating context for obtaining already created
   * instances, etc.
   *
   * @param clazz the target class type of new instance.
   * @param customStrategy annotation for custom construction strategy (may be <code>null</code>).
   * @param context the populating context which may be used for object construction.
   * @return Bean descriptor of newly constructed instance of given class type.
   * @throws ConstructionStrategyException If some error occurs during construction of new instance.
   */
  public Bean constructObject(Class<?> clazz, CustomConstructionStrategy customStrategy, PopulatingContext context) throws ConstructionStrategyException;

}
