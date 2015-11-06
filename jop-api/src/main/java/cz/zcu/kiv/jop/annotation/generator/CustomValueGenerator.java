package cz.zcu.kiv.jop.annotation.generator;

import cz.zcu.kiv.jop.annotation.Empty;
import cz.zcu.kiv.jop.generator.ValueGenerator;

/**
 * This annotation marks property for which will be generated value using
 * specific {@link ValueGenerator value generator}. The value generator cannot
 * support parameters (only supported is {@link Empty} annotation for empty
 * parameters).
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
  public Class<? extends ValueGenerator<?, Empty>> value();

}
