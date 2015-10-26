package cz.zcu.kiv.jop.property;

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
  private static final long serialVersionUID = 20151026L;

  /** Class type of a property owner. */
  protected final Class<?> objectClass;
  /** Name of property. */
  protected final String propertyName;

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

}
