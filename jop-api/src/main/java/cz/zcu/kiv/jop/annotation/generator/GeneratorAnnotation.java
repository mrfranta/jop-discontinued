package cz.zcu.kiv.jop.annotation.generator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves as marker for generators of (random) values for
 * populated properties. Each annotation for generator has to be annotated with
 * this annotation or it won't be possible to bind it to some implementation of
 * generator.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratorAnnotation {

}
