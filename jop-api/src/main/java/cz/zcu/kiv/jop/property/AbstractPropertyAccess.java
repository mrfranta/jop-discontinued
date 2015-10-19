package cz.zcu.kiv.jop.property;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

import cz.zcu.kiv.jop.property.exception.PropertyAccessException;

/**
 * Abstract implementation of {@link PropertyAccess} interface which provides an
 * implementation of the common methods for handling of / accessing to the
 * property.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Declared class type of property.
 * @param <M> Member type which will be used for access to property.
 */
public abstract class AbstractPropertyAccess<T, M extends Member> implements PropertyAccess<T> {

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
  private static final long serialVersionUID = -7677437009464962597L;

  /** Name of property. */
  protected final String propertyName;
  /** Member which will be used for access to property. */
  protected final transient M member;

  /**
   * Constructs property accessor.
   *
   * @param propertyName the name of property.
   * @param member the member which will be used for access to property.
   */
  public AbstractPropertyAccess(String propertyName, M member) {
    this.propertyName = propertyName;
    this.member = member;
  }

  /**
   * Returns the class type of a property owner.
   *
   * @return Class type of property owner.
   */
  public Class<?> getObjectClass() {
    return member.getDeclaringClass();
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
  public Member getMember() {
    return member;
  }

  /**
   * {@inheritDoc}
   */
  public Method getMethod() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public String getMethodName() {
    return null;
  }

  /**
   * Creates exception which can be thrown by getter in case of some problem
   * during getting of the property value.
   *
   * @param message the detail message of exception.
   * @param cause the cause of exception.
   * @return Created {@link PropertyAccessException} for getter method.
   */
  protected PropertyAccessException createGetterAccessException(String message, Throwable cause) {
    return new PropertyAccessException(message, cause, getObjectClass(), getPropertyName());
  }

  /**
   * Creates exception which can be thrown by setter in case of some problem
   * during setting of the value to property.
   *
   * @param message the detail message of exception.
   * @param cause the cause of exception.
   * @return Created {@link PropertyAccessException} for setter method.
   */
  protected PropertyAccessException createSetterAccessException(String message, Throwable cause) {
    return new PropertyAccessException(message, cause, getObjectClass(), getPropertyName(), true);
  }

  /**
   * Returns a string representation of property accessor.
   *
   * @return String representation of property accessor.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [" + getObjectClass() + '.' + getPropertyName() + ']';
  }

}
