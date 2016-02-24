package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Annotation;

/**
 * Implementation of annotation {@link RandomClassForName}.
 *
 * @author Mr.FrAnTA
 */
public class RandomClassForNameImpl implements RandomClassForName {

  /** Value of annotation. */
  private final String[] value;
  /** Probabilities of annotation. */
  private final double[] probabilities;
  /** Information whether the class will be initialized. */
  private final boolean initialize;
  /** Symbolic name of class loader. */
  private final String classLoader;

  /**
   * Constructs the annotation.
   */
  public RandomClassForNameImpl() {
    this(null, new double[0], true, ClassLoaderConst.CALLER);
  }

  /**
   * Constructs the annotation.
   *
   * @param value the value of annotation.
   */
  public RandomClassForNameImpl(String... value) {
    this(value, new double[0], true, ClassLoaderConst.CALLER);
  }

  /**
   * Constructs the annotation.
   *
   * @param value the value of annotation.
   * @param probabilities probabilities of annotation.
   * @param initialize information whether the class will be initialized.
   * @param classLoader symbolic name of class loader.
   */
  public RandomClassForNameImpl(String[] value, boolean initialize, String classLoader) {
    this(value, new double[0], initialize, classLoader);
  }

  /**
   * Constructs the annotation.
   *
   * @param value the value of annotation.
   * @param probabilities probabilities of annotation.
   * @param initialize information whether the class will be initialized.
   * @param classLoader symbolic name of class loader.
   */
  public RandomClassForNameImpl(String[] value, double[] probabilities, boolean initialize, String classLoader) {
    this.value = value;
    this.probabilities = probabilities;
    this.initialize = initialize;
    this.classLoader = classLoader;
  }

  /**
   * Returns type of annotation (this class).
   *
   * @return type of annotation.
   */
  @Override
  public Class<? extends Annotation> annotationType() {
    return getClass();
  }

  /**
   * @return Value of annotation.
   */
  @Override
  public String[] value() {
    return value;
  }

  /**
   * @return Probabilities of annotation.
   */
  @Override
  public double[] probabilities() {
    return probabilities;
  }

  /**
   * @return Information whether the class will be initialized.
   */
  @Override
  public boolean initialize() {
    return initialize;
  }

  /**
   * @return Symbolic name of class loader.
   */
  @Override
  public String classLoader() {
    return classLoader;
  }
}
