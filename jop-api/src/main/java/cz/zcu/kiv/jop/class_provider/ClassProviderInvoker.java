package cz.zcu.kiv.jop.class_provider;

import cz.zcu.kiv.jop.property.Property;

/**
 * Interface which serves as interlayer between the class providers and another parts of this
 * library. Implementation of this interface should analyze the annotations of given property and
 * then should choose and invoke some class provider. After invocation is returned the result of
 * chosen class provider. There is also support of multiple invocations of same class provider.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ClassProviderInvoker {

  /**
   * Returns information whether the class provider annotation is present on given property.
   *
   * @param property the property which annotations will be checked.
   * @return <code>true</code> if given property is annotated by class provider annotation;
   *         <code>false</code> otherwise.
   */
  public boolean isAnnotationPresent(Property<?> property);

  /**
   * Analyzes annotations for given property and then choose the proper class provider for
   * invocation. The chosen class provider is invoked and then result of invocation is returned.
   *
   * @param property the property for which will be invoked the proper class provider.
   * @return The result of class provider invocation.
   * @throws ClassProviderException If some error occurs during class provider invocation.
   */
  public Class<?> get(Property<?> property) throws ClassProviderException;

  /**
   * Analyzes annotations for given property and then choose the proper class provider for
   * invocation. The chosen class provider is invoked multiple times (parameter <code>count</code>)
   * and then array of invocation results is returned.
   *
   * @param property the property for which will be invoked the proper class provider.
   * @param count the number of class provider invocations.
   * @return Array of results of class provider invocations.
   * @throws ClassProviderException If some error occurs during class provider invocation.
   */
  public Class<?>[] get(Property<?> property, int count) throws ClassProviderException;
}
