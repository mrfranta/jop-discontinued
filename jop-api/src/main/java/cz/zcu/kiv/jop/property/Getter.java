package cz.zcu.kiv.jop.property;

/**
 * This interface extends {@code PropertyAccess} interface for getting of property value.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Declared class type of property.
 */
public interface Getter<T> extends PropertyAccess<T> {

  /**
   * Returns actual value of property of given <code>owner</code>.
   *
   * @param owner the owner of the property.
   * @return The value of property of given <code>owner</code>.
   * @throws PropertyAccessException If some problem occurs during getting of the property value.
   */
  public T get(Object owner) throws PropertyAccessException;

}
