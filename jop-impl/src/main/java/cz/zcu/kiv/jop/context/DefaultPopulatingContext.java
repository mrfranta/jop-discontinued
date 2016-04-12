package cz.zcu.kiv.jop.context;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.bean.JopBean;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.session.PopulatingSession;
import cz.zcu.kiv.jop.strategy.PopulatingStrategy;
import cz.zcu.kiv.jop.strategy.PopulatingStrategyException;

/**
 * Default implementation of populating context which stores all required information about current
 * ongoing population of some object. Context contains currently populated (generated) object and
 * list of all already populated (generated) objects. It also contains the queue of lazy populating
 * strategies invocation.
 * <p>
 * The context should contain the {@link DependencyGraph} for preventing cyclic dependencies, the
 * {@link PopulatingQueue} for enqueuing dependencies to generate and populate and the
 * {@link PopulatingSession} which stores the already populated (generated) objects.
 * <p>
 * This context should be created for each call of method <code>populate</code> of
 * {@link cz.zcu.kiv.jop.ObjectPopulator ObjectPopulator}. Context should not be shared with other
 * calls of <code>populate</code> methods (except the session which may share instances between
 * calls) and it should not be in IoC context because it should be handled exclusively by populator.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class DefaultPopulatingContext extends AbstractPopulatingContext {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(DefaultPopulatingContext.class);

  /** Graph of dependencies between populated objects. */
  protected final DependencyGraph dependencyGraph;
  /** Populating queue which contains the bean descriptors of dependencies which will be populated. */
  protected final PopulatingQueue populatingQueue;

  /** List of invocations of lazy populating strategies. */
  protected final List<LazyPopulatingStrategyInvocation> lazyStrategies;

  /**
   * Constructs empty default populating context.
   *
   * @param globalSession the populating session which contains already generated objects.
   */
  public DefaultPopulatingContext(PopulatingSession globalSession) {
    this(new DependencyGraphImpl(), new PopulatingQueueImpl(), globalSession, new PopulatingContextSessionImpl());
  }

  /**
   * Constructs default populating context from given parts.
   *
   * @param graph the graph of dependencies between populated objects.
   * @param queue the populating queue which contains the bean descriptors of dependencies which
   *          will be populated.
   * @param globalSession the instance of session which contains all already populated instances
   *          (may be <code>null</code>).
   * @param session the instance of local session of populated (generated) objects.
   */
  protected DefaultPopulatingContext(DependencyGraph graph, PopulatingQueue queue, PopulatingSession globalSession, PopulatingContextSession session) {
    super(globalSession, session);

    dependencyGraph = graph;
    populatingQueue = queue;

    lazyStrategies = new ArrayList<LazyPopulatingStrategyInvocation>();
  }

  /**
   * Constructs default populating context from given parts.
   *
   * @param graph the graph of dependencies between populated objects.
   * @param queue the populating queue which contains the bean descriptors of dependencies which
   *          will be populated.
   * @param globalInstances the list of populated instances out of this populating context or
   *          sub-context.
   * @param session the instance of local session of populated (generated) objects.
   */
  protected DefaultPopulatingContext(DependencyGraph graph, PopulatingQueue queue, List<Object> globalInstances, PopulatingContextSession session) {
    super(globalInstances, session);

    dependencyGraph = graph;
    populatingQueue = queue;

    lazyStrategies = new ArrayList<LazyPopulatingStrategyInvocation>();
  }

  /**
   * Returns graph of dependencies between populated objects.
   *
   * @return Dependencies graph of this populating context.
   */
  DependencyGraph getDependencyGraph() {
    return dependencyGraph;
  }

  /**
   * Returns populating queue which contains the bean descriptors of dependencies which will be
   * populated.
   *
   * @return Populating queue of this populating context.
   */
  PopulatingQueue getPopulatingQueue() {
    return populatingQueue;
  }

  /**
   * Returns list of invocations of lazy populating strategies.
   *
   * @return List of invocations of lazy populating strategies.
   */
  List<LazyPopulatingStrategyInvocation> getLazyStrategies() {
    return lazyStrategies;
  }

  /**
   * {@inheritDoc}
   */
  public Object getCurrentObject() {
    Bean currentBean = getCurrentBean();

    return currentBean == null ? null : currentBean.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  public Bean getCurrentBean() {
    return populatingQueue.front();
  }

  /**
   * {@inheritDoc}
   */
  public boolean canPopulate(Class<?> dependency) {
    if (dependencyGraph == null) {
      return false; // no dependency graph available, not possible to populate
    }

    // dependency class type is not supported for JopBean
    if (!JopBean.isSupported(dependency)) {
      return false;
    }

    // TODO: check in graph

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean populate(Object dependency) {
    if (dependency == null || !canPopulate(dependency.getClass())) {
      return false;
    }

    // object was already populated? do not enqueue it...
    if (session != null && session.contains(dependency)) {
      return false;
    }

    return populatingQueue.enqueue(new JopBean(dependency));
  }

  /**
   * {@inheritDoc}
   */
  public boolean populate(Bean dependency) {
    if (dependency == null || dependency.getInstance() == null || !canPopulate(dependency.getType())) {
      return false;
    }

    // object was already populated? do not enqueue it...
    if (session.contains(dependency.getInstance())) {
      return false;
    }

    return populatingQueue.enqueue(dependency);
  }

  /**
   * {@inheritDoc}
   */
  public void addLazyPopulatingStrategyInvocation(Property<?> property, PopulatingStrategy strategy) throws PopulatingStrategyException {
    logger.debug("Added lazy populating strategy: " + strategy.getClass().getName() + "; for property: " + property);
    lazyStrategies.add(new LazyPopulatingStrategyInvocation(property, getCurrentBean(), strategy));
  }

  /**
   * {@inheritDoc}
   */
  public PopulatingContext createConstructionContext(Bean bean) {
    return new ConstructionPopulatingContext(bean, dependencyGraph, populatingQueue, globalInstances, session);
  }
}
