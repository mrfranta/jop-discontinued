package cz.zcu.kiv.jop.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Abstract implementation of {@link Property} interface which provides an
 * implementation of the common methods for all properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Declared class type of property.
 */
public abstract class AbstractProperty<T> implements Property<T> {

  /**
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
  private static final long serialVersionUID = 20151113L;

  /** Class type of a property owner. */
  protected final Class<?> objectClass;
  /** Name of property. */
  protected final String propertyName;

  /** Cache for array of annotations by which is annotated property. */
  private Annotation[] annotations;

  /** Created getter for property. */
  protected Getter<T> getter;
  /** Created setter for property. */
  protected Setter<T> setter;

  /**
   * Constructs an abstract property.
   *
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public AbstractProperty(Class<?> objectClass, String propertyName) {
    this.objectClass = objectClass;
    this.propertyName = propertyName;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getObjectClass() {
    return objectClass;
  }

  /**
   * {@inheritDoc}
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * {@inheritDoc}
   */
  public Annotation[] getAnnotations() throws PropertyException {
    if (annotations == null) {
      Field field = getField(objectClass, propertyName);
      annotations = field.getAnnotations();
    }

    return annotations;
  }

  /**
   * {@inheritDoc}
   */
  public Getter<T> getGetter() throws GetterNotFoundException {
    if (getter == null) {
      getter = createGetter();
    }

    return getter;
  }

  /**
   * {@inheritDoc}
   */
  public Setter<T> getSetter() throws SetterNotFoundException {
    if (setter == null) {
      setter = createSetter();
    }

    return setter;
  }

  /**
   * Creates the instance of appropriate <em>getter</em> for the property.
   *
   * @return The <em>getter</em> for the property.
   * @throws GetterNotFoundException If some error occurs during interpretation
   *           of the getter name (getter for property was not found).
   */
  protected abstract Getter<T> createGetter() throws GetterNotFoundException;

  /**
   * Creates the instance of appropriate <em>setter</em> for the property.
   *
   * @return The <em>getter</em> for the property.
   * @throws GetterNotFoundException If some error occurs during interpretation
   *           of the setter name (setter for property was not found).
   */
  protected abstract Setter<T> createSetter() throws SetterNotFoundException;

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
  protected static Field getField(Class<?> root, Class<?> clazz, String fieldName) throws PropertyNotFoundException {
    if (clazz == null || clazz == Object.class) {
      throw new PropertyNotFoundException(root, fieldName);
    }

    // the declared field will be accessible, no additional setting is needed.
    Field field = ReflectionUtils.getDeclaredField(clazz, fieldName);
    if (field == null) {
      field = getField(root, clazz.getSuperclass(), fieldName);
    }

    return field;
  }

}
