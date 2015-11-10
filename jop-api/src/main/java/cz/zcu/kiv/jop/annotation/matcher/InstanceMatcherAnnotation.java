package cz.zcu.kiv.jop.annotation.matcher;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves as marker for objects (instances) matchers which may
 * be used in combination with
 * {@link cz.zcu.kiv.jop.annotation.strategy.SearchInstance SearchInstance}
 * annotation to determine which object (instance) can be set into populated
 * field.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InstanceMatcherAnnotation {

}
