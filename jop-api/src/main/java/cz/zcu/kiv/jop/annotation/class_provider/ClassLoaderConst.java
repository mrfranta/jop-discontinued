package cz.zcu.kiv.jop.annotation.class_provider;

/**
 * This interface contains constants for default three types of class loaders which may be for
 * loading of classes. These constants may be used in annotations {@link TargetClassForName} or
 * {@link RandomClassForName} in parameter <code>classLoader</code>.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ClassLoaderConst {

  /**
   * Constant for class loader of caller class (default). That means that for loading of class will
   * be used class loader of caller of method {@link Class#forName(String, boolean, ClassLoader)
   * Class.forName}.
   */
  public String CALLER = "CALLER";

  /**
   * Constant for context class loader of thread which calls method
   * {@link Class#forName(String, boolean, ClassLoader) Class.forName}.
   */
  public String CONTEXT = "CONTEXT";

  /**
   * Constant for system class loader.
   */
  public String SYSTEM = "SYSTEM";

}
