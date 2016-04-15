package cz.zcu.kiv.jop.util;

/**
 * Helper class for determination of bound of some type (for example numeric type).
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Some class type for which was created bounds.
 */
public class Bounds<T> {

  /** The minimal value. */
  protected final T min;
  /** The maximal value. */
  protected final T max;

  /**
   * Constructs bounds.
   *
   * @param min the minimal value.
   * @param max the maximal value.
   */
  public Bounds(T min, T max) {
    this.min = min;
    this.max = max;
  }

  /**
   * Returns the minimal value.
   *
   * @return The minimal value.
   */
  public T getMin() {
    return min;
  }

  /**
   * Returns the maximal value.
   *
   * @return The maximal value.
   */
  public T getMax() {
    return max;
  }

  /**
   * Returns a hash code value for this bounds. This method is supported for the benefit of hash
   * tables such as those provided by {@link java.util.HashMap}.
   *
   * @return A hash code value for this bounds.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((max == null) ? 0 : max.hashCode());
    result = prime * result + ((min == null) ? 0 : min.hashCode());

    return result;
  }

  /**
   * Indicates whether some object is "equal to" this bounds.
   *
   * @param obj the reference object with which to compare.
   * @return <code>true</code> if this bounds is the same as the obj argument; <code>false</code>
   *         otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Bounds)) {
      return false;
    }

    Bounds<?> other = (Bounds<?>)obj;
    if (max == null) {
      if (other.max != null) {
        return false;
      }
    }
    else if (!max.equals(other.max)) {
      return false;
    }

    if (min == null) {
      if (other.min != null) {
        return false;
      }
    }
    else if (!min.equals(other.min)) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of bounds.
   *
   * @return String value of bounds.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [min=" + min + ", max=" + max + "]";
  }

}
