package cz.zcu.kiv.jop.generator.number;

import java.util.Random;

import cz.zcu.kiv.jop.annotation.generator.number.ExponentialGenerator;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of number generator for annotation {@link ExponentialGenerator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class ExponentialNumberGenerator extends AbstractValueGenerator<Double, ExponentialGenerator> {

  /**
   * {@inheritDoc}
   */
  public Class<Double> getValueType() {
    return Double.TYPE;
  }

  /**
   * Returns random double value with Exponential distribution for rate of given parameters.
   *
   * @param params the parameters of (random) number value generator.
   * @return Generated random double value.
   * @throws ValueGeneratorException If given parameters are not valid.
   */
  public Double getValue(ExponentialGenerator params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    Random rand = getRandomGenerator(params);
    org.uncommons.maths.random.ExponentialGenerator generator =
        new org.uncommons.maths.random.ExponentialGenerator(params.rate(), rand);

    return generator.nextValue();
  }
}
