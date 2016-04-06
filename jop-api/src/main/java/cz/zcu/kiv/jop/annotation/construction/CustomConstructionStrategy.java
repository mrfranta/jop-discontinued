package cz.zcu.kiv.jop.annotation.construction;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.strategy.NewInstance;
import cz.zcu.kiv.jop.construction.ConstructionStrategy;

/**
 * Custom annotation for custom construction strategy which will be used for construction of new
 * instance of some object. This annotation may be used in combination with {@link NewInstance new
 * instance annotation}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomConstructionStrategy {

  /**
   * Required parameter for custom construction strategy.
   */
  public Class<? extends ConstructionStrategy> value();

}
