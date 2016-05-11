package cz.zcu.kiv.jop.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.matcher.CustomInstanceMatcher;
import cz.zcu.kiv.jop.annotation.parameters.CustomParameters;
import cz.zcu.kiv.jop.example.domain.Gender;

/**
 * Parameters for {@link cz.zcu.kiv.jop.example.matcher.ChildInstanceMatcher ChildInstanceMatcher}
 * which matches (filters) children with specified {@link #gender()}.
 *
 * @author Mr.FrAnTA
 */
@CustomParameters(CustomInstanceMatcher.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChildInstanceMatcherParameters {

  /** Required for gender of accepted children by instance matcher of children. */
  public Gender gender();

}
