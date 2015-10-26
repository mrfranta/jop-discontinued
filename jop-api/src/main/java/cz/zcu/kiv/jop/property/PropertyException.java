package cz.zcu.kiv.jop.property;

/**
 * The general exception which can occur during manipulation with
 * <em>Object</em>'s properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public class PropertyException extends Exception {

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

  /**
   * Constructs an exception.
   *
   * @param objectClass class type of a property owner.
   * @param propertyName name of property.
   */
  public PropertyException(Class<?> objectClass, String propertyName) {
    this(null, null, objectClass, propertyName);
  }

  /**
   * Constructs an exception.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param objectClass class type of a property owner.
   * @param propertyName name of property.
   */
  public PropertyException(String message, Class<?> objectClass, String propertyName) {
    this(message, null, objectClass, propertyName);
  }

  /**
   * Constructs an exception.
   *
   * @param cause the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   * @param objectClass class type of a property owner.
   * @param propertyName name of property.
   */
  public PropertyException(Throwable cause, Class<?> objectClass, String propertyName) {
    this(null, cause, objectClass, propertyName);
  }

  /**
   * Constructs an exception.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param cause the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   * @param objectClass class type of a property owner.
   * @param propertyName name of property.
   */
  public PropertyException(String message, Throwable cause, Class<?> objectClass, String propertyName) {
    super(message, cause);

    this.objectClass = objectClass;
    this.propertyName = propertyName;
  }

  /**
   * Returns the class type of a property owner.
   *
   * @return Class type of property owner.
   */
  public Class<?> getObjectClass() {
    return objectClass;
  }

  /**
   * Returns the name of property.
   *
   * @return The property name.
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * Returns the detail message string of this exception.
   *
   * @return The detail message string of this {@link PropertyException}
   *         instance.
   */
  @Override
  public String getMessage() {
    String message = super.getMessage();
    if (message == null) {
      // if message is null (because of complex constructors), overtake the message from cause
      Throwable cause = getCause();
      if (cause != null) {
        return cause.getMessage();
      }
    }

    return message;
  }

}
