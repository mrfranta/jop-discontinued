package cz.zcu.kiv.jop.annotation.populator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation serves as marker for populator of properties (fields).
 * Populator annotations are very similar to annotations which are marked by
 * {@link cz.zcu.kiv.jop.annotation.strategy.StrategyAnnotation
 * StrategyAnnotation}.
 * <p>
 * They can be combined with annotations marked by
 * {@link cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation
 * GeneratorAnnotation} which provides the generated values which will be
 * populated into annotated field.
 * <p>
 * Also may be combined in case that parameter {@link #value()} is set to
 * {@link PopulatorType#MASTER MASTER} with another populator which has this
 * parameter set to {@link PopulatorType#SLAVE SLAVE}. Two master/slave
 * populator annotations cannot be combined (to prevent long chaining in case of
 * master type).
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PopulatorAnnotation {

  /**
   * Optional parameter for type of property populator. If the type arameter is
   * set to {@link PopulatorType#MASTER MASTER}, this populator annotation can
   * be combined with another populator which has this parameter set to
   * {@link PopulatorType#SLAVE SLAVE}. Two master populator annotations cannot
   * be combined (to prevent long chaining). Default value of this parameter is
   * {@link PopulatorType#SLAVE SLAVE}.
   */
  public PopulatorType value() default PopulatorType.SLAVE;

}
