package cz.zcu.kiv.jop.example.generator;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.example.annotation.DictionaryGenerator;
import cz.zcu.kiv.jop.factory.binding.BindingException;
import cz.zcu.kiv.jop.generator.ValueGeneratorFactoryImpl;

/**
 * Custom implementation of factory of value generators which extends the default implementation.
 * This implementation contains custom bindings of value generators.
 *
 * @author Mr.FrAnTA
 */
@Singleton
public class CustomValueGeneratorFactory extends ValueGeneratorFactoryImpl {

  /**
   * Configures custom bindings.
   */
  @Override
  protected void customConfigure() throws BindingException {
    bind(DictionaryGenerator.class).to(DictionaryValueGenerator.class);
  }

}
