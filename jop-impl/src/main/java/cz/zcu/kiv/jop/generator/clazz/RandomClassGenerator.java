package cz.zcu.kiv.jop.generator.clazz;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.class_provider.RandomClass;
import cz.zcu.kiv.jop.generator.CategoricalGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of categorical class generator for annotation {@link RandomClass}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class RandomClassGenerator extends CategoricalGenerator<Class<?>, RandomClass> {

  /**
   * Returns random class type with categorical distribution according to given parameters.
   */
  public Class<?> getValue(RandomClass params) throws ValueGeneratorException {
    return getRandomValue(params);
  }

}
