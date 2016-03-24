package cz.zcu.kiv.jop.annotation.strategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation presents a populating strategy which allows to assign new instance into field. In
 * such case, the class that will be used to create new instances have to be supplied (as the
 * reference is usually typed only to the interface), and the reference will be assigned to a new
 * instance of provided class. Afterwards the created instance is enqueued for population of
 * properties. This annotation may be used in combination with annotation for class provider.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@PopulatingStrategyAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NewInstance {

}
