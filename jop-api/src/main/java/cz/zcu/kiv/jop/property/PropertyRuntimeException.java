package cz.zcu.kiv.jop.property;

import cz.zcu.kiv.jop.JopRuntimeException;

/**
 * The general runtime exception which can occur during manipulation with
 * <em>Object</em>'s properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public class PropertyRuntimeException extends JopRuntimeException {

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
  private static final long serialVersionUID = 20151114L;

  /** Class type of a property owner. */
  protected final Class<?> objectClass;

  /** Name of property. */
  protected final String propertyName;

  /**
   * Constructs a new property runtime exception with <code>null</code> as its
   * detail message. The cause is not initialized, and may subsequently be
   * initialized by a call to {@link #initCause}.
   *
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public PropertyRuntimeException(Class<?> objectClass, String propertyName) {
    this(null, null, objectClass, propertyName);
  }

  /**
   * Constructs a new property runtime exception with the specified detail
   * message. The cause is not initialized, and may subsequently be initialized
   * by a call to {@link #initCause}.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public PropertyRuntimeException(String message, Class<?> objectClass, String propertyName) {
    this(message, null, objectClass, propertyName);
  }

  /**
   * Constructs a new property runtime exception with the specified cause and
   * <code>null</code> as its detail message. This constructor is different to
   * parent {@link RuntimeException#RuntimeException(Throwable) constructor}
   * which sets detail message as message from cause. This constructor brings
   * possibility to use {@link #getDefaultMessage} instead.
   *
   * @param cause the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public PropertyRuntimeException(Throwable cause, Class<?> objectClass, String propertyName) {
    this(null, cause, objectClass, propertyName);
  }

  /**
   * Constructs a new property runtime exception with the specified detail
   * message and cause.
   * <p>
   * Note that the detail message associated with <code>cause</code> is
   * <i>not</i> automatically incorporated in this exception's detail message.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param cause the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public PropertyRuntimeException(String message, Throwable cause, Class<?> objectClass, String propertyName) {
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
   * Returns the class type name of a property owner.
   *
   * @return Class type name of property owner.
   */
  public String getObjectClassName() {
    return (objectClass == null) ? null : objectClass.getName();
  }

  /**
   * Returns the name of property.
   *
   * @return The property name.
   */
  public String getPropertyName() {
    return propertyName;
  }

}
