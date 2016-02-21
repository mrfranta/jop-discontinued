package cz.zcu.kiv.jop.annotation.generator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random numeric value with
 * <em>Poisson distribution</em> which is a discrete probability distribution that expresses the
 * probability of a given number of events occurring in a fixed interval of time and/or space if
 * these events occur with a known average rate and independently of the time since the last event.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Poisson_distribution">Poisson distribution</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PoissonGenerator {

  /**
   * Required parameter for mean of generated values. Mean has to be positive integer number.
   */
  public double mean();

}
