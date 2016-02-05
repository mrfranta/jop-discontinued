package cz.zcu.kiv.jop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation may be used for determination of an access type of specific attribute or all
 * attributes of generated class.
 * <p>
 * This annotation isn't inherited and each class may specify a type of access for its attributes.
 * If is used for class, then each attribute can change an access type for itself using this
 * annotation.
 * <p>
 * If this annotation isn't used, the default access type is used - each attribute is property with
 * getter and setter (which may not have public access).
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {

  /**
   * Required parameter which specifies an access type of specific attribute or all attributes of
   * generated class.
   */
  public AccessType value();

}
