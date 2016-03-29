package cz.zcu.kiv.jop.populator;

import cz.zcu.kiv.jop.property.Property;

/**
 * Interface which serves as interlayer between the property populators and another parts of this
 * library (including between chained property populators).
 * <p>
 * Implementation of the {@link #populate} method should analyze the annotations of given property
 * and then should choose and invoke some property populator which populates the value of given
 * property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PropertyPopulatorInvoker {

  /**
   * Returns information whether the property populator annotation is present on given property.
   *
   * @param property the property which annotations will be checked.
   * @return <code>true</code> if given property is annotated by populating property populator;
   *         <code>false</code> otherwise.
   */
  public boolean isAnnotationPresent(Property<?> property);

  /**
   * Analyzes annotations of given property and then choose the first property populator for
   * invocation.
   * <p>
   * If the property has no property populator annotation, the default property populator will be
   * chosen. The default property populator should invoke the value generator, if property is
   * annotated by some annotation for value generator, and populates the generated value into given
   * property.
   * <p>
   * This invocation method should serve as entry point into chain of populators. The first invoked
   * populator will overtake the flow control between chained populators for given property.
   *
   * @param property the property for which will be invoked the proper populator.
   * @param owner the instance of property owner.
   * @throws PropertyPopulatorException If some error occurs during property populator invocation.
   */
  public void populate(Property<?> property, Object owner) throws PropertyPopulatorException;

  /**
   * Analyzes annotations of given property and then choose the next property poupulator for
   * invocation. It allows to specify the target class type of populated value. The populated value
   * by next populator is returned by this method.
   * <p>
   * If the property has no next property populator annotation, the default property populator will
   * be chosen. The default property populator should invoke the value generator, if property is
   * annotated by some annotation for value generator, and returns generated value into given
   * property.
   * <p>
   * This method may use {@link cz.zcu.kiv.jop.property.VirtualProperty VirtualProperty} for
   * invocation of next populator and primary serves for chaining of property populators and should
   * be called only for populators.
   *
   * @param property the property for which will be invoked next populator.
   * @param targetClassType the target class type of populated value by next property populator.
   * @return The populated value by next property populator.
   * @throws PropertyPopulatorException If some error occurs during next property populator
   *           invocation.
   */
  public <T> T invokeNextPopulator(Property<?> property, Class<T> targetClassType) throws PropertyPopulatorException;

}
