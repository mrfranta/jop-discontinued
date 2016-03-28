package cz.zcu.kiv.jop.annotation.generator.number;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.ValueGeneratorAnnotation;

/**
 * This marks property into which will be set the constant value set in parameter {@link #value()}.
 * This is very useful for generate of some constant value into property or parameter which cannot
 * have the default value (in property or parameter declaration) and the constant value has to be
 * "generated".
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@ValueGeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstantGenerator {

  /**
   * Required parameter for constant value which will be generated (set) into annotated property or
   * parameter.
   */
  public double value();

}
