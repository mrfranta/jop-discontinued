package cz.zcu.kiv.jop.generator.number;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.number.ConstantGenerator;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of constant number generator for annotation {@link ConstantGenerator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ConstantNumberGenerator extends AbstractValueGenerator<Double, ConstantGenerator> {

  /**
   * {@inheritDoc}
   */
  public Class<Double> getValueType() {
    return Double.class;
  }

  /**
   * Returns constant number value from parameter {@link ConstantDouble#value() value}.
   *
   * @param params the parameters of constant number value generator.
   * @return Constant number value from parameter {@link ConstantDouble#value() value}.
   * @throws ValueGeneratorException If given annotation is <code>null</code>.
   */
  public Double getValue(ConstantGenerator params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    return params.value();
  }
}
