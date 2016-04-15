package cz.zcu.kiv.jop.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * The general comparator of number values. This comparator allows to compare two different types of
 * numbers (for example {@link Double} and {@link Integer}).
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class NumberComparator implements Comparator<Number> {

  /**
   * Returns the smaller of two number values. If the arguments have the same value, the result is
   * that same value - its returned <code>x</code>.
   *
   * @param x the first number value to compare.
   * @param y the second number value to compare.
   * @return The smaller of <code>x</code> and <code>y</code>.
   */
  public Number min(Number x, Number y) {
    return compare(x, y) <= 0 ? x : y;
  }

  /**
   * Returns the larger of two number values. If the arguments have the same value, the result is
   * that same value - its returned <code>x</code>.
   *
   * @param x the first number value to compare.
   * @param y the second number value to compare.
   * @return The larger of <code>x</code> and <code>y</code>.
   */
  public Number max(Number x, Number y) {
    return compare(x, y) >= 0 ? x : y;
  }

  /**
   * Compares two number values and returns a negative integer, zero, or a positive integer as the
   * <code>x</code> is less than, equal to, or greater than <code>y</code>.
   *
   * @param x the first number value to compare.
   * @param y the second number value to compare.
   * @return A negative integer, zero, or a positive integer as <code>x</code> is less than, equal
   *         to, or greater than <code>y</code>.
   */
  public int compare(Number x, Number y) {
    if (isSpecial(x) || isSpecial(y)) {
      return Double.compare(x.doubleValue(), y.doubleValue());
    }
    else {
      return toBigDecimal(x).compareTo(toBigDecimal(y));
    }
  }

  /**
   * Returns information whether the entered number is special type of number (NaN or
   * positive/negative Infinite).
   *
   * @param number the number value to check.
   * @return <code>true</code> if given number value is special; <code>false</code> otherwise.
   *
   * @see Double#NaN
   * @see Double#POSITIVE_INFINITY
   * @see Double#NEGATIVE_INFINITY
   *
   * @see Float#NaN
   * @see Float#POSITIVE_INFINITY
   * @see Float#NEGATIVE_INFINITY
   */
  public static boolean isSpecial(Number number) {
    if (number instanceof Double && (Double.isNaN((Double)number) || Double.isInfinite((Double)number))) {
      return true;
    }

    if (number instanceof Float && (Float.isNaN((Float)number) || Float.isInfinite((Float)number))) {
      return true;
    }

    return false;
  }

  /**
   * Transforms given number value into {@link BigDecimal} value.
   *
   * @param number the number value to transform.
   * @return Transformed {@link BigDecimal} value from given number value.
   * @throws NumberFormatException If given number cannot be transformed into {@link BigDecimal}
   *           value.
   */
  public static BigDecimal toBigDecimal(Number number) throws NumberFormatException {
    if (number instanceof BigDecimal) {
      return (BigDecimal)number;
    }
    if (number instanceof BigInteger) {
      return new BigDecimal((BigInteger)number);
    }
    if (number instanceof Byte || number instanceof Short || number instanceof Integer || number instanceof Long) {
      return new BigDecimal(number.longValue());
    }
    if (number instanceof Float || number instanceof Double) {
      return new BigDecimal(number.doubleValue());
    }

    // otherwise
    try {
      return new BigDecimal(number.toString());
    }
    catch (NumberFormatException exc) {
      // re-throw exception
      throw new NumberFormatException("The given number (\"" + number + "\" of class " + number.getClass().getName()
          + ") does not have a parsable string representation");
    }
  }
}
