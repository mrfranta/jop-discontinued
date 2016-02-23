package cz.zcu.kiv.jop.ioc.callback;

/**
 * The callback interface for post-construct initialization of dependencies implementing this
 * interface. The method {@link #init()} method should be invoked after construction of some
 * dependency.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface Initializable {

  /**
   * Post-construct initialization of dependency.
   *
   * @throws InitException if some error occurs during initialization.
   */
  public void init() throws InitException;

}
