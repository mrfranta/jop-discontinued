package cz.zcu.kiv.jop.annotation.populator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.defaults.Default;

/**
 * Annotation for populator of array properties. This populator annotation can be combined with
 * another populator annotation(s) or generator annotation. It allows to populate array of custom or
 * declared type with exact or random size.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@PopulatorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArrayValue {

  /**
   * Optional parameter for minimal size of populated array property. Value of minimum has to be
   * lesser than or equals to {@link #max() maximum} and has to be greater than or equals to 0. The
   * default value is 0.
   */
  public int min() default 0;

  /**
   * Optional parameter for maximal size of populated array property. Value of maximum has to be
   * greater than or equals to {@link #min() minimum} with one exception - if the value is lower
   * than 0. The default value is -1.
   * <p>
   * In case that maximum value is positive, the random number of elements between minimum and
   * maximum will be created. In case that maximum is negative, some random value will be chosen for
   * maximum (not recommended because it can be also {@link Integer#MAX_VALUE}.
   */
  public int max() default -1;

  /**
   * Optional parameter for exact size of populated array property. If the value of this parameter
   * is greater than or equals to 0, parameters for {@link #min() minimum} and {@link #max()
   * maximum} are ignored. The default value is -1.
   */
  public int size() default -1;

  /**
   * Optional parameter which can specify the target class type populated into annotated property.
   * This annotation is useful in case that property is declared by abstract, interface or object
   * type.
   */
  public Class<?> target() default Default.class;

}
