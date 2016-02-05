package cz.zcu.kiv.jop.annotation.generator.bool;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random boolean value with
 * <em>Bernoulli distribution</em>. The result can be the value <code>true</code> with success
 * probability of {@link #probability() p} and the value <code>false</code> with failure probability
 * of <code>q = 1 - p</code>.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bernoulli_distribution">Bernoulli distribution</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomBoolean {

  /**
   * Required parameter for probability of success (generator provides the <code>true</code> value).
   */
  public double probability();

}
