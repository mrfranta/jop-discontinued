package cz.zcu.kiv.jop.property;

import java.lang.reflect.Field;

import cz.zcu.kiv.jop.property.exception.GetterNotFoundException;
import cz.zcu.kiv.jop.property.exception.PropertyAccessException;
import cz.zcu.kiv.jop.property.exception.PropertyNotFoundException;
import cz.zcu.kiv.jop.property.exception.SetterNotFoundException;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * The implementation of <em>Object</em>'s property. It provides the getter and
 * setter, which use the {@link Field} for manipulation with property.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Declared class type of property.
 */
public class DirectAccessProperty<T> extends AbstractProperty<T> {

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
  private static final long serialVersionUID = 20151020L;

  /**
   * Constructs a direct access property.
   *
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public DirectAccessProperty(Class<?> objectClass, String propertyName) {
    super(objectClass, propertyName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Getter<T> createGetter() throws GetterNotFoundException {
    try {
      return new DirectGetter<T>(propertyName, getField(objectClass, propertyName));
    }
    catch (PropertyNotFoundException exc) {
      throw new GetterNotFoundException(exc, objectClass, propertyName);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Setter<T> createSetter() throws SetterNotFoundException {
    try {
      return new DirectSetter<T>(propertyName, getField(objectClass, propertyName));
    }
    catch (PropertyNotFoundException exc) {
      throw new SetterNotFoundException(exc, objectClass, propertyName);
    }
  }

  /**
   * Recursively searches for declared field with given name in given class and
   * in all parent classes or implemented interfaces. If the field is not found,
   * the exception is thrown.
   *
   * @param clazz the class type of a field owner.
   * @param fieldName the name of field.
   * @return Found declared field.
   * @throws PropertyNotFoundException If the declared field with given name was
   *           not found.
   */
  protected static Field getField(Class<?> clazz, String fieldName) throws PropertyNotFoundException {
    return getField(clazz, clazz, fieldName);
  }

  /**
   * Recursively searches for declared field with given name in given class and
   * in all parent classes or implemented interfaces. If the field is not found,
   * the exception is thrown.
   *
   * @param root the root class type of a field owner (the class from the
   *          recursion started).
   * @param clazz the class type of a field owner.
   * @param fieldName the name of field.
   * @return Found declared field.
   * @throws PropertyNotFoundException If the declared field with given name was
   *           not found.
   */
  protected static Field getField(Class<?> root, Class<?> clazz, String fieldName) {
    if (clazz == null || clazz == Object.class) {
      return null;
    }

    // the declared field will be accessible, no additional setting is needed.
    Field field = ReflectionUtils.getDeclaredField(clazz, fieldName);
    if (field == null) {
      field = getField(root, clazz.getSuperclass(), fieldName);
    }

    return field;
  }

  /**
   * Implementation of {@code Getter} interface which uses {@link Field} for
   * direct access to property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0
   *
   * @param <T> Declared class type of property.
   */
  public static class DirectGetter<T> extends AbstractPropertyAccess<T, Field> implements Getter<T> {

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
    private static final long serialVersionUID = 5222843204772142534L;

    /**
     * Constructs a getter.
     *
     * @param propertyName the name of property.
     * @param field the field which will be used for access to property.
     */
    DirectGetter(String propertyName, Field field) {
      super(propertyName, field);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Class<T> getPropertyType() {
      return (Class<T>)member.getType();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(Object owner) throws PropertyAccessException {
      try {
        return (T)member.get(owner);
      }
      catch (Exception exc) {
        throw createGetterAccessException("Could not get a field value by reflection inside", exc);
      }
    }

  }

  /**
   * Implementation of {@code Setter} interface which uses {@link Field} for
   * direct access to property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0
   *
   * @param <T> Declared class type of property.
   */
  public static class DirectSetter<T> extends AbstractPropertyAccess<T, Field> implements Setter<T> {

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
    private static final long serialVersionUID = 4294761455944801476L;

    /**
     * Constructs a setter.
     *
     * @param propertyName the name of property.
     * @param field the field which will be used for access to property.
     */
    DirectSetter(String propertyName, Field field) {
      super(propertyName, field);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Class<T> getPropertyType() {
      return (Class<T>)member.getType();
    }

    /**
     * {@inheritDoc}
     */
    public void set(Object owner, T value) throws PropertyAccessException {
      try {
        member.set(owner, value);
      }
      catch (Exception exc) {
        if (value == null && getPropertyType().isPrimitive()) {
          throw createSetterAccessException("Null value was assigned to a property of primitive type inside", exc);
        }
        else {
          throw createSetterAccessException("Could not set a field value by reflection inside", exc);
        }
      }
    }

  }

}
