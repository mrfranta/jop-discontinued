package cz.zcu.kiv.jop.session;

import java.util.List;

/**
 * Common interface for session of populated (generated) objects. This session may be part of
 * populating context for storing already populated (generated) objects which may be used by
 * instance matcher(s). It also may be part of {@link cz.zcu.kiv.jop.ObjectPopulator
 * ObjectPopulator} which may store all populated (generated) objects.
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
   * Returns list of all populated (generated) instances.
   *
   * @return List of all populated instances.
   */
  public List<Object> getPopulatedInstances();

  /**
   * Clears the session. This operation is optional and may throw
   * {@link UnsupportedOperationException}.
   *
   * @throws UnsupportedOperationException If clear operation is not allowed.
   */
  public void clear();

}
