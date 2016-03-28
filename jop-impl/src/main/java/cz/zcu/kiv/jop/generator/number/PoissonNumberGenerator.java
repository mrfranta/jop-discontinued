package cz.zcu.kiv.jop.generator.number;

import java.util.Random;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.number.PoissonGenerator;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of number generator for annotation {@link PoissonGenerator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class PoissonNumberGenerator extends AbstractValueGenerator<Integer, PoissonGenerator> {

  /**
   * {@inheritDoc}
   */
  public Class<Integer> getValueType() {
    return Integer.class;
  }

  /**
   * Returns random integer value with Poisson distribution for mean of given parameters.
   *
   * @param params the parameters of (random) number value generator.
   * @return Generated random integer value.
   * @throws ValueGeneratorException If given parameters are not valid.
   */
  public Integer getValue(PoissonGenerator params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    double mean = params.mean();
    if (mean <= 0) {
      throw new ValueGeneratorException("Mean cannot be negative");
    }

    Random rand = getRandomGenerator(params);
    org.uncommons.maths.random.PoissonGenerator generator =
        new org.uncommons.maths.random.PoissonGenerator(params.mean(), rand);

    return generator.nextValue();
  }
}
