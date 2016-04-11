package cz.zcu.kiv.jop.context;

import cz.zcu.kiv.jop.bean.Bean;

/**
 * Interface for iterator through the populating queue. This iterator is not a standard iterator
 * because call of method {@link #front()} doesn't not move with iterator and call of method
 * {@link #next()} affects the content of queue.
 * <p>
 * The call of method {@link #front()} should return for each call the bean descriptor of currently
 * populated object without removal. For movement to next element in queue is required call of
 * method {@link #next()}. For checking whether the queue contains any bean descriptor (in the
 * front) should be used method {@link #hasFront()}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingQueueIterator {

  /**
   * Returns information whether the populating queue contains any bean descriptor of currently
   * populated object (in other words returns <code>true</code> if call of method {@link #front()}
   * returns some object or <code>false</code> in case that queue contains no more elements and call
   * of method {@link #front()} returns <code>null</code>).
   *
   * @return <code>true</code> if populating queue contains any bean descriptor (in front);
   *         <code>false</code> otherwise.
   */
  public boolean hasFront();

  /**
   * Returns bean descriptor of currently populated object in populating queue without removal. This
   * method may return <code>null</code> in case that there is no object in front of populating
   * queue.
   *
   * @return Bean descriptor of currently populated object or <code>null</code> if there is no bean
   *         descriptor in front of populating queue (queue is empty).
   */
  public Bean front();

  /**
   * Removes and returns bean descriptor of currently populated object in populating queue (iterator
   * moves to next bean descriptor in queue). This method may return <code>null</code> in case that
   * there is no object in front of populating queue.
   *
   * @return Bean descriptor of removed currently populated object or <code>null</code> if there is
   *         no bean descriptor in front of populating queue (queue is empty).
   */
  public Bean next();

}
