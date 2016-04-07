package cz.zcu.kiv.jop.context;

import java.util.Iterator;

import cz.zcu.kiv.jop.bean.Bean;

/**
 * Interface for populating context handler which contains the actual populating context and
 * provides extended operations for context which cannot be in the {@link PopulatingContext}
 * interface. It serves primary for {@link cz.zcu.kiv.jop.ObjectPopulator ObjectPopulator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingContextHandler {

  /**
   * Returns iterator for iterating bean descriptors in populating queue.
   *
   * @return Iterator for iterating populating queue.
   */
  public Iterator<Bean> getPopulatingQueueIterator();

  /**
   * Stores given instance into session of populated instances.
   *
   * @param instance the instance to add (store).
   */
  public void addPopulatedInstance(Object instance);

  /**
   * Returns populating context handled by this handler.
   *
   * @return Populating context.
   */
  public PopulatingContext getPopulatingContext();

}
