package cz.zcu.kiv.jop.property;

/**
 * This exception may occur in case that no <em>getter</em> for the property was
 * not found.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public class GetterNotFoundException extends PropertyException {

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

  /**
   * Constructs a new exception for not found getter with <code>null</code> as
   * its detail message. The cause is not initialized, and may subsequently be
   * initialized by a call to {@link #initCause}.
   *
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public GetterNotFoundException(Class<?> objectClass, String propertyName) {
    this(null, null, objectClass, propertyName);
  }

  /**
   * Constructs a new exception for not found getter with the specified detail
   * message. The cause is not initialized, and may subsequently be initialized
   * by a call to {@link #initCause}.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public GetterNotFoundException(String message, Class<?> objectClass, String propertyName) {
    this(message, null, objectClass, propertyName);
  }

  /**
   * Constructs a new exception for not found getter with the specified cause
   * and <code>null</code> as its detail message. This constructor is different
   * to parent {@link Exception#Exception(Throwable) constructor} which sets
   * detail message as message from cause. This constructor brings possibility
   * to use {@link #getDefaultMessage} instead.
   *
   * @param cause the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   * @param objectClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public GetterNotFoundException(Throwable cause, Class<?> objectClass, String propertyName) {
    this(null, cause, objectClass, propertyName);
  }

  /**
   * Constructs a new exception for not found getter with the specified detail
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
  public GetterNotFoundException(String message, Throwable cause, Class<?> objectClass, String propertyName) {
    super(message, cause, objectClass, propertyName);
  }

  /**
   * Returns default message in case that the detail message given in exception
   * constructor is <code>null</code>.
   *
   * @return Default message of exception.
   */
  @Override
  protected String getDefaultMessage() {
    return "Could not find a getter for " + getPropertyName() + " in class " + getObjectClassName();
  }

}
