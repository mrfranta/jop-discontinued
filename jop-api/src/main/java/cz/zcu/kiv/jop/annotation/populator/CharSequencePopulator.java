package cz.zcu.kiv.jop.annotation.populator;

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
public @interface CharSequencePopulator {

  /**
   * Optional parameter for minimal length of populated string (character sequence) property. Value
   * of minimum has to be lesser than or equals to {@link #maxLength() maximum} and has to be
   * greater than or equals to 0. The default value is 0.
   * <p>
   * In case that populated value has lesser length than minimal length the value will be extended
   * by spaces.
   */
  public int minLength() default 0;

  /**
   * Optional parameter for maximal length of populated string (character sequence) property. Value
   * of maximum has to be greater than or equals to {@link #minLength() minimum} with one exception
   * - if the value is lower than 0. The default value is -1.
   * <p>
   * In case that maximum value is positive the populated value may be cropped to maximum length.
   */
  public int maxLength() default -1;

  /**
   * Optional parameter for exact length of populated string (character sequence) property. If the
   * value of this parameter is greater than or equals to 0, parameters for {@link #minLength()
   * minimum} and {@link #maxLength() maximum} are ignored. The default value is -1.
   * <p>
   * In case that exact length is specified, the populated value may be cropped or expanded by
   * spaces to exact length of string (char sequence).
   */
  public int length() default -1;

  /**
   * Optional parameter which can specify the target number class populated into annotated property.
   * This annotation is useful in case that property is declared by abstract numeric type.
   */
  public Class<? extends CharSequence> target() default DefaultCharSequence.class;

}
