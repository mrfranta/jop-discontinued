package cz.zcu.kiv.jop.util;

public class Bounds<T> {

  protected final T min;
  protected final T max;

  public Bounds(T min, T max) {
    this.min = min;
    this.max = max;
  }

  public T getMin() {
    return min;
  }

  public T getMax() {
    return max;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((max == null) ? 0 : max.hashCode());
    result = prime * result + ((min == null) ? 0 : min.hashCode());
    return result;
  }

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

  @Override
  public String toString() {
    return getClass().getName() + " [min=" + min + ", max=" + max + "]";
  }

}
