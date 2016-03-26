package cz.zcu.kiv.jop.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import cz.zcu.kiv.jop.util.Preconditions;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Abstract implementation of {@link Property} interface which provides an implementation of the
 * common methods for all properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Declared class type of property.
 */
public abstract class AbstractProperty<T> implements Property<T> {

  /**
   * Determines if a de-serialized file is compatible with this class.
   * <p>
   * Maintainers must change this value if and only if the new version of this class is not
   * compatible with old versions. See Oracle docs for <a
   * href="http://docs.oracle.com/javase/1.5.0/docs/guide/ serialization/">details</a>.
   * <p>
   * Not necessary to include in first version of the class, but included here as a reminder of its
   * importance.
   */
  private static final long serialVersionUID = 20160326L;

  /** Class type of a property owner. */
  protected final Class<?> declaringClass;
  /** Name of property. */
  protected final String propertyName;

  /**
   * Field for property which can be lazy loaded (when is required) - use getter {@link #getField()}
   * instead of direct access.
   */
  private transient Field field;

  /** Created getter for property. */
  protected transient Getter<T> getter;
  /** Created setter for property. */
  protected transient Setter<T> setter;

  /**
   * Constructs an abstract property.
   *
   * @param declaringClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public AbstractProperty(Class<?> declaringClass, String propertyName) {
    this.declaringClass = Preconditions.checkArgumentNotNull(declaringClass, "Class type cannot be null");
    this.propertyName = Preconditions.checkArgumentNotNull(propertyName, "Name of property cannot be null");
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getDeclaringClass() {
    return declaringClass;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {
    return propertyName;
  }

  /**
   * {@inheritDoc}
   *
   * @throws PropertyRuntimeException If some error occurs during getting annotation for property.
   */
  public Class<?> getType() {
    try {
      return getField().getType();
    }
    catch (PropertyNotFoundException exc) {
      throw new PropertyRuntimeException(exc, declaringClass, propertyName);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws PropertyRuntimeException If some error occurs during getting annotation for property.
   */
  public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
    try {
      return getField().isAnnotationPresent(annotationType);
    }
    catch (PropertyNotFoundException exc) {
      throw new PropertyRuntimeException(exc, declaringClass, propertyName);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws PropertyRuntimeException If some error occurs during getting annotation for property.
   */
  public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    try {
      return getField().getAnnotation(annotationType);
    }
    catch (PropertyNotFoundException exc) {
      throw new PropertyRuntimeException(exc, declaringClass, propertyName);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws PropertyRuntimeException If some error occurs during getting annotation for property.
   */
  public Annotation[] getAnnotations() {
    try {
      return getField().getAnnotations();
    }
    catch (PropertyNotFoundException exc) {
      throw new PropertyRuntimeException(exc, declaringClass, propertyName);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws PropertyRuntimeException If some error occurs during getting annotation for property.
   */
  public Annotation[] getDeclaredAnnotations() {
    try {
      return getField().getDeclaredAnnotations();
    }
    catch (PropertyNotFoundException exc) {
      throw new PropertyRuntimeException(exc, declaringClass, propertyName);
    }
  }

  /**
   * Returns field for property which is lazy loaded. Because of that is strongly suggested to use
   * this getter instead of direct access.
   *
   * @return Declared field for property.
   * @throws PropertyNotFoundException If the field with given name was not found in
   *           <code>declaringClass</code>.
   */
  protected Field getField() throws PropertyNotFoundException {
    if (field == null) {
      field = getField(declaringClass, propertyName);
    }

    return field;
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
   * @throws GetterNotFoundException If some error occurs during interpretation of the getter name
   *           (getter for property was not found).
   */
  protected abstract Getter<T> createGetter() throws GetterNotFoundException;

  /**
   * Creates the instance of appropriate <em>setter</em> for the property.
   *
   * @return The <em>getter</em> for the property.
   * @throws GetterNotFoundException If some error occurs during interpretation of the setter name
   *           (setter for property was not found).
   */
  protected abstract Setter<T> createSetter() throws SetterNotFoundException;

  /**
   * Returns string value of property.
   *
   * @return String value of property.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [declaringClass=" + (declaringClass == null ? null : declaringClass.getName()) + ", propertyName=" + propertyName + "]";
  }

  /**
   * Recursively searches for declared field with given name in given class and in all parent
   * classes or implemented interfaces. If the field is not found, the exception is thrown.
   *
   * @param clazz the class type of a field owner.
   * @param fieldName the name of field.
   * @return Found declared field.
   * @throws PropertyNotFoundException If the declared field with given name was not found.
   */
  protected static Field getField(Class<?> clazz, String fieldName) throws PropertyNotFoundException {
    return getField(clazz, clazz, fieldName);
  }

  /**
   * Recursively searches for declared field with given name in given class and in all parent
   * classes or implemented interfaces. If the field is not found, the exception is thrown.
   *
   * @param root the root class type of a field owner (the class from the recursion started).
   * @param clazz the class type of a field owner.
   * @param fieldName the name of field.
   * @return Found declared field.
   * @throws PropertyNotFoundException If the declared field with given name was not found.
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
