package cz.zcu.kiv.jop.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.CustomValueGenerator;
import cz.zcu.kiv.jop.annotation.parameters.CustomParameters;

/**
 * Parameters for {@link cz.zcu.kiv.jop.example.generator.DateValueGenerator RandomDateGenerator}
 * which generates dates between {@link #since()} and today.
 *
 * @author Mr.FrAnTA
 */
@CustomParameters(CustomValueGenerator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateGeneratorParameters {

  /** Required parameter for minimal date in format yyyy-MM-dd. */
  public String since();

}
