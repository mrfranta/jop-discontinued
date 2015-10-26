package cz.zcu.kiv.jop;

import cz.zcu.kiv.jop.util.StringUtils;

/**
 * Common runtime exception for all runtime exceptions in this library which
 * provides the common behavior for all of them and also brings possibility to
 * catching exceptions in one simple catch block. Also it brings possibility to
 * more flow of exception through multiple method calls.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public class JopRuntimeException extends RuntimeException {

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

  /**
   * Constructs a new runtime exception with <code>null</code> as its detail
   * message. The cause is not initialized, and may subsequently be initialized
   * by a call to {@link #initCause}.
   */
  public JopRuntimeException() {
    super();
  }

  /**
   * Constructs a new runtime exception with the specified detail message. The
   * cause is not initialized, and may subsequently be initialized by a call to
   * {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later
   *          retrieval by the {@link #getMessage()} method.
   */
  public JopRuntimeException(String message) {
    super(message);
  }

  /**
   * Constructs a new runtime exception with the specified cause and
   * <code>null</code> as its detail message. This constructor is different to
   * parent {@link RuntimeException#RuntimeException(Throwable) constructor}
   * which sets detail message as message from cause. This constructor brings
   * possibility to use {@link #getDefaultMessage} instead.
   *
   * @param cause the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   */
  public JopRuntimeException(Throwable cause) {
    super(null, cause);
  }

  /**
   * Constructs a new runtime exception with the specified detail message and
   * cause.
   * <p>
   * Note that the detail message associated with <code>cause</code> is
   * <i>not</i> automatically incorporated in this exception's detail message.
   *
   * @param message the detail message (which is saved for later retrieval by
   *          the {@link #getMessage()} method).
   * @param cause the cause (which is saved for later retrieval by the
   *          {@link #getCause()} method). (A <tt>null</tt> value is permitted,
   *          and indicates that the cause is nonexistent or unknown.)
   */
  public JopRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Returns the detail message string of this runtime exception. If message is
   * <code>null</code> (because of complex constructors) and there is no default
   * message ({@link #getDefaultMessage}), the message from cause will be
   * returned.
   *
   * @return The detail message string of this {@link JopRuntimeException}
   *         instance.
   */
  @Override
  public String getMessage() {
    String message = super.getMessage(); // message passed in constructor
    if (!StringUtils.hasText(message)) {
      message = getDefaultMessage(); // default message
      if (!StringUtils.hasText(message)) {
        Throwable cause = getCause(); // message from cause
        if (cause != null) {
          message = cause.getMessage();
        }
      }
    }

    return message;
  }

  /**
   * Returns default message in case that the detail message given in exception
   * constructor is <code>null</code>. The default implementation returns also
   * <code>null</code> and may be overridden in child classes.
   *
   * @return Always <code>null</code>. In case of override, it returns default
   *         message of exception.
   */
  protected String getDefaultMessage() {
    return null;
  }

}
