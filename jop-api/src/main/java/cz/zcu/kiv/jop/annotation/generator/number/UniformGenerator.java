package cz.zcu.kiv.jop.annotation.generator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random numeric value with
 * <em>Continuous uniform distribution</em> (symmetric probability distribution).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Uniform_distribution_%28continuous%29"> Continuous
 *      uniform distribution</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniformGenerator {

  /**
   * Required parameter for minimum of generated value. Value of minimum has to be lesser than
   * {@link #max() maximum}.
   */
  public double min();

  /**
   * Required parameter for maximum of generated value. Value of maximum has to be greater than
   * {@link #min() minimum}.
   */
  public double max();

}
