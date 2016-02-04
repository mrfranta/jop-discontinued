package cz.zcu.kiv.jop.session;

/**
 * Interface for session which stores mapped class loaders to symbolic unique names. This session
 * primary servers for possibility of using own class loaders for loading of classes by their fully
 * qualified names.
 * <p>
 * Stored class loaders may be used by {@link cz.zcu.kiv.jop.class_provider.ClassProvider
 * ClassProviders} for loading of classes specified by their fully qualified names in annotations
 * {@link cz.zcu.kiv.jop.annotation.class_provider.TargetClassForName TargetClassForName} or
 * {@link cz.zcu.kiv.jop.annotation.class_provider.RandomClassForName RandomClassForName}.
 * <p>
 * Notice: Do not use names of default class loaders specified in interface
 * {@link cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst ClassLoaderConst}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ClassLoaderSession {

  /**
   * Stores given class loader under given name. If some class loader already exist for given name,
   * it will be replaced.
   *
   * @param name the symbolic name of class loader.
   * @param classLoader the class loader which will be stored (may be <code>null</code>).
   */
  public void setClassLoader(String name, ClassLoader classLoader);

  /**
   * Returns stored class loader under given symbolic name. If no class loader was stored, it may
   * return <code>null</code>.
   *
   * @param name the symbolic name of stored class loader.
   * @return Stored class loader for given name or <code>null</code>.
   */
  public ClassLoader getClassLoader(String name);

  /**
   * Clears the session (removes all stored class loaders).
   */
  public void clear();
}
