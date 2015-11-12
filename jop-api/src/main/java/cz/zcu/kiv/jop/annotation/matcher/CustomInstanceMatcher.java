package cz.zcu.kiv.jop.annotation.matcher;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.Empty;
import cz.zcu.kiv.jop.matcher.TypedInstanceMatcher;

/**
 * Objects (instances) matcher annotation which determines the specific
 * {@link TypedInstanceMatcher instance matcher} which decides which already
 * generated object (instance) can be set into populated field. Instance matcher
 * cannot support parameters (only supported is {@link Empty} annotation for
 * empty parameters).
 *
 * @see TypedInstanceMatcher
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@InstanceMatcherAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomInstanceMatcher {

  /**
   * Required parameter for instance matcher which decides which already
   * generated object (instance) can be set into populated field.
   */
  public Class<? extends TypedInstanceMatcher<?, Empty>> value();

}
