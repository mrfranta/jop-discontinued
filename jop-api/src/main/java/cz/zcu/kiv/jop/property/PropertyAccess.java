package cz.zcu.kiv.jop.property;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * This interface contains general methods for handling of / accessing to the property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Declared class type of property.
 */
public interface PropertyAccess<T> extends Serializable {

  /**
   * Returns the name of property.
   *
   * @return The property name.
   */
  public String getPropertyName();

  /**
   * Returns the declared class type of a property.
   *
   * @return Declared class type of property.
   */
  public Class<?> getPropertyType();

  /**
   * Returns the member used for access to property. This might be the field or method.
   *
   * @return The member used for access to property.
   */
  public Member getMember();

  /**
   * Returns the method used for access to property (getter or setter method). This is optional
   * operation an may return <code>null</code>.
   *
   * @return The getter/setter method or <code>null</code>.
   */
  public Method getMethod();

  /**
   * Returns name of the method used for access to property (getter or setter method). This is
   * optional operation an may return <code>null</code>.
   *
   * @return Name of the getter/setter method or <code>null</code>.
   */
  public String getMethodName();

}
