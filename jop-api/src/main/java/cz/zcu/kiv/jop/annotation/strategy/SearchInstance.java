package cz.zcu.kiv.jop.annotation.strategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation presents a generating strategy which allows to use already
 * existing instance which will be searched in list of already generated
 * objects. This annotation may be used in combination with annotation for
 * instance matcher.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@StrategyAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchInstance {

}
