package cz.zcu.kiv.jop.annotation.generator.string;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random string value based on
 * <em>Regular expression</em>. This generator transforms the regular expression to
 * <em><a href="https://en.wikipedia.org/wiki/Finite-state_machine">
 * FSM</a></em>. The transitions again represents the generating of next character to string, but in
 * this time, probability of all characters in each state is equal.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Regular_expression">Regular expression</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegularExpression {

  /**
   * Required parameter for regular expression which will be used for generating of random string
   * value.
   */
  public String value();

  /**
   * Optional parameter for maximal number of characters in generated string. The value has to be
   * greater or equal to 0.
   */
  public int maxLen() default Integer.MAX_VALUE;

}
