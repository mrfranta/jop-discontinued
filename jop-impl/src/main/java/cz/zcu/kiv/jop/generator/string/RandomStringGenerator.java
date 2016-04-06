package cz.zcu.kiv.jop.generator.string;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.string.RandomString;
import cz.zcu.kiv.jop.generator.CategoricalGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of categorical string generator for annotation {@link RandomString}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class RandomStringGenerator extends CategoricalGenerator<String, RandomString> {

  /**
   * Returns random string value with categorical distribution according to given parameters.
   */
  public String getValue(RandomString params) throws ValueGeneratorException {
    return getRandomValue(params);
  }

}
