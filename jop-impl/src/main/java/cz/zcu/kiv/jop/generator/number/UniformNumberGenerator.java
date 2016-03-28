package cz.zcu.kiv.jop.generator.number;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.number.UniformGenerator;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of number generator for annotation {@link UniformGenerator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class UniformNumberGenerator extends AbstractValueGenerator<Double, UniformGenerator> {

  /**
   * {@inheritDoc}
   */
  public Class<Double> getValueType() {
    return Double.class;
  }

  /**
   * Returns random double value between minimum (inclusive) and maximum (inclusive) values given in
   * parameters. Values are generated with uniform distribution.
   *
   * @param params the parameters of (random) number value generator.
   * @return Generated random double value between minimum and maximum value.
   * @throws ValueGeneratorException If given parameters are not valid.
   */
  public Double getValue(UniformGenerator params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    double min = params.min();
    double max = params.max();

    if (min > max) {
      throw new ValueGeneratorException("Minimum value is greater than maximum");
    }

    // values are same => constant generator
    if (Double.compare(min, max) == 0) {
      return min;
    }

    return nextDouble(params, min, max);
  }
}
