package cz.zcu.kiv.jop.ioc.callback;

/**
 * The callback interface for post-construct initialization of dependencies implementing this
 * interface. The method {@link #init()} method should be invoked by injector after construction of
 * object implementing this interface.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface Initializable {

  /**
   * Post-construct initialization of object.
   *
   * @throws InitException if some error occurs during initialization.
   */
  public void init() throws InitException;

}
