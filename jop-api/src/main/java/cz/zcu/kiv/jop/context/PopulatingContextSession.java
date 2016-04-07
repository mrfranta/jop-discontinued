package cz.zcu.kiv.jop.context;

import java.util.List;

import cz.zcu.kiv.jop.session.PopulatingSession;

/**
 * Extension of interface for {@link PopulatingSession}. This session serves primary for populating
 * context for storing already populated (generated) objects. This session supports operation
 * {@link #contains} which may be used for check whatever is some object already present in session.
 * Also this session should not support the {@link #clear} operation.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingContextSession extends PopulatingSession {

  /**
   * Returns information whether this session contains the given object.
   *
   * @param obj the instance for check.
   * @return <code>true</code> if the session contains given object; <code>false</code> otherwise.
   */
  public boolean contains(Object obj);

  /**
   * Returns list of instances populated only in scope of populating context
   *
   * @return List of populated instances within populating context.
   */
  public List<Object> getPopulatedInstances();

  /**
   * {@inheritDoc}
   * <p>
   * This operation should not be supported!
   */
  public void clear();

}
