package cz.zcu.kiv.jop.generator.bool;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.bool.RandomBoolean;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of boolean generator for annotation {@link RandomBoolean}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class RandomBooleanGenerator extends AbstractValueGenerator<Boolean, RandomBoolean> {

  /**
   * {@inheritDoc}
   */
  public Class<Boolean> getValueType() {
    return Boolean.TYPE;
  }

  /**
   * Returns random boolean value according to probability in given parameters.
   *
   * @param params the parameters of (random) boolean value generator.
   * @return Random boolean value.
   * @throws ValueGeneratorException If probability in given parameters is not between 0.0 and 1.0.
   */
  public Boolean getValue(RandomBoolean params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    double probability = params.probability();
    if (probability < 0.0 || probability > 1.0) {
      throw new ValueGeneratorException("Invalid value of probability: " + probability);
    }

    return (nextDouble(params) < probability) ? Boolean.TRUE : Boolean.FALSE;
  }
}
