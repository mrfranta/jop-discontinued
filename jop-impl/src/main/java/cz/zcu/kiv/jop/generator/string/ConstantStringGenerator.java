package cz.zcu.kiv.jop.generator.string;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.string.ConstantString;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * Implementation of constant string generator for annotation {@link ConstantString}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ConstantStringGenerator extends AbstractValueGenerator<String, ConstantString> {

  /**
   * {@inheritDoc}
   */
  public Class<String> getValueType() {
    return String.class;
  }

  /**
   * Returns constant string value from parameter {@link ConstantString#value() value}.
   *
   * @param params the parameters of constant string value generator.
   * @return Constant string value from parameter {@link ConstantString#value() value}.
   * @throws ValueGeneratorException If given annotation is <code>null</code>.
   */
  public String getValue(ConstantString params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    return params.value();
  }
}
