package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Annotation;

/**
 * Implementation of annotation {@link TargetClass}.
 *
 * @author Mr.FrAnTA
 */
public class TargetClassImpl implements TargetClass {

  /** Value of annotation. */
  private final Class<?> value;

  /**
   * Constructs the annotation.
   *
   * @param value the value of annotation.
   */
  public TargetClassImpl(Class<?> value) {
    this.value = value;
  }

  /**
   * Returns type of annotation (this class).
   *
   * @return type of annotation.
   */
  public Class<? extends Annotation> annotationType() {
    return getClass();
  }

  /**
   * @return Value of annotation.
   */
  public Class<?> value() {
    return value;
  }
}
