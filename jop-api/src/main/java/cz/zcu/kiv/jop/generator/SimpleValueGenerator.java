package cz.zcu.kiv.jop.generator;

import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;

/**
 * Abstract implementation of {@link ValueGenerator} interface which don't need parameters for
 * generating of values. Also implementations of this class can be used in
 * {@link cz.zcu.kiv.jop.annotation.generator.CustomValueGenerator CustomValueGenerator} annotation.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Type of generated values.
 */
public abstract class SimpleValueGenerator<T> implements ValueGenerator<T, EmptyParameters> {

  /**
   * Returns generated value according to given parameters.
   *
   * @param params the parameters for generation of value (unused).
   * @return Generated value.
   * @throws ValueGeneratorException if some error occurs during value generation.
   */
  public final T getValue(EmptyParameters params) throws ValueGeneratorException {
    return getValue();
  }

  /**
   * Returns generated value.
   *
   * @return Generated value.
   * @throws ValueGeneratorException if some error occurs during value generation.
   */
  public abstract T getValue() throws ValueGeneratorException;

}
