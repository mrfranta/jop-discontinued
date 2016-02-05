package cz.zcu.kiv.jop.annotation.strategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation presents a generating strategy which allows to forbid the generating of referred
 * object (field). In such case will be the annotated field skipped and no value will be generated
 * for that field. Also the value of the field won't be changed. This is default strategy - if the
 * field has no another generating strategy annotation, this strategy will be used.
 * <p>
 * An example of an annotation usage:
 *
 * <pre>
 * &#064;Ignore
 * public Animal animal; // the value will be null
 * &#064;Ignore
 * public Animal cat = new Cat(); // the value won't be changed
 * public Animal dog = new Dog(); // the value won't be also changed
 * </pre>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@StrategyAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {

}
