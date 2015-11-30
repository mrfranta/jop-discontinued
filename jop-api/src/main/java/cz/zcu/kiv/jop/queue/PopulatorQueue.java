package cz.zcu.kiv.jop.queue;

import java.util.List;

import cz.zcu.kiv.jop.dependency.DependencyGraph;

/**
 * Interface for populator queue which contains the class types which was or
 * will be populated. This queue stores already populated (generated) objects,
 * their dependencies and queue of class types which will be populated.
 * <p>
 * The class types can be enqueued by populator or from interface
 * {@link cz.zcu.kiv.jop.strategy.GeneratingStrategy GeneratingStrategy}. In
 * that interface also can be obtained list of already populated (generated)
 * instances.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface PopulatorQueue {

  /**
   * Returns actual populated dependency (its at front of queue). If there is no
   * dependency in the front of queue, it returns <code>null</code>.
   *
   * @return Actual populated dependency or <code>null</code>.
   */
  public Class<?> front();

  /**
   * Checks whatever the given dependency (of owner returned by {@link #front()
   * method}) can be enqueued into queue of populator. It will be checked
   * whatever the given dependency don't create cycle in graph (tree) of
   * dependencies.
   *
   * @param dependency the dependency to enqueue.
   * @return <code>true</code> if given dependency can be enqueued;
   *         <code>false</code> otherwise.
   */
  public boolean canEnqueue(Class<?> dependency);

  /**
   * Tries to enqueue the given dependency (of owner returned by
   * {@link #front() method}) to populate. This method calls {@link #canEnqueue}
   * method which decides whatever the given dependency can be enqueued.
   *
   * @param dependency the dependency to enqueue.
   * @return <code>true</code> if given dependency was enqueued;
   *         <code>false</code> otherwise.
   */
  public boolean enqueue(Class<?> dependency);

  /**
   * Graph (tree) of enqueued objects and their relations between each other.
   * Each object can be related (dependent) on any number of dependencies but it
   * cannot create cycle (example: A -&gt; B -&gt; A). Because of that this
   * graph can be also called as tree.
   * <p>
   * The whole graph can be generated at beginning of populating or it can be
   * generated during the populating.
   *
   * @return Graph (tree) of enqueued objects and their relations between each
   *         other.
   */
  public DependencyGraph getDependencyGraph();

  /**
   * Returns list of all populated (generated) instances by populator.
   *
   * @return List of all populated instances.
   */
  public List<Object> getPopulatedInstances();

  /**
   * Returns list of populated (generated) instances by poupulator which are
   * filtered by given type. The list contains only instances which has same
   * type as given type or if their type extends or implements the given type.
   *
   * @param type the class type of populated instances.
   * @return Filtered list of populated (generated) instances by poupulator.
   */
  public <T> List<T> getPopulatedInstances(Class<T> type);

}
