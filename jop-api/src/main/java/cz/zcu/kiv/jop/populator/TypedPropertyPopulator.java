package cz.zcu.kiv.jop.populator;

import java.lang.annotation.Annotation;

import cz.zcu.kiv.jop.property.Property;

/**
 * The common interface for all populators of properties which can be used for populating of values
 * into properties. They can be used in combination with another populator,
 * {@link cz.zcu.kiv.jop.generator.ValueGenerator ValueGenerator} or they can contains some custom
 * populating strategy.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Type of property which can be populated by this populator.
 * @param <P> Annotation type of parameters for populator of property.
 */
public interface TypedPropertyPopulator<T, P extends Annotation> {

  /**
   * Populates given property according to given parameters. Populator can use additional
   * annotations which can be obtained from given property.
   *
   * @param property the property which will be populated.
   * @param params the parameters for populating of given property.
   * @throws PropertyPopulatorException if some error occurs during populating of given property or
   *           if given parameters are not valid.
   */
  public void populate(Property<? extends T> property, P params) throws PropertyPopulatorException;

}
