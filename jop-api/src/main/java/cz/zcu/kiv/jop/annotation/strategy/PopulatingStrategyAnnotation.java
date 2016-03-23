package cz.zcu.kiv.jop.annotation.strategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves as marker for populating strategy annotations. populating strategy
 * annotations control populating of dependent objects and object fields.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PopulatingStrategyAnnotation {

}
