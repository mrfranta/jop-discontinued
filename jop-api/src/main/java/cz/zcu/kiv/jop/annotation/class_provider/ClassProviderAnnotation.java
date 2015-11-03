package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves as marker for class provider annotations. The class
 * providers may be used in combination with
 * {@link cz.zcu.kiv.jop.annotation.strategy.NewInstance NewInstance} annotation
 * to determine which class may be used for new instance creation.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassProviderAnnotation {

}
