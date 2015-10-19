package cz.zcu.kiv.jop.property.exception;

import cz.zcu.kiv.jop.util.StringUtils;

/**
 * This exception may occur in case that no <em>getter</em> for the property was
 * not found.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public class GetterNotFoundException extends PropertyException {

  /**
   * <p>
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
  private static final long serialVersionUID = 1189569212587267374L;

  /**
   * Constructs an exception.
   *
   * @param objectClass class type of a property owner.
   * @param propertyName name of property.
   */
  public GetterNotFoundException(Class<?> objectClass, String propertyName) {
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
  public GetterNotFoundException(String message, Class<?> objectClass, String propertyName) {
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
  public GetterNotFoundException(Throwable cause, Class<?> objectClass, String propertyName) {
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
  public GetterNotFoundException(String message, Throwable cause, Class<?> objectClass, String propertyName) {
    super(message, cause, objectClass, propertyName);
  }

  /**
   * Returns the detail message string of this exception.
   *
   * @return The detail message string of this {@link GetterNotFoundException}
   *         instance.
   */
  @Override
  public String getMessage() {
    String message = super.getMessage();
    if (StringUtils.hasText(message)) {
      return message; // returns custom message passed in constructor.
    }

    // default message
    return "Could not find a getter for " + propertyName + " in class " + objectClass.getName();
  }

}
