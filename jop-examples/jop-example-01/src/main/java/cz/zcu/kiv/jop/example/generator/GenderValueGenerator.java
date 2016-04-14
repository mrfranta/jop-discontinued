package cz.zcu.kiv.jop.example.generator;

import java.util.Random;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;
import cz.zcu.kiv.jop.example.obj.Gender;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of the value generator of genders for persons.
 *
 * @author Mr.FrAnTA
 */
@Singleton
public class GenderValueGenerator extends AbstractValueGenerator<Gender, EmptyParameters> {

  /**
   * {@inheritDoc}
   */
  public Class<Gender> getValueType() {
    return Gender.class;
  }

  /**
   * Generates random gender.
   */
  public Gender getValue(EmptyParameters params) throws ValueGeneratorException {
    Random random = getRandomGenerator(params);

    return (random.nextDouble() < 0.48 ? Gender.MALE : Gender.FEMALE);
  }
}
