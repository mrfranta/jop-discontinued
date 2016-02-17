package cz.zcu.kiv.jop.annotation;

import java.lang.annotation.Annotation;

/**
 * Helper "implementation" of annotation {@link Foo} which may be used for manual creation of
 * annotation instance.
 */
public class FooImpl implements Foo {

  /** Annotation value. */
  private final int value;

  /**
   * Constructs annotation.
   *
   * @param value value of annotation.
   */
  public FooImpl(int value) {
    this.value = value;
  }

  /**
   * Returns type of annotation (this class).
   *
   * @return type of annotation.
   */
  @Override
  public Class<? extends Annotation> annotationType() {
    return Foo.class;
  }

  /**
   * @return Value of annotation.
   */
  @Override
  public int value() {
    return value;
  }
}
