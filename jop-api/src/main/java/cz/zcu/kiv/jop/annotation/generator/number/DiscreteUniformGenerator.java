package cz.zcu.kiv.jop.annotation.generator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random integer
 * value with <em>Discrete uniform distribution</em> (symmetric probability
 * distribution).
 *
 * @see <a
 *      href="https://en.wikipedia.org/wiki/Uniform_distribution_%28discrete%29">
 *      Discrete uniform distribution</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DiscreteUniformGenerator {

  /**
   * Required parameter for minimum of generated value. Value of minimum has to
   * be lesser than {@link #max() maximum}.
   */
  public int min();

  /**
   * Required parameter for maximum of generated value. Value of maximum has to
   * be greater than or equals {@link #min() minimum}.
   */
  public int max();

}
