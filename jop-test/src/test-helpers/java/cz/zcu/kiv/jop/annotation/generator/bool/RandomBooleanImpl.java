package cz.zcu.kiv.jop.annotation.generator.bool;

import java.lang.annotation.Annotation;

/**
 * Implementation of annotation {@link RandomBoolean}.
 *
 * @author Mr.FrAnTA
 */
public class RandomBooleanImpl implements RandomBoolean {

  /** The value of parameter probability. */
  private final double probability;

  /**
   * Constructs the annotation.
   *
   * @param probability probability of annotation.
   */
  public RandomBooleanImpl(double probability) {
    this.probability = probability;
  }

  /**
   * Returns type of annotation (this class).
   *
   * @return type of annotation.
   */
  public Class<? extends Annotation> annotationType() {
    return getClass();
  }

  /**
   * @return The value of parameter probability.
   */
  public double probability() {
    return probability;
  }

}
