package cz.zcu.kiv.jop.property.exception;

/**
 * This exception may occur in case that no <em>setter</em> for the property was
 * not found.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public class SetterNotFoundException extends PropertyException {

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
  private static final long serialVersionUID = 5553890687341610926L;

  /**
   * Constructs an exception.
   *
   * @param objectClass class type of a property owner.
   * @param propertyName name of property.
   */
  public SetterNotFoundException(Class<?> objectClass, String propertyName) {
    super(objectClass, propertyName);
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
  public SetterNotFoundException(Throwable cause, Class<?> objectClass, String propertyName) {
    super(cause, objectClass, propertyName);
  }

  /**
   * Returns the detail message string of this exception.
   *
   * @return The detail message string of this {@link SetterNotFoundException}
   *         instance.
   */
  @Override
  public String getMessage() {
    return "Could not find a setter for " + propertyName + " in class " + objectClass.getName();
  }

}
