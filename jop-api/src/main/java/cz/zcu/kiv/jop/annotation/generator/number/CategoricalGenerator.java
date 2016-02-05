package cz.zcu.kiv.jop.annotation.generator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random numeric value with
 * <em>Categorical distribution</em> (also called a &quot;generalized Bernoulli distribution&quot;,
 * &quot;multinoulli distribution&quot; or, less precisely, a &quot;discrete distribution&quot;) is
 * a probability distribution that describes the possible results of a random event that can take on
 * one of possible outcomes, with the probability of each outcome separately specified.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Categorical_distribution"> Categorical
 *      distribution</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoricalGenerator {

  /**
   * Required parameter for outcome values of generator where each value has a probability of
   * occurrence in parameter {@link #probabilities() probabilities} on same index like value.
   */
  public double[] value();

  /**
   * Optional parameter for probabilities of outcome values. If the probabilities won't be set the
   * uniform distribution will be used (all values has same probability).
   * <p>
   * The number of probabilities should be lesser that or equals to number of values. In case of
   * lesser number of probabilities, the value 0.0 will be used for rest of values.
   * <p>
   * Values of probabilities should be greater or equal to 0 and lesser or equal to 1.0. To ensure
   * that the summary of probabilities will be always 1.0, the values are automatically normalized.
   */
  public double[] probabilities() default {};

}
