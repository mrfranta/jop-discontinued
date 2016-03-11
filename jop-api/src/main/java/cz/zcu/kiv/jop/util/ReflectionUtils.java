package cz.zcu.kiv.jop.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Helper static class for various reflection operations.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class ReflectionUtils {

  /**
   * Private constructor in combination with abstract modifier of this class makes it static.
   */
  private ReflectionUtils() {}

  /**
   * Returns information whether given <code>member</code> is accessible.
   *
   * @param member the member to check.
   * @return <code>true</code> if <code>member</code> is accessible; <code>false</code> otherwise.
   *
   * @see Member#getModifiers()
   */
  public static boolean isAccessible(Member member) {
    return (member != null && Modifier.isPublic(member.getModifiers()) && !member.isSynthetic());
  }

  /**
   * Creates a new instance of given class type in case that given class has declared parameterless
   * constructor. In case that some problem occurs during new instance creation the exception will
   * be thrown.
   *
   * @param clazz the class type for which will be created a new instance.
   * @return New instance of given class.
   * @throws ReflectionException If some error occurs during new instance creation or if there is no
   *           declared parameterless constructor in given class.
   */
  public static <T> T createInstance(Class<T> clazz) throws ReflectionException {
    Constructor<T> constructor = null;
    try {
      constructor = clazz.getDeclaredConstructor();
    }
    catch (Exception exc) {
      throw new ReflectionException("Cannot get declared parameterless constructor of " + clazz.getName(), exc);
    }

    return createInstance(constructor);
  }

  /**
   * Creates a new instance of given class type in case that given class has declared constructor
   * accepting given parameters. In case that some problem occurs during new instance creation the
   * exception will be thrown.
   *
   * @param clazz the class type for which will be created a new instance.
   * @param params variable argument for parameters of class constructor.
   * @return New instance of given class.
   * @throws ReflectionException If some error occurs during new instance creation or if there is no
   *           declared constructor matching given parameters in given class.
   */
  public static <T> T createInstance(Class<T> clazz, Object... params) throws ReflectionException {
    Constructor<T> constructor = null;
    try {
      Class<?>[] classes = null;
      if (params != null && params.length > 0) {
        classes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
          classes[i] = params[i].getClass();
        }
      }

      constructor = clazz.getDeclaredConstructor(classes);
    }
    catch (Exception exc) {
      // @formatter:off
      throw new ReflectionException("Cannot get declared constructor of " + clazz.getName() +
          " with given parameters: " + java.util.Arrays.toString(params), exc);
      // @formatter:on
    }

    return createInstance(constructor, params);
  }

  /**
   * Creates a new instance of object which owns given constructor in case that given constructor
   * accepts given parameters otherwise the exception will be thrown.
   *
   * @param constructor the constructor which will be used for creation of new instance.
   * @param params variable argument for parameters of given constructor.
   * @return New instance of owner of given constructor.
   * @throws ReflectionException If some error occurs during new instance creation or if given
   *           constructor doesn't accept given parameters.
   */
  public static <T> T createInstance(Constructor<T> constructor, Object... params) throws ReflectionException {
    if (!isAccessible(constructor)) {
      constructor.setAccessible(true);
    }

    try {
      return constructor.newInstance(params);
    }
    catch (Exception exc) {
      throw new ReflectionException("Cannot create new instance of " + constructor.getDeclaringClass().getName(), exc);
    }
  }

  /**
   * Returns declared field in given <code>clazz</code>. If the field was not found, it returns
   * <code>null</code>. Also if the field is not accessible, it marks the field as accessible.
   *
   * @param clazz the owner of searched declared field.
   * @param fieldName the name of searched declared field.
   * @return Declared field or <code>null</code>.
   */
  public static Field getDeclaredField(Class<?> clazz, String fieldName) {
    return getDeclaredField(clazz, fieldName, false);
  }

  /**
   * Returns declared field in given <code>clazz</code>. If the field is not found, it returns
   * <code>null</code>. Also if the field is not accessible and the parameter
   * <code>accessibleOnly</code> is set to <code>true</code>, it returns <code>null</code>;
   * otherwise it marks the field as accessible.
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
   * Returns method in given <code>clazz</code>. If the method was not found, it returns
   * <code>null</code>
   *
   * @param clazz the owner of searched method.
   * @param methodName the name of searched method.
   * @param parameterTypes array of parameter types of method parameters.
   * @return Method or <code>null</code>.
   */
  public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
    try {
      return clazz.getMethod(methodName, parameterTypes);
    }
    catch (Exception exc) {
      // ignore exception, the null will be returned
    }

    return null;
  }

  /**
   * Returns declared method in given <code>clazz</code>. If the method was not found, it returns
   * <code>null</code>. Also if the method is not accessible, it marks the method as accessible.
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

  /**
   * Returns information whether the class with given fully qualified name exists on classpath.
   *
   * @param className the fully qualified name of class.
   * @return <code>true</code> if class exists on classpath; <code>false</code> otherwise.
   */
  public static boolean isClassExist(String className) {
    try {
      // FIXME: There should be used some workaround for Reflection.getCallerClass()
      // Class.forName(className, false, ClassLoaderResolver.getCallerClassLoader());
      Class.forName(className);
      return true;
    }
    catch (ClassNotFoundException exc) {
      // class doesn't exist on classpath
      return false;
    }
  }

}
