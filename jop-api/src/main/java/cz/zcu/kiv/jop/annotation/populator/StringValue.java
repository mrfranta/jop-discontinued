package cz.zcu.kiv.jop.annotation.populator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.defaults.DefaultCharSequence;

/**
 * Annotation for populator of string (character sequence) properties. This annotation can be used
 * as limiting conditions or as mapper for populating of any value which will be "mapped" into
 * string (char sequence). It also may be used for determination of target implementation type in
 * case that property is declared by abstract class or interface.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@PropertyPopulatorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValue {

  /**
   * Optional parameter for minimal length of populated string (character sequence) property. Value
   * of minimum has to be lesser than or equals to {@link #maxLength() maximal length} and has to be
   * greater than or equals to 0. The default value is 0.
   * <p>
   * In case that populated value has lesser length than minimal length the value will be expanded
   * by by character from {@link #fill()} parameter.
   */
  public int minLength() default 0;

  /**
   * Optional parameter for maximal length of populated string (character sequence) property. Value
   * of maximum has to be greater than or equals to {@link #minLength() minimal length}.
   */
  public int maxLength() default Integer.MAX_VALUE;

  /**
   * Optional parameter for exact length of populated string (character sequence) property. If the
   * value of this parameter is greater than or equals to 0, parameters for {@link #minLength()
   * minimal length} and {@link #maxLength() maximal length} are ignored. The default value is -1.
   * <p>
   * In case that exact length is specified, the populated value may be cropped or expanded by
   * character from {@link #fill()} parameter to exact length of string (char sequence).
   */
  public int length() default -1;

  /**
   * Optional parameter for filling character which will be used in case that the exact length is
   * specified and the populated string (character sequence) has to be expanded.
   */
  public char fill() default ' ';

  /**
   * Optional parameter which may specify the target char sequence implementation which will be
   * populated into annotated property. This annotation is useful in case that property is declared
   * as {@link Object} or {@link CharSequence}.
   * <p>
   * If the default value {@link DefaultCharSequence} is used, the declared class of property will
   * be used. In case that the property is declared as {@link Object} or {@link CharSequence} and
   * this parameter contains default value, the class type returned by
   * {@link DefaultCharSequence#getDefaultClass()} will be used.
   */
  public Class<? extends CharSequence> target() default DefaultCharSequence.class;

}
