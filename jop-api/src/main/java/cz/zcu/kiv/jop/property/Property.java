package cz.zcu.kiv.jop.property;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * This interface serves as abstraction of <em>Object</em>'s property. It
 * provides the appropriate getter and setter for manipulation with property.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Declared class type of property.
 */
public interface Property<T> extends Serializable {

  /**
   * Returns the class type of a property owner.
   *
   * @return Class type of property owner.
   */
  public Class<?> getObjectClass();

  /**
   * Returns the name of property.
   *
   * @return The property name.
   */
  public String getPropertyName();

  /**
   * Returns array of annotations by which is annotated property. If there is no
   * annotation for the property it returns empty array (it never returns
   * <code>null</code> value).
   *
   * @return Array of annotations by which is annotated property.
   * @throws PropertyException if some error occurs during obtaining of property
   *           annotations - for example when this property was created for
   *           non-existing field.
   */
  public Annotation[] getAnnotations() throws PropertyException;

  /**
   * Creates and returns the instance of appropriate <em>getter</em> for the
   * property.
   *
   * @return The <em>getter</em> for the property.
   * @throws GetterNotFoundException If some error occurs during interpretation
   *           of the getter name or if getter for property was not found.
   */
  public Getter<T> getGetter() throws GetterNotFoundException;

  /**
   * Creates and returns the instance of appropriate <em>setter</em> for the
   * property.
   *
   * @return The <em>getter</em> for the property.
   * @throws GetterNotFoundException If some error occurs during interpretation
   *           of the setter name or if setter for property was not found.
   */
  public Setter<T> getSetter() throws SetterNotFoundException;

}
