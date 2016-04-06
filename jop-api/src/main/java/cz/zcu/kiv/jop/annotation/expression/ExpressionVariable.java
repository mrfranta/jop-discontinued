package cz.zcu.kiv.jop.annotation.expression;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation "re-maps" variable (field) name for evaluation of {@link Expression Expressions}.
 * If this annotation is not used, the name of field is used as variable name.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpressionVariable {

  /**
   * Required parameter which specifies name of variable for expressions. The name shouldn't
   * contains the spaces or another special characters which are not allowed for for variables in
   * Java.
   */
  public String value();

}
