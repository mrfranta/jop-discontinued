package cz.zcu.kiv.jop.annotation.parameters;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;

/**
 * This annotation serves as marker for custom parameters which may be used in combination with
 * custom annotations for class provider, value generator, etc.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomParameters {

  /**
   * Optional parameter which may be used for linkage between custom parameters and custom
   * annotation for class provider, value generator, etc. If the parameter is not set the linkage
   * will be performed dynamically using reflection.
   */
  public Class<? extends Annotation> value() default CustomAnnotation.class;

}
