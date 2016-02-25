package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Annotation;

/**
 * Implementation of annotation {@link TargetClassForName}.
 *
 * @author Mr.FrAnTA
 */
public class TargetClassForNameImpl implements TargetClassForName {

  /** Value of annotation. */
  private final String value;
  /** Information whether the class will be initialized. */
  private final boolean initialize;
  /** Symbolic name of class loader. */
  private final String classLoader;

  /**
   * Constructs the annotation.
   *
   * @param value the value of annotation.
   */
  public TargetClassForNameImpl(String value) {
    this(value, true, ClassLoaderConst.CALLER);
  }

  /**
   * Constructs the annotation.
   *
   * @param value the value of annotation.
   * @param initialize information whether the class will be initialized.
   * @param classLoader symbolic name of class loader.
   */
  public TargetClassForNameImpl(String value, boolean initialize, String classLoader) {
    this.value = value;
    this.initialize = initialize;
    this.classLoader = classLoader;
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
  public String value() {
    return value;
  }

  /**
   * @return Information whether the class will be initialized.
   */
  public boolean initialize() {
    return initialize;
  }

  /**
   * @return Symbolic name of class loader.
   */
  public String classLoader() {
    return classLoader;
  }
}
