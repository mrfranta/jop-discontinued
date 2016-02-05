package cz.zcu.kiv.jop.annotation.matcher;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.matcher.TypedInstanceMatcher;

/**
 * Objects (instances) matcher annotation which determines the specific instance of
 * {@link TypedInstanceMatcher matcher} which decides which already generated object (instance) can
 * be set into populated field. The parameters for given instance matcher may be set in separated
 * annotation for property.
 *
 * @see TypedInstanceMatcher
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@CustomAnnotation
@InstanceMatcherAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomInstanceMatcher {

  /**
   * Required parameter for instance matcher which decides which already generated object (instance)
   * can be set into populated field.
   */
  public Class<? extends TypedInstanceMatcher<?, ?>> value();

}
