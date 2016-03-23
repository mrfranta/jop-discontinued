package cz.zcu.kiv.jop.annotation.populator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves as marker for populator of properties (fields).They can be combined with
 * annotations marked by {@link cz.zcu.kiv.jop.annotation.generator.ValueGeneratorAnnotation
 * ValueGeneratorAnnotation} which provides the generated values which will be populated into
 * annotated field.
 * <p>
 * Also may be combined with annotations marked as populator. The chaning of populators is specified
 * by order of
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PopulatorAnnotation {

}
