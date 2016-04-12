package cz.zcu.kiv.jop.context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cz.zcu.kiv.jop.bean.Bean;

/**
 * Implementation of populating queue which contains the bean descriptors of dependencies which will
 * be populated. This queue may be part of {@link PopulatingContext} which enqueues dependencies
 * into queue. These dependencies are dequeued by {@link cz.zcu.kiv.jop.ObjectPopulator
 * ObjectPopulator} for population.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class PopulatingQueueImpl implements PopulatingQueue {

  /** Implementation of queue used for populating queue. */
  private final Queue<Bean> queue = new LinkedList<Bean>();

  /**
   * {@inheritDoc}
   */
  public boolean enqueue(Bean dependency) {
    // invalid dependencies
    if (dependency == null || dependency.getInstance() == null) {
      return false;
    }

    return queue.offer(dependency);
  }

  /**
   * {@inheritDoc}
   */
  public Bean dequeue() {
    return queue.poll();
  }

  /**
   * {@inheritDoc}
   */
  public Bean front() {
    return queue.peek();
  }

  /**
   * {@inheritDoc}
   */
  public int size() {
    return queue.size();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  public List<Bean> values() {
    return new ArrayList<Bean>(queue);
  }
}
