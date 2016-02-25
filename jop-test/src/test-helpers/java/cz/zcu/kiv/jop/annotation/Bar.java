package cz.zcu.kiv.jop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Helper marked annotation which may be used in tests.
 *
 * @author Mr.FrAnTA
 */
@Marker
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Bar {

  /**
   * Attribute for value.
   */
  public int value();

}
