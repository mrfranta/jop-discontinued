package cz.zcu.kiv.jop.construction;

import cz.zcu.kiv.jop.context.PopulatingContext;

/**
 * The common interface for construction of new instances of objects. The strategy may be used
 * creation of objects for population or for creation of their dependencies.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ConstructionStrategy {

  /**
   * Constructs new instance of given class type. During construction may be used given populating
   * context for obtaining already created instances, etc.
   *
   * @param objClass the target class type of new instance.
   * @param context the populating context which may be used for object construction.
   * @return Constructed new instance of given class type.
   * @throws ConstructionStrategyException If some error occurs.
   */
  public <T> T constructObject(Class<T> objClass, PopulatingContext context) throws ConstructionStrategyException;

}
