package cz.zcu.kiv.jop.context;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.kiv.jop.session.PopulatingSession;

/**
 * Abstract implementation of {@link PopulatingContext} interface which contains currently populated
 * (generated) object and list of all already populated (generated) objects.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class AbstractPopulatingContext implements PopulatingContext {

  /**
   * List of already populated instances. This list is initialized during construction of context
   * because of separation from "live" session which may be adjusted by another contexts or cleared.
   */
  protected final List<Object> globalInstances;
  /** Local session of populated (generated) objects. */
  protected final PopulatingContextSession session;

  /**
   * Constructions abstract populating context with given sessions of populated objects.
   *
   * @param globalSession the instance of session which contains all already populated instances
   *          (may be <code>null</code>).
   * @param session the instance of local session of populated (generated) objects.
   */
  protected AbstractPopulatingContext(PopulatingSession globalSession, PopulatingContextSession session) {
    this.globalInstances = new ArrayList<Object>();

    if (globalSession != null) {
      this.globalInstances.addAll(globalSession.getPopulatedInstances()); // separate instances
    }

    this.session = session;
  }

  /**
   * Constructions abstract populating context with given session of populated objects and list of
   * already populated instances.
   *
   * @param globalInstances the list of populated instances out of this populating context or
   *          sub-context.
   * @param session the instance of local session of populated (generated) objects.
   */
  protected AbstractPopulatingContext(List<Object> globalInstances, PopulatingContextSession session) {
    this.globalInstances = globalInstances;
    this.session = session;
  }

  /**
   * Returns list of populated instances out of this populating context or sub-context.
   *
   * @return List of global instances.
   */
  List<Object> getGlobalInstances() {
    return globalInstances;
  }

  /**
   * Returns populating session of this populating context.
   *
   * @return Populating session of this populating context.
   */
  PopulatingContextSession getSession() {
    return session;
  }

  /**
   * {@inheritDoc}
   */
  public List<Object> getPopulatedInstances(boolean includeGlobalInstances) {
    List<Object> instances = null;
    if (session != null) {
      instances = session.getPopulatedInstances();
    }
    else {
      instances = new ArrayList<Object>();
    }

    if (includeGlobalInstances && globalInstances != null) {
      instances.addAll(globalInstances);
    }

    return instances;
  }
}
