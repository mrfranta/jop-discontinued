package cz.zcu.kiv.jop.annotation.defaults;

/**
 * Helper class which serves only as default value for annotations which has optional parameter
 * declared as <code>Class<? extends CharSequence></code>. The class is abstract and has private
 * constructor - it's "static" - so cannot be extended or it cannot be created new instance of it.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class DefaultCharSequence implements CharSequence {

  /**
   * Private constructor in combination with abstract modifier of this class makes it static.
   */
  private DefaultCharSequence() {}

  /**
   * Returns default class type for implementation of char sequence interface.
   *
   * @return Default class type for char sequences.
   */
  public static Class<? extends CharSequence> getDefaultClass() {
    return String.class;
  }

}
