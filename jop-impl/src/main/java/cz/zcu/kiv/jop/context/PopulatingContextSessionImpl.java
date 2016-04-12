package cz.zcu.kiv.jop.context;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link PopulatingContextSession}. This session stores already populated
 * (generated) objects by some populating context. It also supports operation {@link #contains}
 * which may be used for check whatever is some object already present in session. Also this session
 * doesn't support the {@link #clear} operation.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class PopulatingContextSessionImpl implements PopulatingContextSession {

  /** List of stored populated instances in session. */
  protected final List<Object> instances = new ArrayList<Object>();

  /**
   * {@inheritDoc}
   */
  public synchronized void addPopulatedInstance(Object instance) {
    instances.add(instance);
  }

  /**
   * {@inheritDoc}
   */
  public synchronized boolean contains(Object obj) {
    for (Object instance : instances) {
      if (instance == obj) { // do not use the equals method!!!
        return true;
      }
    }

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public synchronized List<Object> getPopulatedInstances() {
    return new ArrayList<Object>(instances);
  }

  /**
   * This operation is not allowed and throws {@link UnsupportedOperationException}.
   *
   * @throws UnsupportedOperationException For each call of this method.
   */
  public void clear() {
    throw new UnsupportedOperationException("Clear operation is not allowed");
  }
}
