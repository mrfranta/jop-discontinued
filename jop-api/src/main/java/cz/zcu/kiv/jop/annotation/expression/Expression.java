package cz.zcu.kiv.jop.annotation.expression;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows specify an expression which will be evaluated and the
 * result value will be set into annotated field. The expression can process
 * basic arithmetic, as well as invocation of functions from Math library.
 *
 * <p>
 * An example of an annotation usage:
 *
 * <pre>
 * &#064;Expression(&quot;rnd1 * atr1 + ref1.atr1&quot;)
 * protected int rnd1;
 * </pre>
 *
 * <p>
 * Value of the variable <code>rnd1</code> will be determined as result of the
 * provided expression (value of field <code>rnd1</code>). It is possible to
 * refer to all attributes of the same class (<code>atr1</code>) or of the
 * directly referenced classes (<code>ref1.atr1</code>).
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Expression {

  /**
   * Required parameter which specifies expression which will be evaluated and
   * the result value will be set into annotated field.
   */
  public String value();

}
