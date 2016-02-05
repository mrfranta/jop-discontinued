package cz.zcu.kiv.jop.annotation.generator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.generator.ValueGenerator;

/**
 * This annotation marks property for which will be generated value using specific
 * {@link ValueGenerator value generator}. The parameters for given value generator may be set in
 * separated annotation for property.
 *
 * @see ValueGenerator
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@CustomAnnotation
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomValueGenerator {

  /**
   * Required parameter for value generator which provides generated value.
   */
  public Class<? extends ValueGenerator<?, ?>> value();

}
