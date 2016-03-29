package cz.zcu.kiv.jop.generator;

import cz.zcu.kiv.jop.property.Property;

/**
 * Interface which serves as interlayer between the value generators and another parts of this
 * library. Implementation of this interface should analyze the annotations of given property and
 * then should choose and invoke some value generator. After invocation is returned the result of
 * chosen value generator. There is also support of multiple invocations of same value generator.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ValueGeneratorInvoker {

  /**
   * Returns information whether the value generator annotation is present on given property.
   *
   * @param property the property which annotations will be checked.
   * @return <code>true</code> if given property is annotated by value generator annotation;
   *         <code>false</code> otherwise.
   */
  public boolean isAnnotationPresent(Property<?> property);

  /**
   * Analyzes annotations for given property and then choose the proper value generator for
   * invocation. The chosen value generator is invoked and then result of invocation is returned.
   *
   * @param property the property for which will be invoked the proper value generator.
   * @return The result of value generator invocation.
   * @throws ValueGeneratorException If some error occurs during value generator invocation.
   */
  public Object getValue(Property<?> property) throws ValueGeneratorException;

  /**
   * Analyzes annotations for given property and then choose the proper value generator for
   * invocation. The chosen value generator is invoked multiple times (parameter <code>count</code>)
   * and then array of invocation results is returned.
   *
   * @param property the property for which will be invoked the proper value generator.
   * @param count the number of value generator invocations.
   * @return Array of results of value generator invocations.
   * @throws ValueGeneratorException If some error occurs during value generator invocation.
   */
  public Object[] getValue(Property<?> property, int count) throws ValueGeneratorException;
}
