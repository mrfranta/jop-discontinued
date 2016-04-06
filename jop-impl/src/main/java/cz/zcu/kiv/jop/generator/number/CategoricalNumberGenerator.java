package cz.zcu.kiv.jop.generator.number;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.number.CategoricalGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of categorical number generator for annotation {@link CategoricalGenerator}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class CategoricalNumberGenerator extends cz.zcu.kiv.jop.generator.CategoricalGenerator<Double, CategoricalGenerator> {

  /**
   * Returns random number with categorical distribution according to given parameters.
   */
  public Double getValue(CategoricalGenerator params) throws ValueGeneratorException {
    return getRandomValue(params);
  }

}
