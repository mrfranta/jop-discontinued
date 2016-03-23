package cz.zcu.kiv.jop.annotation.strategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation presents a populating strategy which allows to forbid populating of referred
 * object (field). In such case the reference will be set on <code>null</code> and the dependency
 * will not be created. Also the value of the field will be &quot;replaced&quot;. This annotation
 * can be also used for primitive types for which will be used default value (for example 0 for
 * numeric types).
 * <p>
 * An example of an annotation usage:
 *
 * <pre>
 * &#064;NullValue
 * public Animal animal; // the value will be null
 * &#064;NullValue
 * public Animal cat = new Cat(); // the value will be changed to null
 * &#064;NullValue
 * public int animalsCount = 10; // the value will be changed to 0 (default value)
 * </pre>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@PopulatingStrategyAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullValue {

}
