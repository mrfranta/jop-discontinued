package cz.zcu.kiv.jop.context;

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
   * Returns populating context handled by this handler.
   *
   * @return Populating context.
   */
  public PopulatingContext getPopulatingContext();

  /**
   * Sets root bean descriptor of object for which started the population into populating context.
   *
   * @param rootBean the root bean to set.
   */
  public void setRootBean(Bean rootBean);

  /**
   * Returns iterator for iterating through the populating queue of handled populating context.
   *
   * @return Iterator for populating queue of handled populating context.
   */
  public PopulatingQueueIterator getPopulatingQueueIterator();

  /**
   * Stores given instance into session of populated instances.
   *
   * @param instance the instance to add (store).
   */
  public void addPopulatedInstance(Object instance);

  /**
   * Invokes all lazy populating strategies which was added into handled populating context.
   *
   * @throws PopulatingContextException If some error occurs during invocation of lazy populating
   *           strategies.
   */
  public void invokeLazyPopulatingStrategies() throws PopulatingContextException;

}
