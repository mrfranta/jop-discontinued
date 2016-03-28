package cz.zcu.kiv.jop.generator.number;

import java.util.Random;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.number.DiscreteUniformGenerator;
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
public class DiscreteUniformNumberGenerator extends AbstractValueGenerator<Integer, DiscreteUniformGenerator> {

  /**
   * {@inheritDoc}
   */
  public Class<Integer> getValueType() {
    return Integer.class;
  }

  /**
   * Returns random integer value between minimum (inclusive) and maximum (inclusive) values given
   * in parameters. Values are generated with discrete uniform distribution.
   *
   * @param params the parameters of (random) number value generator.
   * @return Generated random integer value between minimum and maximum value.
   * @throws ValueGeneratorException If given parameters are not valid.
   */
  public Integer getValue(DiscreteUniformGenerator params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    int min = params.min();
    int max = params.max();

    if (min > max) {
      throw new ValueGeneratorException("Minimum value is greater than maximum");
    }

    // values are same => constant generator
    if (min == max) {
      return min;
    }

    Random rand = getRandomGenerator(params);
    org.uncommons.maths.random.DiscreteUniformGenerator generator =
        new org.uncommons.maths.random.DiscreteUniformGenerator(params.min(), params.max(), rand);

    return generator.nextValue();
  }

}
