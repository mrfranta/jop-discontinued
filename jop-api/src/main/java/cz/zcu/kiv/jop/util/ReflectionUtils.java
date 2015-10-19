package cz.zcu.kiv.jop.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Helper static class for various reflection operations.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public abstract class ReflectionUtils {

  /**
   * Private constructor in combination with abstract modifier of this class
   * makes it static.
   */
  private ReflectionUtils() {}

  /**
   * Returns information whether given <code>member</code> is accessible.
   *
   * @param member the member to check.
   * @return <code>true</code> if <code>member</code> is accessible;
   *         <code>false</code> otherwise.
   *
   * @see Member#getModifiers()
   */
  public static boolean isAccessible(Member member) {
    return (member != null && Modifier.isPublic(member.getModifiers()) && !member.isSynthetic());
  }

  /**
   * Returns declared field in given <code>clazz</code>. If the field is not
   * found, it returns <code>null</code>. Also if the field is not accessible,
   * it marks the field as accessible.
   *
   * @param clazz the owner of searched declared field.
   * @param fieldName the name of searched declared field.
   * @return Declared field or <code>null</code>.
   */
  public static Field getDeclaredField(Class<?> clazz, String fieldName) {
    return getDeclaredField(clazz, fieldName, false);
  }

  /**
   * Returns declared field in given <code>clazz</code>. If the field is not
   * found, it returns <code>null</code>. Also if the field is not accessible
   * and the parameter <code>accessibleOnly</code> is set to <code>true</code>,
   * it returns <code>null</code>; otherwise it marks the field as accessible.
   *
   * @param clazz the owner of searched declared field.
   * @param fieldName the name of searched declared field.
   * @param accessibleOnly whatever the declared field has to be accessible.
   * @return Declared field or <code>null</code>.
   *
   * @see #isAccessible(Member)
   */
  public static Field getDeclaredField(Class<?> clazz, String fieldName, boolean accessibleOnly) {
    try {
      Field field = clazz.getDeclaredField(fieldName);
      if (!isAccessible(field)) {
        if (accessibleOnly) {
          return null;
        }
        else {
          field.setAccessible(true);
        }
      }

      return field;
    }
    catch (NoSuchFieldException exc) {
      // ignore exception, the null will be returned
    }

    return null;
  }

  /**
   * Returns declared field in given <code>clazz</code>. If the field is not
   * found, it returns <code>null</code>. Also if the field is not accessible,
   * it marks the field as accessible.
   *
   * @param clazz the owner of searched declared method.
   * @param methodName the name of searched declared method.
   * @param parameterTypes array of parameter types of method parameters.
   * @return Declared method or <code>null</code>.
   *
   * @see #isAccessible(Member)
   */
  public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    try {
      Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
      if (!isAccessible(method)) {
        method.setAccessible(true);
      }

      return method;
    }
    catch (Exception exc) {
      // ignore exception, the null will be returned
    }

    return null;
  }

}
