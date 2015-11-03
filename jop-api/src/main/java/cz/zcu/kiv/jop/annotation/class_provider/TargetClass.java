package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * Class provider annotation which determines the specific (target) class which
 * may be used for new instance creation.
 * <p>
 * This annotation can be also used as generator annotation which marks property
 * for which will be generated of class (<code>Class&lt;?&gt;</code>) value.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@ClassProviderAnnotation
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetClass {

  /**
   * Required parameter for class type which will be returned by class provider
   * or value generator.
   */
  public Class<?> value();

}
