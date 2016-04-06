package cz.zcu.kiv.jop.annotation.populator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import cz.zcu.kiv.jop.annotation.defaults.DefaultCollection;

/**
 * Annotation for populator of collection properties. This populator annotation can be combined with
 * another (slave) annotation. It allows to populate collection of custom or declared type with
 * exact or random size.
 * <p>
 * Notice: the final size of collection may be different than minimal or exact size of collection
 * because a {@link java.util.Set Sets} consumes equals objects - it prevents infinite loops for
 * populating.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@PropertyPopulatorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionValue {

  /**
   * Optional parameter for minimal size of populated collection property. Value of minimal size has
   * to be lesser than or equals to {@link #maxSize() maximal size} and has to be greater than or
   * equals to 0. The default value is 0.
   * <p>
   * The final size may be different than minimal size of collection because a {@link java.util.Set
   * Sets} consumes equals objects - it prevents infinite loops for populating.
   */
  public int minSize() default 0;

  /**
   * Optional parameter for maximal size of populated collection property. Value of maximal size has
   * to be greater than or equals to {@link #minSize() minimal size} with one exception - if the
   * value is lower than 0. The default value is -1.
   * <p>
   * In case that maximum value is positive, the random number of elements between minimum and
   * maximum will be created. In case that maximum is negative, some random value will be chosen for
   * maximum (not recommended because it can be also {@link Integer#MAX_VALUE}.
   */
  public int maxSize() default -1;

  /**
   * Optional parameter for exact size of populated collection property. If the value of this
   * parameter is greater than or equals to 0, parameters for {@link #minSize() minimal size} and
   * {@link #maxSize() maximal size} are ignored. The default value is -1.
   * <p>
   * The exact size may not be same like final size of collection because a {@link java.util.Set
   * Sets} consumes equals objects - it prevents infinite loops for populating.
   */
  public int size() default -1;

  /**
   * Optional parameter which can specify the target collection class type populated into annotated
   * property. This annotation is useful in case that property is declared by abstract, interface or
   * object type.
   */
  @SuppressWarnings("rawtypes")
  public Class<? extends Collection> target() default DefaultCollection.class;

}
