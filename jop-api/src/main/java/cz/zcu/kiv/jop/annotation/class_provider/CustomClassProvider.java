package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;
import cz.zcu.kiv.jop.class_provider.ClassProvider;

/**
 * Class provider annotation which determines the specific {@link ClassProvider
 * class provider} which provides class type that will be used for new instance
 * creation.
 * <p>
 * This annotation can be also used as generator annotation which marks property
 * for which will be generated of class (<code>Class&lt;?&gt;</code>) value.
 *
 * @see ClassProvider
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@ClassProviderAnnotation
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomClassProvider {

  /**
   * Required parameter for class provider which provides class type that will
   * be returned by class provider or value generator.
   */
  public Class<? extends ClassProvider<?>> value();

}
