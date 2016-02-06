package cz.zcu.kiv.jop.util;

/**
 * Helper static class which provides default values for primitive or object types.
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">
 *      Primitive Data Types</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class Defaults {

  // These gets initialized to their default values
  /** Default value for primitive boolean type. */
  private static boolean DEFAULT_BOOLEAN;
  /** Default value for primitive byte type. */
  private static byte DEFAULT_BYTE;
  /** Default value for primitive character type. */
  private static char DEFAULT_CHAR;
  /** Default value for primitive short type. */
  private static short DEFAULT_SHORT;
  /** Default value for primitive integer type. */
  private static int DEFAULT_INT;
  /** Default value for primitive long type. */
  private static long DEFAULT_LONG;
  /** Default value for primitive float type. */
  private static float DEFAULT_FLOAT;
  /** Default value for primitive double type. */
  private static double DEFAULT_DOUBLE;
  /** Default value for primitive object type. */
  private static Object DEFAULT_OBJECT;

  /**
   * Private constructor in combination with abstract modifier of this class makes it static.
   */
  private Defaults() {}

  /**
   * Returns default value for given class type (<code>clazz</code>). If the given class type is
   * primitive, the default value of that type is returned. For object types is returned
   * <code>null</code>.
   *
   * @param clazz class type for which will be returned its default value.
   * @return Default value for given class type.
   */
  public static Object getDefaultValue(Class<?> clazz) {
    if (clazz.equals(boolean.class)) {
      return DEFAULT_BOOLEAN;
    }
    else if (clazz.equals(byte.class)) {
      return DEFAULT_BYTE;
    }
    else if (clazz.equals(char.class)) {
      return DEFAULT_CHAR;
    }
    else if (clazz.equals(short.class)) {
      return DEFAULT_SHORT;
    }
    else if (clazz.equals(int.class)) {
      return DEFAULT_INT;
    }
    else if (clazz.equals(long.class)) {
      return DEFAULT_LONG;
    }
    else if (clazz.equals(float.class)) {
      return DEFAULT_FLOAT;
    }
    else if (clazz.equals(double.class)) {
      return DEFAULT_DOUBLE;
    }
    else {
      return DEFAULT_OBJECT;
    }
  }

}
