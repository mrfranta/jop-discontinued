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
   * Required parameter for probabilities of outcome values. The number of
   * probabilities has to be same like number of values. Also value of each
   * probability should be greater or equal to 0 and lesser or equal to 1.To
   * ensure that the sum of probabilities will be always 1, the values are
   * automatically normalized.
   */
  public double[] probabilities();

}
