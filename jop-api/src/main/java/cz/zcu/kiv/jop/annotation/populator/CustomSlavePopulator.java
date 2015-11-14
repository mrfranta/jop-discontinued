package cz.zcu.kiv.jop.annotation.populator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.Empty;
import cz.zcu.kiv.jop.populator.TypedPropertyPopulator;

/**
 * This slave populator annotation marks property which will be populated by
 * specific {@link TypedPropertyPopulator property populator}. The property
 * populator cannot support parameters (only supported is {@link Empty}
 * annotation for empty parameters).
 *
 * @see TypedPropertyPopulator
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@PopulatorAnnotation(PopulatorType.SLAVE)
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomSlavePopulator {

  /**
   * Required parameter for class type of property populator which populates
   * annotated property.
   */
  public Class<? extends TypedPropertyPopulator<?, Empty>> value();

}
