package cz.zcu.kiv.jop.annotation.generator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random integer
 * value with <em>Binomial distribution</em>. It is discrete probability
 * distribution of the number of successes in a sequence of independent yes/no
 * experiments, each of which yields success with some probability. A
 * success/failure experiment is also called a <em>Bernoulli experiment</em> or
 * <em>Bernoulli trial</em>; when <code>n = 1</code>, the binomial distribution
 * is a <em>Bernoulli distribution</em>.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Binomial_distribution">Binomial
 *      distribution</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BinomialGenerator {

  /**
   * Required parameter for number of independent yes/no experiments (trials).
   * Value of trials count has to be greater than 0.
   */
  public int trials();

  /**
   * Required parameter for probability of individual experiment (trial)
   * success. Probability value has to be greater or equal to 0 and lesser or
   * equal to 1.
   */
  public double probability();

}
