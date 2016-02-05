package cz.zcu.kiv.jop.annotation.generator;

import cz.zcu.kiv.jop.generator.ValueGenerator;

/**
 * This annotation marks property for which will be generated value using specific
 * {@link ValueGenerator value generator}. The parameters for given value generator may be set in
 * separated annotation for property.
 *
 * @see ValueGenerator
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public @interface CustomValueGenerator {

  /**
   * Required parameter for value generator which provides generated value.
   */
  public Class<? extends ValueGenerator<?, ?>> value();

}
