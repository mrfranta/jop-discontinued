package cz.zcu.kiv.jop.util;

/**
 * Helper static class for wrapping/unwrapping of primitive class types.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class PrimitiveUtils {

  /**
   * Private constructor in combination with abstract modifier of this class makes it static.
   */
  private PrimitiveUtils() {}

  /** Constant for array of wrapper (object) types. */
  @SuppressWarnings("rawtypes")
  //@formatter:off
  private static final Class[] wrappers = {
      Integer.class,
      Double.class,
      Byte.class,
      Boolean.class,
      Character.class,
      Void.class,
      Short.class,
      Float.class,
      Long.class
  };
  // @formatter:on

  /** Constant for array of primitive types. */
  @SuppressWarnings("rawtypes")
  //@formatter:off
  private static final Class[] primitives = {
      int.class,
      double.class,
      byte.class,
      boolean.class,
      char.class,
      void.class,
      short.class,
      float.class,
      long.class
  };
  // @formatter:on

  /** Constant for abbreviations of primitive class types. */
  //@formatter:off
  private static final String[] abbreviations = {
      "I", // int
      "D", // double
      "B", // byte
      "Z", // boolean
      "C", // char
      "V", // void
      "S", // short
      "F", // float
      "J"  // long
  };
  // @formatter:on

  /**
   * Wraps class type of primitive type into class type for object type.
   *
   * @param clazz the primitive type to wrap.
   * @return Wrapped object type.
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> wrap(Class<T> clazz) {
    if (clazz == null) {
      return null;
    }

    if (!clazz.isPrimitive()) {
      return clazz;
    }

    String name = clazz.getName();
    int c0 = name.charAt(0);
    int c2 = name.charAt(2);
    int mapper = (c0 + c0 + c0 + 5) & (118 - c2);

    return wrappers[mapper];
  }

  /**
   * Unwraps class type of object type into class type for primitive type.
   *
   * @param clazz the object type to unwrap.
   * @return Unwrapped primitive type.
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> unwrap(Class<T> clazz) {
    if (clazz == null) {
      return null;
    }

    if (clazz.isPrimitive()) {
      return clazz;
    }

    String name = clazz.getName();
    int c10 = name.charAt(10);
    int c12 = name.charAt(12);
    int mapper = (c10 + c10 + c10 + 5) & (118 - c12);

    return primitives[mapper];
  }

  /**
   * Abbreviates name of given primitive class type. It returns <code>null</code> in case that given
   * class type is <code>null</code>. Also returns class name in case that it isn't primitive.
   *
   * @param clazz the primitive class type.
   * @return Abbreviation of given primitive class type; name of object class type or
   *         <code>null</code>.
   */
  public static String abbreviate(Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    String name = clazz.getName();
    if (!clazz.isPrimitive()) {
      return name;
    }

    int c0 = name.charAt(0);
    int c2 = name.charAt(2);
    int mapper = (c0 + c0 + c0 + 5) & (118 - c2);

    return abbreviations[mapper];
  }
}
