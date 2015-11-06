package cz.zcu.kiv.jop.annotation.generator;

import cz.zcu.kiv.jop.generator.ValueGenerator;

/**
 * This annotation marks property for which will be generated value using
 * specific {@link ValueGenerator value generator}.
 *
 * @see ValueGenerator
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public @interface CustomValueGenerator {

  /**
   * Required parameter for value generator which provides generated value.
   */
  public Class<? extends ValueGenerator<?, ?>> value();

}
