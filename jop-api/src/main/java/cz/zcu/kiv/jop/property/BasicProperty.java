package cz.zcu.kiv.jop.property;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * The basic implementation of <em>Object</em>'s property. It provides the getter and setter, which
 * use the methods for manipulation with property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Declared class type of property.
 */
public class BasicProperty<T> extends AbstractProperty<T> {

  /**
   * Determines if a de-serialized file is compatible with this class.
   * <p>
   * Maintainers must change this value if and only if the new version of this class is not
   * compatible with old versions. See Oracle docs for <a
   * href="http://docs.oracle.com/javase/1.5.0/docs/guide/serialization/">details</a>.
   * <p>
   * Not necessary to include in first version of the class, but included here as a reminder of its
   * importance.
   */
  private static final long serialVersionUID = 20160326L;

  /**
   * Constructs a basic property.
   *
   * @param declaringClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public BasicProperty(Class<?> declaringClass, String propertyName) {
    super(declaringClass, propertyName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  protected Getter<T> createGetter() throws GetterNotFoundException {
    Getter<T> result = (Getter<T>)getGetterOrNull(declaringClass, propertyName);
    if (result == null) {
      throw new GetterNotFoundException(declaringClass, propertyName);
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  protected Setter<T> createSetter() throws SetterNotFoundException {
    Setter<T> result = (Setter<T>)getSetterOrNull(declaringClass, propertyName);
    if (result == null) {
      throw new SetterNotFoundException(declaringClass, propertyName);
    }

    return result;
  }

  /**
   * Recursively searches for declared getter method for property in owner class and in all parent
   * classes or implemented interfaces. If the getter method was found, it returns the getter using
   * found method.
   *
   * @param clazz the class type of a property owner.
   * @param propertyName the name of property.
   * @return Getter for property or <code>null</code>.
   */
  protected static Getter<?> getGetterOrNull(Class<?> clazz, String propertyName) {
    if (clazz == Object.class || clazz == null) {
      return null;
    }

    Method method = getGetterMethod(clazz, propertyName);
    if (method != null) {
      if (!ReflectionUtils.isAccessible(method)) {
        method.setAccessible(true);
      }

      return new BasicGetter<Object>(propertyName, method);
    }

    Getter<?> getter = getGetterOrNull(clazz.getSuperclass(), propertyName);
    if (getter == null) {
      Class<?>[] interfaces = clazz.getInterfaces();
      for (int i = 0; getter == null && i < interfaces.length; i++) {
        getter = getGetterOrNull(interfaces[i], propertyName);
      }
    }

    return getter;
  }

  /**
   * Searches for declared getter method for property.
   *
   * @param clazz the class type of a property owner.
   * @param propertyName the name of property.
   * @return Found getter method for property or <code>null</code>.
   */
  protected static Method getGetterMethod(Class<?> clazz, String propertyName) {
    Method[] methods = clazz.getDeclaredMethods();
    for (Method method : methods) {
      // if the method has parameters, skip it
      if (method.getParameterTypes().length != 0) {
        continue;
      }

      // if the method is a "bridge", skip it
      if (method.isBridge()) {
        continue;
      }

      final String methodName = method.getName();

      // try "get"
      if (methodName.startsWith("get")) {
        String testStdMethod = Introspector.decapitalize(methodName.substring(3));
        String testOldMethod = methodName.substring(3);
        if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
          return method;
        }
      }

      // if not "get", then try "is"
      if (methodName.startsWith("is")) {
        String testStdMethod = Introspector.decapitalize(methodName.substring(2));
        String testOldMethod = methodName.substring(2);
        if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
          return method;
        }
      }
    }

    return null;
  }

  /**
   * Recursively searches for declared setter method for property in owner class and in all parent
   * classes or implemented interfaces. If the setter method was found, it returns the setter using
   * found method.
   *
   * @param clazz the class type of a property owner.
   * @param propertyName the name of property.
   * @return Setter for property or <code>null</code>.
   */
  protected static Setter<?> getSetterOrNull(Class<?> clazz, String propertyName) {
    if (clazz == Object.class || clazz == null) {
      return null;
    }

    Method method = getSetterMethod(clazz, propertyName);

    if (method != null) {
      if (!ReflectionUtils.isAccessible(method)) {
        method.setAccessible(true);
      }

      return new BasicSetter<Object>(propertyName, method);
    }

    Setter<?> setter = getSetterOrNull(clazz.getSuperclass(), propertyName);
    if (setter == null) {
      Class<?>[] interfaces = clazz.getInterfaces();
      for (int i = 0; setter == null && i < interfaces.length; i++) {
        setter = getSetterOrNull(interfaces[i], propertyName);
      }
    }

    return setter;
  }

  /**
   * Searches for declared setter method for property.
   *
   * @param clazz the class type of a property owner.
   * @param propertyName the name of property.
   * @return Found setter method for property or <code>null</code>.
   */
  protected static Method getSetterMethod(Class<?> clazz, String propertyName) {
    Getter<?> getter = getGetterOrNull(clazz, propertyName);
    Class<?> returnType = (getter == null) ? null : getter.getType();

    Method[] methods = clazz.getDeclaredMethods();
    Method potentialSetter = null;
    for (Method method : methods) {
      final String methodName = method.getName();

      if (method.getParameterTypes().length == 1 && methodName.startsWith("set")) {
        String testStdMethod = Introspector.decapitalize(methodName.substring(3));
        String testOldMethod = methodName.substring(3);
        if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
          potentialSetter = method;
          if (returnType == null || method.getParameterTypes()[0].equals(returnType)) {
            return potentialSetter;
          }
        }
      }
    }

    return potentialSetter;
  }

  /**
   * Basic implementation of {@code Getter} interface which uses getter {@link Method} for access to
   * property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   *
   * @param <T> Declared class type of property.
   */
  public static class BasicGetter<T> extends AbstractPropertyAccess<T, Method> implements Getter<T> {

    /**
     * Determines if a de-serialized file is compatible with this class.
     * <p>
     * Maintainers must change this value if and only if the new version of this class is not
     * compatible with old versions. See Oracle docs for <a
     * href="http://docs.oracle.com/javase/1.5.0/docs/guide/serialization/">details</a>.
     * <p>
     * Not necessary to include in first version of the class, but included here as a reminder of
     * its importance.
     */
    private static final long serialVersionUID = 20160326L;

    /** Logger used for logging. */
    private static final Log logger = LogFactory.getLog(BasicGetter.class);

    /**
     * Constructs a basic getter.
     *
     * @param propertyName the name of property.
     * @param method the method which will be used for access to property.
     */
    BasicGetter(String propertyName, Method method) {
      super(propertyName, method);
    }

    /**
     * {@inheritDoc}
     */
    public Class<?> getType() {
      return member.getReturnType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Method getMethod() {
      return member;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMethodName() {
      return member.getName();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(Object owner) throws PropertyAccessException {
      try {
        return (T)member.invoke(owner);
      }
      catch (NullPointerException exc) {
        throw createGetterAccessException("Static call of non-static", exc);
      }
      catch (InvocationTargetException exc) {
        throw createGetterAccessException("Exception occurred inside", exc);
      }
      catch (IllegalAccessException exc) {
        // shouldn't occur
        throw createGetterAccessException("Illegal access occured during call of", exc);
      }
      catch (IllegalArgumentException exc) {
        String ownerName = (owner == null) ? null : owner.getClass().getName();
        logger.error("Given incorrect owner '" + ownerName + "' for getter of property: " + getDeclaringClassName() + '.' + getPropertyName());
        throw createGetterAccessException("Given incorrect owner for calling", exc);
      }
      catch (Exception exc) {
        throw createGetterAccessException("An exception occured during call of", exc);
      }
    }
  }

  /**
   * Basic implementation of {@code Setter} interface which uses setter {@link Method} for access to
   * property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   *
   * @param <T> Declared class type of property.
   */
  public static class BasicSetter<T> extends AbstractPropertyAccess<T, Method> implements Setter<T> {

    /**
     * Determines if a de-serialized file is compatible with this class.
     * <p>
     * Maintainers must change this value if and only if the new version of this class is not
     * compatible with old versions. See Oracle docs for <a
     * href="http://docs.oracle.com/javase/1.5.0/docs/guide/serialization/">details</a>.
     * <p>
     * Not necessary to include in first version of the class, but included here as a reminder of
     * its importance.
     */
    private static final long serialVersionUID = 20160326L;

    /** Logger used for logging. */
    private static final Log logger = LogFactory.getLog(BasicSetter.class);

    /**
     * Constructs a basic setter.
     *
     * @param propertyName the name of property.
     * @param method the method which will be used for access to property.
     */
    BasicSetter(String propertyName, Method method) {
      super(propertyName, method);
    }

    /**
     * {@inheritDoc}
     */
    public Class<?> getType() {
      return member.getParameterTypes()[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Method getMethod() {
      return member;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMethodName() {
      return member.getName();
    }

    /**
     * {@inheritDoc}
     */
    public void set(Object owner, T value) throws PropertyAccessException {
      try {
        member.invoke(owner, value);
      }
      catch (NullPointerException exc) {
        if (owner == null) {
          throw createGetterAccessException("Static call of non-static", exc);
        }
        else {
          throw createGetterAccessException("Null pointer exception occured inside", exc);
        }
      }
      catch (InvocationTargetException exc) {
        throw createGetterAccessException("Exception occurred inside", exc);
      }
      catch (IllegalAccessException exc) {
        // shouldn't occur
        throw createGetterAccessException("Illegal access occured during call of", exc);
      }
      catch (IllegalArgumentException exc) {
        if (value == null && getType().isPrimitive()) {
          throw createSetterAccessException("Null value was assigned to a property of primitive type while calling", exc);
        }
        else if (owner != null && !getDeclaringClass().isAssignableFrom(owner.getClass())) {
          String ownerName = owner.getClass().getName();
          logger.error("Given incorrect owner '" + ownerName + "' for setter of property: " + getDeclaringClassName() + '.' + getPropertyName());
          throw createGetterAccessException("Given incorrect owner for calling", exc);
        }
        else {
          logger.error("Given incorrect value type for setter of property: " + getDeclaringClassName() + '.' + getPropertyName()
              + " expected type: " + getType().getName() + ", given value type: " + (value == null ? null : value.getClass().getName()));
          throw createSetterAccessException("Given incorrect value type for", exc);
        }
      }
      catch (Exception exc) {
        throw createGetterAccessException("An exception occured during call of", exc);
      }
    }
  }
}
