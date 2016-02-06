package cz.zcu.kiv.jop.annotation.defaults;

/**
 * Helper class which serves only as default value for annotations which has optional parameter
 * declared as <code>Class<? extends Number></code>. The class is abstract and has private
 * constructor - it's "static" - so cannot be extended or it cannot be created new instance of it.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class DefaultNumber extends Number {

  /**
   * Determines if a de-serialized file is compatible with this class.
   * <p>
   * Maintainers must change this value if and only if the new version of this class is not
   * compatible with old versions. See Oracle docs for <a
   * href="http://docs.oracle.com/javase/1.5.0/docs/guide/ serialization/">details</a>.
   * <p>
   * Not necessary to include in first version of the class, but included here as a reminder of its
   * importance.
   */
  private static final long serialVersionUID = 20160206L;

  /**
   * Private constructor in combination with abstract modifier of this class makes it static.
   */
  private DefaultNumber() {}

  /**
   * Returns default class type for implementation of number class.
   *
   * @return Default class type for numbers.
   */
  public static Class<? extends Number> getDefaultClass() {
    return Double.class;
  }

}
