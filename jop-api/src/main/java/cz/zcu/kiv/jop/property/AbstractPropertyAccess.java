package cz.zcu.kiv.jop.property;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

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
  private static final long serialVersionUID = 6346392989765731679L;

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
   * Returns a string representation of property accessor.
   *
   * @return String representation of property accessor.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [" + getObjectClass() + '.' + getPropertyName() + ']';
  }

}
