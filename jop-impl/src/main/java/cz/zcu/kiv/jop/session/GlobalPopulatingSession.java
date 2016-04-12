package cz.zcu.kiv.jop.session;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

/**
 * Implementation of {@link PopulatingSession} which may be used in implementation of
 * {@link cz.zcu.kiv.jop.ObjectPopulator ObjectPopulator} for storing all populated (generated)
 * objects.
 * <p>
 * This implementation of populating session supports method {@link #clear()}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class GlobalPopulatingSession implements PopulatingSession {

  /** List of stored instances in session. */
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
  public synchronized List<Object> getPopulatedInstances() {
    return new ArrayList<Object>(instances);
  }

  /**
   * {@inheritDoc}
   */
  public synchronized void clear() {
    instances.clear();
  }
}
