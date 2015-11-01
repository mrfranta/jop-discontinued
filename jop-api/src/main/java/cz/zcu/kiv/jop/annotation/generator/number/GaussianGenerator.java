package cz.zcu.kiv.jop.annotation.generator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random numeric
 * value with <em>Gaussian (Normal) distribution</em> (very common continuous
 * probability distribution).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Normal_distribution">Normal
 *      distribution</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface GaussianGenerator {

  /**
   * Required parameter for mean - the expected value of a random generator.
   */
  public double mean();

  /**
   * Required parameter for variance which measures how far a set of numbers is
   * spread out from the mean value. A variance of zero indicates that all the
   * values are identical. Variance is always non-negative: a small variance
   * indicates that the data points tend to be very close to the {@link #mean()
   * mean} (expected value) and hence to each other, while a high variance
   * indicates that the data points are very spread out around the mean and from
   * each other.
   */
  public double variance();

}
