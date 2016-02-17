package cz.zcu.kiv.jop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Helper annotation which may be used in tests.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Foo {

  /**
   * Attribute for value.
   */
  public int value();

}
