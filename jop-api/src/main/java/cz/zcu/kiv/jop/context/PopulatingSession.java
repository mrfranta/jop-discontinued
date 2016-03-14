package cz.zcu.kiv.jop.context;

import java.util.List;

/**
 * Interface for session of populated (generated) objects. The session may be part of populating
 * context for storing already populated (generated) objects which may be used by instance
 * matcher(s).
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingSession {

  /**
   * Adds (stores) given instance into session of populated instances.
   *
   * @param instance the instance to add (store).
   */
  public void addPopulatedInstance(Object instance);

  /**
   * Returns list of all populated (generated) instances by populator.
   *
   * @return List of all populated instances.
   */
  public List<Object> getPopulatedInstances();

}
