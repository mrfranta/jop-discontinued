package cz.zcu.kiv.jop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.util.AnnotationUtils;

/**
 * Helper annotation for test of proxies creation in {@link AnnotationUtils#getAnnotationProxy}.
 *
 * @author Mr.FrAnTA
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexAnnotation {

  // --- Defaults --------------------------------------------------------------

  /** Default value for member <code>value</code>. */
  public String DEFAULT_VALUE = "value";
  /** Default value for member <code>bool</code>. */
  public boolean DEFAULT_BOOL = true;
  /** Default value for member <code>integers</code>. */
  public int[] DEFAULT_INTEGERS = {1, 2, 3, 4, 5};
  /** Default value for member <code>strings</code>. */
  public String[] DEFAULT_STRINGS = new String[] {"1", "2", "3", "4", "5"};

  // --- Members ---------------------------------------------------------------

  /**
   * Member <code>value</code>.
   */
  public String value() default DEFAULT_VALUE;

  /**
   * Member <code>bool</code>.
   */
  public boolean bool() default DEFAULT_BOOL;

  /**
   * Member <code>integers</code>.
   */
  public int[] integers() default {1, 2, 3, 4, 5};

  /**
   * Member <code>strings</code>.
   */
  public String[] strings() default {"1", "2", "3", "4", "5"};

}
