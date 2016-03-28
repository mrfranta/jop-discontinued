package cz.zcu.kiv.jop.generator.number;

import java.util.Random;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.number.BinomialGenerator;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of number generator for annotation {@link BinomialGenerator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class BinomialNumberGenerator extends AbstractValueGenerator<Integer, BinomialGenerator> {

  /**
   * {@inheritDoc}
   */
  public Class<Integer> getValueType() {
    return Integer.class;
  }

  /**
   * Returns random integer for number of successes in a sequence of independent yes/no experiments,
   * each of which yields success with some probability.
   *
   * @param params the parameters of (random) number value generator.
   * @return Generated random integer number.
   * @throws ValueGeneratorException If given parameters are not valid.
   */
  public Integer getValue(BinomialGenerator params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    // check number of trials
    int trials = params.trials();
    if (trials <= 0) {
      throw new ValueGeneratorException("Number of trials has to be positive");
    }

    // check and normalize probability
    double probability = params.probability();
    if (probability <= 0.0 || probability >= 1.0) {
      throw new ValueGeneratorException("Invalid value of probability: " + probability);
    }

    Random rand = getRandomGenerator(params);
    org.uncommons.maths.random.BinomialGenerator generator =
        new org.uncommons.maths.random.BinomialGenerator(trials, probability, rand);

    return generator.nextValue();
  }
}
