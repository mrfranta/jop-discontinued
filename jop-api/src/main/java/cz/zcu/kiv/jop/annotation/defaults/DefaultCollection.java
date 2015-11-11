package cz.zcu.kiv.jop.annotation.defaults;

import java.util.Collection;

/**
 * Helper class which serves only as default value for annotations which has
 * optional parameter declared as <code>Class<? extends Collection></code>. The
 * class is abstract and has private constructor - it's "static" - so cannot be
 * extended or it cannot be created new instance of it.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class DefaultCollection implements Collection {

  /**
   * Private constructor in combination with abstract modifier of this class
   * makes it static.
   */
  private DefaultCollection() {}

}
