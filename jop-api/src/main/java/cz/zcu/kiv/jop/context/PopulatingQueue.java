package cz.zcu.kiv.jop.context;

import java.util.List;

import cz.zcu.kiv.jop.bean.Bean;

/**
 * Interface for populating queue which contains the bean descriptors of dependencies which will be
 * populated. This queue may be part of {@link PopulatingContext} which enqueues dependencies into
 * queue. These dependencies are dequeued by {@link cz.zcu.kiv.jop.ObjectPopulator ObjectPopulator}
 * for population.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingQueue {

  /**
   * Tries to enqueue given bean descriptor of dependency into queue and returns information whether
   * was given class enqueued successfully. The bean descriptor has to contains the instance of
   * described object.
   *
   * @param dependency the bean descriptor of dependency to enqueue.
   * @return <code>true</code> if given dependency was enqueued; <code>false</code> otherwise.
   */
  public boolean enqueue(Bean dependency);

  /**
   * Removes and returns the bean descriptor of first dependency in queue.
   *
   * @return The bean descriptor of first dependency in queue.
   */
  public Bean dequeue();

  /**
   * Returns the bean descriptor of first dependency in queue without removal.
   *
   * @return The bean descriptor of first dependency in queue.
   */
  public Bean front();

  /**
   * Returns size of queue - number of dependencies.
   *
   * @return Size of populating queue.
   */
  public int size();

  /**
   * Returns information whether the populating queue is empty (contains no elements; size of queue
   * is equals to 0).
   *
   * @return <code>true</code> if queue is empty; <code>false</code> otherwise.
   */
  public boolean isEmpty();

  /**
   * Return list of bean descriptors of all dependencies waiting for population.
   *
   * @return List of bean descriptors of dependencies waiting for population.
   */
  public List<Bean> values();

}
