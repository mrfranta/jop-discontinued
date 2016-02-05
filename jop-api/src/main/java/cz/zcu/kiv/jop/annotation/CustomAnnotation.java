package cz.zcu.kiv.jop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves as marker for custom annotations for class provider, value generator, etc.
 * annotations. The custom annotations has to be marked by this annotation because they have
 * external annotation for parameters and the processor of annotations has to recognize, that is has
 * to lookup for parameters in another annotations of property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAnnotation {

}
