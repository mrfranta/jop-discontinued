package cz.zcu.kiv.jop.property;

/**
 * This interface extends {@code PropertyAccess} interface for setting of
 * property value.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Declared class type of property.
 */
public interface Setter<T> extends PropertyAccess<T> {

  /**
   * Sets given value to property of given <code>owner</code>.
   *
   * @param owner the owner of the property.
   * @param value the value which will be set to property.
   * @throws PropertyAccessException If some problem occurs during setting of
   *           the value to property.
   */
  public void set(Object owner, T value) throws PropertyAccessException;

}
