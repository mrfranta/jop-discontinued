package cz.zcu.kiv.jop.annotation.generator.string;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random string
 * value with <em>Categorical distribution</em> - as value of property will be
 * used one of value from {@link #values() values} based on their
 * {@link #probabilities() probabilities}.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomString {

  /**
   * Required parameter for outcome values of generator where each value has a
   * probability of occurrence in parameter {@link #probabilities()
   * probabilities} on same index like value.
   */
  public String[] values();

  /**
   * Optional parameter for probabilities of outcome values. If the
   * probabilities won't be set the uniform distribution will be used (all
   * values has same probability).
   * <p>
   * The number of probabilities should be lesser that or equals to number of
   * values. In case of lesser number of probabilities, the value 0.0 will be
   * used for rest of values.
   * <p>
   * Values of probabilities should be greater or equal to 0 and lesser or equal
   * to 1.0. To ensure that the summary of probabilities will be always 1.0, the
   * values are automatically normalized.
   */
  public double[] probabilities() default {};

}
