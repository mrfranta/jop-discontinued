package cz.zcu.kiv.jop.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

public class NumberComparator implements Comparator<Number> {

  public NumberComparator() {}

  public Number min(Number x, Number y) {
    return compare(x, y) <= 0 ? x : y;
  }

  public Number max(Number x, Number y) {
    return compare(x, y) >= 0 ? x : y;
  }

  public int compare(Number x, Number y) {
    if (isSpecial(x) || isSpecial(y)) {
      return Double.compare(x.doubleValue(), y.doubleValue());
    }
    else {
      return toBigDecimal(x).compareTo(toBigDecimal(y));
    }
  }

  public static boolean isSpecial(Number x) {
    if (x instanceof Double && (Double.isNaN((Double)x) || Double.isInfinite((Double)x))) {
      return true;
    }

    if (x instanceof Float && (Float.isNaN((Float)x) || Float.isInfinite((Float)x))) {
      return true;
    }

    return false;
  }

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
