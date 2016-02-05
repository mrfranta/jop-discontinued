package cz.zcu.kiv.jop.annotation.generator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random numeric value with
 * <em>Exponential distribution</em> (a.k.a. negative exponential distribution) which is the
 * probability distribution that describes the time between events in a <em>Poisson process</em>,
 * i.e. a process in which events occur continuously and independently at a constant average rate.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Exponential_distribution"> Exponential
 *      distribution</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExponentialGenerator {

  /**
   * A number generator that provides values to use as the rate of random events occurrence in
   * <em>Poisson process</em>. The value has to be greater than 0.
   */
  public double rate();

}
