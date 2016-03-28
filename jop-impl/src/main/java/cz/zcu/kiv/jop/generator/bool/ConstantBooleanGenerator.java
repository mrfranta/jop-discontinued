package cz.zcu.kiv.jop.generator.bool;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.bool.ConstantBoolean;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of constant boolean generator for annotation {@link ConstantBoolean}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ConstantBooleanGenerator extends AbstractValueGenerator<Boolean, ConstantBoolean> {

  /**
   * {@inheritDoc}
   */
  public Class<Boolean> getValueType() {
    return Boolean.class;
  }

  /**
   * Returns constant boolean value from parameter {@link ConstantBoolean#value() value}.
   *
   * @param params the parameters of constant boolean value generator.
   * @return Constant boolean value from parameter {@link ConstantBoolean#value() value}.
   * @throws ValueGeneratorException If given annotation is <code>null</code>.
   */
  public Boolean getValue(ConstantBoolean params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    return params.value();
  }
}
