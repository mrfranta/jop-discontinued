package cz.zcu.kiv.jop.property;

import cz.zcu.kiv.jop.util.StringUtils;

/**
 * This exception may occur in case that some problem occurred during
 * manipulation with property.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public class PropertyAccessException extends PropertyException {

  /**
   * Determines if a de-serialized file is compatible with this class.
   * <p>
   * Maintainers must change this value if and only if the new version of this
   * class is not compatible with old versions. See Oracle docs for <a
   * href="http://docs.oracle.com/javase/1.5.0/docs/guide/ serialization/"
   * >details</a>.
   * <p>
   * Not necessary to include in first version of the class, but included here
   * as a reminder of its importance.
   */
  private static final long serialVersionUID = 20151114L;

  /** Information whether the exception raised in setter. */
  protected final boolean fromSetter;

  /**
   * Constructs a new property access exception with the specified detail
   * message. The cause is not initialized, and may subsequently be initialized
   * by a call to {@link #initCause}.
   * <p>
   * This exception was raised from getter.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public PropertyAccessException(String message, Class<?> objectClass, String propertyName) {
    this(message, null, objectClass, propertyName, false);
  }

  /**
   * Constructs a new property access exception with the specified detail
   * message. The cause is not initialized, and may subsequently be initialized
   * by a call to {@link #initCause}.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   * @param fromSetter information whether the exception raised in
   *          {@link cz.zcu.kiv.jop.property.Setter setter}.
   */
  public PropertyAccessException(String message, Class<?> objectClass, String propertyName, boolean fromSetter) {
    this(message, null, objectClass, propertyName, fromSetter);
  }

  /**
   * Constructs a new property access exception with the specified detail
   * message and cause.
   * <p>
   * Note that the detail message associated with <code>cause</code> is
   * <i>not</i> automatically incorporated in this exception's detail message.
   * <p>
   * This exception was raised from getter.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param cause the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public PropertyAccessException(String message, Throwable cause, Class<?> objectClass, String propertyName) {
    this(message, cause, objectClass, propertyName, false);
  }

  /**
   * Constructs a new property access exception with the specified detail
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
   * @param fromSetter information whether the exception raised in
   *          {@link cz.zcu.kiv.jop.property.Setter setter}.
   */
  public PropertyAccessException(String message, Throwable cause, Class<?> objectClass, String propertyName, boolean fromSetter) {
    super(message, cause, objectClass, propertyName);

    this.fromSetter = fromSetter;
  }

  /**
   * Returns information whether the exception raised in
   * {@link cz.zcu.kiv.jop.property.Setter setter}.
   *
   * @return <code>true</code> if the exception raised in
   *         {@link cz.zcu.kiv.jop.property.Setter setter}; <code>false</code>
   *         if the exception raised in {@link cz.zcu.kiv.jop.property.Setter
   *         getter}.
   */
  public boolean isFromSetter() {
    return fromSetter;
  }

  /**
   * Returns the detail message string of this exception.
   *
   * @return The detail message string of this {@link PropertyAccessException}
   *         instance.
   */
  @Override
  public String getMessage() {
    String message = super.getMessage();

    // @formatter:off
    StringBuilder sb = new StringBuilder(StringUtils.hasText(message) ? "" : message)
        .append(isFromSetter() ? " setter of " : " getter of ")
        .append(getObjectClassName())
        .append(".")
        .append(getPropertyName());
    // @formatter:on

    return sb.toString();
  }

}
