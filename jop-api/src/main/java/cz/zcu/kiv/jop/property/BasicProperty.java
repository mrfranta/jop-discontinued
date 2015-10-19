package cz.zcu.kiv.jop.property;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.property.exception.GetterNotFoundException;
import cz.zcu.kiv.jop.property.exception.PropertyAccessException;
import cz.zcu.kiv.jop.property.exception.SetterNotFoundException;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * The basic implementation of <em>Object</em>'s property. It provides the
 * getter and setter, which use the methods for manipulation with property.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Declared class type of property.
 */
public class BasicProperty<T> extends AbstractProperty<T> {

  /**
   * <p>
   * Determines if a de-serialized file is compatible with this class.
   * <p>
   * Maintainers must change this value if and only if the new version of this
   * class is not compatible with old versions. See Oracle docs for <a
   * href="http://docs.oracle.com/javase/1.5.0/docs/guide/
   * serialization/">details</a>.
   * <p>
   * Not necessary to include in first version of the class, but included here
   * as a reminder of its importance.
   */
  private static final long serialVersionUID = 5188420974672368425L;

  /**
   * Constructs a basic property.
   *
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public BasicProperty(Class<?> objectClass, String propertyName) {
    super(objectClass, propertyName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  protected Getter<T> createGetter() throws GetterNotFoundException {
    Getter<T> result = (Getter<T>)getGetterOrNull(objectClass, propertyName);
    if (result == null) {
      throw new GetterNotFoundException(objectClass, propertyName);
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  protected Setter<T> createSetter() throws SetterNotFoundException {
    Setter<T> result = (Setter<T>)getSetterOrNull(objectClass, propertyName);
    if (result == null) {
      throw new SetterNotFoundException(objectClass, propertyName);
    }

    return result;
  }

  /**
   * Recursively searches for declared getter method for property in owner class
   * and in all parent classes or implemented interfaces. If the getter method
   * was found, it returns the getter using found method.
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
   * Recursively searches for declared setter method for property in owner class
   * and in all parent classes or implemented interfaces. If the setter method
   * was found, it returns the setter using found method.
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
  protected static Method getSetterMethod(Class<?> theClass, String propertyName) {
    Getter<?> getter = getGetterOrNull(theClass, propertyName);
    Class<?> returnType = (getter == null) ? null : getter.getPropertyType();

    Method[] methods = theClass.getDeclaredMethods();
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
   * Basic implementation of {@code Getter} interface which uses getter
   * {@link Method} for access to property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0
   *
   * @param <T> Declared class type of property.
   */
  public static class BasicGetter<T> extends AbstractPropertyAccess<T, Method> implements Getter<T> {

    /**
     * <p>
     * Determines if a de-serialized file is compatible with this class.
     * <p>
     * Maintainers must change this value if and only if the new version of this
     * class is not compatible with old versions. See Oracle docs for <a
     * href="http://docs.oracle.com/javase/1.5.0/docs/guide/
     * serialization/">details</a>.
     * <p>
     * Not necessary to include in first version of the class, but included here
     * as a reminder of its importance.
     */
    private static final long serialVersionUID = 1898063802358450466L;

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
    @SuppressWarnings("unchecked")
    public Class<T> getPropertyType() {
      return (Class<T>)member.getReturnType();
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
      catch (InvocationTargetException exc) {
        throw createGetterAccessException("Exception occurred inside", exc);
      }
      catch (IllegalAccessException exc) {
        // shouldn't occur
        throw createGetterAccessException("IllegalAccessException occurred while calling", exc);
      }
      catch (IllegalArgumentException exc) {
        logger.error("IllegalArgumentException in class: " + getObjectClass().getName() + ", getter method of property: " + getPropertyName());
        throw createGetterAccessException("IllegalArgumentException occurred calling", exc);
      }
    }

  }

  /**
   * Basic implementation of {@code Setter} interface which uses setter
   * {@link Method} for access to property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0
   *
   * @param <T> Declared class type of property.
   */
  public static class BasicSetter<T> extends AbstractPropertyAccess<T, Method> implements Setter<T> {

    /**
     * <p>
     * Determines if a de-serialized file is compatible with this class.
     * <p>
     * Maintainers must change this value if and only if the new version of this
     * class is not compatible with old versions. See Oracle docs for <a
     * href="http://docs.oracle.com/javase/1.5.0/docs/guide/
     * serialization/">details</a>.
     * <p>
     * Not necessary to include in first version of the class, but included here
     * as a reminder of its importance.
     */
    private static final long serialVersionUID = -4350628252367230916L;

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
    @SuppressWarnings("unchecked")
    public Class<T> getPropertyType() {
      return (Class<T>)member.getParameterTypes()[0];
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
        if (value == null && member.getParameterTypes()[0].isPrimitive()) {
          throw createSetterAccessException("Null value was assigned to a property of primitive type", exc);
        }
        else {
          throw createSetterAccessException("NullPointerException occurred while calling", exc);
        }
      }
      catch (InvocationTargetException exc) {
        throw createSetterAccessException("Exception occurred inside", exc);
      }
      catch (IllegalAccessException exc) {
        // shouldn't occur
        throw createSetterAccessException("IllegalAccessException occurred while calling", exc);
      }
      catch (IllegalArgumentException exc) {
        if (value == null && member.getParameterTypes()[0].isPrimitive()) {
          throw createSetterAccessException("Null value was assigned to a property of primitive type", exc);
        }
        else {
          logger.error("IllegalArgumentException in class: " + getObjectClass() + ", setter method of property: " + getPropertyName());
          logger.error("expected type: " + member.getParameterTypes()[0].getName() + ", actual value: " + (value == null ? null : value.getClass().getName()));
          throw createSetterAccessException("IllegalArgumentException occurred while calling", exc);
        }
      }
    }

  }

}
