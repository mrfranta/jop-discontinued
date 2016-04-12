package cz.zcu.kiv.jop.context;

import java.util.List;

import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.session.PopulatingSession;
import cz.zcu.kiv.jop.strategy.PopulatingStrategyException;

/**
 * Implementation of {@link PopulatingContextHandler} interface which contains actual populating
 * context, session of handled populating context which stores populated (generated) objects and
 * iterator through the populating queue of handled populating context.
 * <p>
 * Handler of populating context provides extended operations for context which cannot be in the
 * {@link PopulatingContext} interface. It serves primary for {@link cz.zcu.kiv.jop.ObjectPopulator
 * ObjectPopulator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class PopulatingContextHandlerImpl implements PopulatingContextHandler {

  /** Instance of handled populating context. */
  protected final DefaultPopulatingContext context;
  /** Iterator through the populating queue of handled populating context. */
  protected final PopulatingQueueIterator queueIterator;

  /**
   * Constructs new handler of populating context.
   *
   * @param populateDependencies information whether may be populated dependencies of
   * @param globalSession the instance of session which contains all already populated instances.
   */
  public PopulatingContextHandlerImpl(boolean populateDependencies, PopulatingSession globalSession) {
    // prepare propriety context
    if (populateDependencies) {
      context = new DefaultPopulatingContext(globalSession);
    }
    else {
      context = new SinglePopulatingContext();
    }

    // prepare iterator for populating queue
    queueIterator = new PopulatingQueueIteratorImpl(context.getPopulatingQueue());
  }

  /**
   * {@inheritDoc}
   */
  public PopulatingContext getPopulatingContext() {
    return context;
  }

  /**
   * {@inheritDoc}
   */
  public void setRootBean(Bean rootBean) {
    context.getPopulatingQueue().enqueue(rootBean);
  }

  /**
   * {@inheritDoc}
   */
  public PopulatingQueueIterator getPopulatingQueueIterator() {
    return queueIterator;
  }

  /**
   * {@inheritDoc}
   */
  public void addPopulatedInstance(Object instance) {
    PopulatingContextSession session = context.getSession();
    if (session != null) {
      session.addPopulatedInstance(instance);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void invokeLazyPopulatingStrategies() throws PopulatingContextException {
    List<LazyPopulatingStrategyInvocation> lazyPopulatingStrategies = context.getLazyStrategies();
    if (lazyPopulatingStrategies == null || lazyPopulatingStrategies.isEmpty()) {
      return; // no lazy populating strategies
    }

    // prepare context
    LazyPopulatingStrategyPopulatingContext lazyContext = new LazyPopulatingStrategyPopulatingContext(null, context.getGlobalInstances(), context.getSession());
    try {
      // invoke all lazy population strategies
      for (LazyPopulatingStrategyInvocation lazyPopulatingStrategy : lazyPopulatingStrategies) {
        lazyContext.setCurrentBean(lazyPopulatingStrategy.getOwner());
        lazyPopulatingStrategy.applyStrategy(lazyContext);
      }
    }
    catch (PopulatingStrategyException exc) {
      throw new PopulatingContextException("Invocation of lazy populating strategy failed", exc);
    }
  }

  /**
   * Implementation of iterator through the populating queue of handled populating context. This
   * iterator may be used by {@link cz.zcu.kiv.jop.ObjectPopulator ObjectPopulator} for iteration
   * through all bean descriptors of objects waiting for population.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   */
  protected static class PopulatingQueueIteratorImpl implements PopulatingQueueIterator {

    /**
     * Populating queue of handled populating context which contains the bean descriptors of
     * dependencies which will be populated.
     */
    protected final PopulatingQueue queue;

    /**
     * Constructs new iterator for given queue.
     *
     * @param queue the populating queue which will be iterated.
     */
    public PopulatingQueueIteratorImpl(PopulatingQueue queue) {
      this.queue = queue;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasFront() {
      return !queue.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    public Bean front() {
      return queue.front();
    }

    /**
     * {@inheritDoc}
     */
    public Bean next() {
      return queue.dequeue();
    }
  }
}
