package cz.zcu.kiv.jop.generator.clazz;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.class_provider.TargetClass;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of class generator for annotation {@link TargetClass}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class TargetClassGenerator implements ValueGenerator<Class<?>, TargetClass> {

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Class<Class<?>> getValueType() {
    return (Class<Class<?>>)(Class<?>)Class.class;
  }

  /**
   * Returns class type from parameter {@link TargetClass#value() value()} of given annotation.
   *
   * @param params the parameters of class type generator.
   * @return Class given in parameter {@link TargetClass#value() value()}.
   * @throws ValueGeneratorException If given parameters are not valid.
   */
  public Class<?> getValue(TargetClass params) throws ValueGeneratorException {
    Class<?> value = AbstractValueGenerator.checkParamsNotNull(params).value(); // check not null

    if (value == null) {
      throw new ValueGeneratorException("Class type cannot be null");
    }

    return value;
  }
}
