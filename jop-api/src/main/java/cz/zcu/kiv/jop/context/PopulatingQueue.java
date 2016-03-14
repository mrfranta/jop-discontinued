package cz.zcu.kiv.jop.context;

import java.util.List;

/**
 * Interface for populating queue which contains the class types which was or will be populated. The
 * queue contains classes (dependencies) to populate. This queue may be part of
 * {@link PopulatingContext} which enqueues classes (dependencies) into queue and then are dequeued
 * by {@link cz.zcu.kiv.jop.ObjectPopulator ObjectPopulator} to populate.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingQueue {

  /**
   * Tries to enqueue given (dependency) class into queue and returns information whether was given
   * class enqueued successfully.
   *
   * @param dependency the (dependency) class to enqueue.
   * @return <code>true</code> if given (dependency) class was enqueued; <code>false</code>
   *         otherwise.
   */
  public boolean enqueue(Class<?> dependency);

  /**
   * Removes and returns the first class (dependency) in queue.
   *
   * @return The first class in queue.
   */
  public Class<?> dequeue();

  /**
   * Returns the first class (dependency) in queue without removal.
   *
   * @return The first class in queue.
   */
  public Class<?> front();

  /**
   * Returns size of queue - number of classes (dependencies).
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
   * Return list of all classes (dependencies) in queue.
   *
   * @return List of classes in queue.
   */
  public List<Class<?>> values();

}
