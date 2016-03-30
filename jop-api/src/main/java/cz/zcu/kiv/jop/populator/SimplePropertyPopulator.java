package cz.zcu.kiv.jop.populator;

import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;
import cz.zcu.kiv.jop.property.Property;

/**
 * Abstract implementation of {@link PropertyPopulator} interface which don't need parameters for
 * populating of properties. Also implementations of this class can be easily used in
 * {@link cz.zcu.kiv.jop.annotation.populator.CustomPropertyPopulator CustomPropertyPopulator}
 * annotation.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class SimplePropertyPopulator implements PropertyPopulator<EmptyParameters> {

  /**
   * Populates given property according to given parameters. Populator can use additional
   * annotations which can be obtained from given property.
   * <p>
   * This method is final and serves only as bridge to method which is without parameters -
   * {@link #populate(Property, Object)}.
   *
   * @param property the property which will be populated.
   * @param owner the instance of property owner.
   * @param params the parameters for populating of given property.
   * @throws PropertyPopulatorException if some error occurs during populating of given property or
   *           if given parameters are not valid.
   */
  public final void populate(Property<?> property, Object owner, EmptyParameters params) throws PropertyPopulatorException {
    populate(property, owner);
  }

  /**
   * Populates given property. Populator can use additional annotations which can be obtained from
   * given property.
   *
   * @param property the property which will be populated.
   * @param owner the instance of property owner.
   * @throws PropertyPopulatorException if some error occurs during populating of given property or
   *           if given parameters are not valid.
   */
  public abstract void populate(Property<?> property, Object owner) throws PropertyPopulatorException;

}
