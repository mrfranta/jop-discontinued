package cz.zcu.kiv.jop.annotation.populator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.populator.PropertyPopulator;

/**
 * Annotation for custom property populator which determines the specific {@link PropertyPopulator
 * property populator} which populates annotated property. The parameters for given property
 * populator may be set in separated annotation for property.
 *
 * @see PropertyPopulator
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@CustomAnnotation
@PopulatorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomPopulator {

  /**
   * Required parameter for class type of property populator which populates annotated property.
   */
  public Class<? extends PropertyPopulator<?>> value();

}
