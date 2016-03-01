package cz.zcu.kiv.jop.class_provider;

import cz.zcu.kiv.jop.property.Property;

/**
 * Interface which serves as interlayer between the class providers and another parts of this
 * library. Implementation of this interface should analyze the annotations of given property and
 * depending on them should choose and invoke some class provider. After invocation is returned the
 * result of chosen class provider.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ClassProviderInvoker {

  /**
   * Analyzes annotations for given property and then choose the proper class provider. The chosen
   * class provider is invoked and then result of invocation is returned.
   *
   * @param property the property for which will be invoked the proper class provider.
   * @return The result of class provider invocation.
   * @throws ClassProviderException If some error occurs during class provider invocation.
   */
  public Class<?> getClass(Property<?> property) throws ClassProviderException;

}
