package cz.zcu.kiv.jop.generator.number;

import java.util.Random;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.number.GaussianGenerator;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of number generator for annotation {@link GaussianGenerator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class GaussianNumberGenerator extends AbstractValueGenerator<Double, GaussianGenerator> {

  /**
   * {@inheritDoc}
   */
  public Class<Double> getValueType() {
    return Double.class;
  }

  /**
   * Returns random double value with Gaussian distribution with given mean and variance in
   * parameters.
   *
   * @param params the parameters of (random) number value generator.
   * @return Generated random double value.
   * @throws ValueGeneratorException If given parameters are not valid.
   */
  public Double getValue(GaussianGenerator params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    double mean = params.mean();
    // variance (standard deviation) - has to be positive or zero
    double variance = params.variance();
    if (variance < 0) {
      throw new ValueGeneratorException("Variance cannot be negative");
    }

    Random rand = getRandomGenerator(params);
    org.uncommons.maths.random.GaussianGenerator generator =
        new org.uncommons.maths.random.GaussianGenerator(mean, variance, rand);

    return generator.nextValue();
  }
}
