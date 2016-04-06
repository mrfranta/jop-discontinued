package cz.zcu.kiv.jop.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * THelper static class for providing of {@link ClassLoader}s for dynamic class/resource loading at
 * any point in an application.
 * <p>
 * This solution was overtaken from <a href=
 * "http://www.javaworld.com/article/2077344/core-java/find-a-way-out-of-the-classloader-maze.html">
 * Find a way out of the ClassLoader maze</a> written by Vladimir Roubtsov.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class ClassLoaderUtils {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(ClassLoaderUtils.class);

  /** Provider of class loaders for classes. */
  private static ClassLoaderProvider provider;

  /** Constant for offset of resolver call for getting caller class. */
  private static final int CALL_RESOLVER_OFFSET = 3;
  /** Resolver of caller class (do not change it). */
  private static CallerClassResolver CALLER_RESOLVER;

  /**
   * Static constructor which prepares utilities.
   */
  static {
    try {
      // this can fail if the current SecurityManager does not allow RuntimePermission ("createSecurityManager"):
      CALLER_RESOLVER = new CallerClassResolver();
    }
    catch (SecurityException exc) {
      logger.error("Cannot create '" + CallerClassResolver.class.getName() + "'", exc);
    }

    provider = new DefaultClassLoaderProvider();
  }

  /**
   * Returns class loader of class type which called this method.
   *
   * @return Caller's class loader.
   */
  public static ClassLoader getCallerClassLoader() {
    Class<?> caller = getCallerClass(0);

    return caller.getClassLoader();
  }

  /**
   * This method selects the "best" class loader instance to be used for class/resource loading by
   * whoever calls this method. The decision typically involves choosing between the caller's
   * current, thread context, system, and other classloaders in the JVM and is made by the
   * {@link ClassLoaderProvider} instance established by the last call to {@link #setStrategy}.
   *
   * @return Class loader to be used by the caller ['null' indicates the primordial loader].
   */
  public static synchronized ClassLoader getClassLoader() {
    Class<?> caller = getCallerClass(0);

    return provider.getClassLoader(caller);
  }

  /**
   * Variant of method {@link #getClassLoader()} with adjustable caller offset (useful for embedding
   * this method in other classes in this package).
   *
   * @param callerOffset extra call context depth offset to pass into method
   *          {@link #getCallerClass(int)}.
   * @return Class loader to be used by the caller ['null' indicates the primordial loader].
   */
  static synchronized ClassLoader getClassLoader(int callerOffset) {
    Class<?> caller = getCallerClass(callerOffset);

    return provider.getClassLoader(caller);
  }

  /**
   * Returns class type which called this method.
   *
   * @param callerOffset the extra call context depth offset to pass into this method.
   * @return Class type which called this method.
   */
  static Class<?> getCallerClass(int callerOffset) {
    if (CALLER_RESOLVER == null) {
      return null;
    }

    return CALLER_RESOLVER.getClassContext()[CALL_RESOLVER_OFFSET + callerOffset];
  }

  /**
   * Returns the current provider of class loaders for classes.
   *
   * @return Current provider of class loaders.
   */
  public static synchronized ClassLoaderProvider getProvider() {
    return provider;
  }

  /**
   * Sets the provider of class loaders for classes which may be used by subsequent calls of method
   * {@link #getClassLoader()}. An instance of {@link DefaultClassLoaderProvider} is in effect if
   * this method is never called.
   *
   * @param classLoaderProvider the provider of class loaders for classes.
   * @return The previously set provider.
   * @throws IllegalArgumentException If given provider is <code>null</code>.
   */
  public static synchronized ClassLoaderProvider setStrategy(ClassLoaderProvider classLoaderProvider) {
    if (classLoaderProvider == null) {
      throw new IllegalArgumentException("null input: strategy");
    }

    ClassLoaderProvider prev = classLoaderProvider;
    provider = classLoaderProvider;

    return prev;
  }

  /**
   * Provider of class loaders for class types.
   *
   * @author Mr.FrAnTA
   */
  public static interface ClassLoaderProvider {

    /**
     * Selects a class loader for given class.
     *
     * @param clazz the class type for which will be returned class loader.
     */
    public ClassLoader getClassLoader(Class<?> clazz);

  }

  /**
   * A default implementation of {@link ClassLoaderProvider} that should be suitable for a variety
   * of situations.
   *
   * @author Mr.FrAnTA
   */
  public static class DefaultClassLoaderProvider implements ClassLoaderProvider {

    /**
     * {@inheritDoc}
     */
    public ClassLoader getClassLoader(Class<?> clazz) {
      ClassLoader callerLoader = clazz == null ? null : clazz.getClassLoader();
      ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();

      // if 'callerLoader' and 'contextLoader' are in a parent-child relationship, always choose the child:

      ClassLoader result;
      if (isChild(contextLoader, callerLoader)) {
        result = callerLoader;
      }
      else if (isChild(callerLoader, contextLoader)) {
        result = contextLoader;
      }
      else {
        // this else branch could be merged into the previous one, but I show it here to emphasize the ambiguous case:
        result = contextLoader;
      }

      ClassLoader systemLoader = ClassLoader.getSystemClassLoader();

      // precaution for when deployed as a bootstrap or extension class:
      if (isChild(result, systemLoader)) {
        result = systemLoader;
      }

      return result;
    }

    /**
     * Returns <code>true</code> if given <code>loader2</code> is a delegation child of
     * <code>loader1</code> (or if <code>loader1 == loader2</code>). This method works only for
     * class loaders that set their parent pointers correctly. <code>null</code> value is
     * interpreted as the <em>primordial</em> class loader (i.e., everybody's parent).
     *
     * @param loader1 the first class loader to check.
     * @param loader2 the second class loader to check.
     * @return <code>true</code> if given <code>loader2</code> is a delegation child of
     *         <code>loader1</code> (or if <code>loader1 == loader2</code>); <code>false</code>
     *         otherwise.
     */
    private static boolean isChild(ClassLoader loader1, ClassLoader loader2) {
      if (loader1 == loader2) {
        return true;
      }

      if (loader2 == null) {
        return false;
      }
      if (loader1 == null) {
        return true;
      }

      for (; loader2 != null; loader2 = loader2.getParent()) {
        if (loader2 == loader1) {
          return true;
        }
      }

      return false;
    }

  }

  /**
   * A helper class to get the call context. It extends {@link SecurityManager} to make method
   * {@link #getClassContext()} accessible. An instance of this class only needs to be created and
   * not installed as an actual security manager.
   *
   * @author Mr.FrAnTA
   */
  private static final class CallerClassResolver extends SecurityManager {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?>[] getClassContext() {
      return super.getClassContext();
    }

  }

}
