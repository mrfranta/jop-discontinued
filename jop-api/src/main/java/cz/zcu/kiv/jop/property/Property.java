package cz.zcu.kiv.jop.property;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;

/**
 * This interface serves as abstraction of <em>Object</em>'s property. It provides the appropriate
 * getter and setter for manipulation with property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Declared class type of property.
 */
public interface Property<T> extends AnnotatedElement, Serializable {

  /**
   * Returns the class type of a property owner.
   *
   * @return Class type of property owner.
   */
  public Class<?> getDeclaringClass();

  /**
   * Returns the name of property.
   *
   * @return The property name.
   */
  public String getName();

  /**
   * Creates and returns the instance of appropriate <em>getter</em> for the property.
   *
   * @return The <em>getter</em> for the property.
   * @throws GetterNotFoundException If some error occurs during interpretation of the getter name
   *           or if getter for property was not found.
   */
  public Getter<T> getGetter() throws GetterNotFoundException;

  /**
   * Creates and returns the instance of appropriate <em>setter</em> for the property.
   *
   * @return The <em>getter</em> for the property.
   * @throws GetterNotFoundException If some error occurs during interpretation of the setter name
   *           or if setter for property was not found.
   */
  public Setter<T> getSetter() throws SetterNotFoundException;

}
