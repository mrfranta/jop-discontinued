package cz.zcu.kiv.jop.ioc;

/**
 * Singleton implementation of {@link InjectorProvider injector provider} which provides stored
 * instance of injector for actual thread. The injector manager for jUnit tests provides injectors
 * which integrates injector of Google Guice.
 *
 * @author Mr.FrAnTA
 */
public class InjectorManager implements InjectorProvider {

  /** Stored instances of provided injectors. */
  protected final ThreadLocal<Injector> injectors = new ThreadLocal<Injector>();

  /** Singleton instance of this this class. */
  private static InjectorManager instance;

  /** Constructs singleton instance of this class. */
  private InjectorManager() {
    // no prepared injectors
  }

  /**
   * Sets (stores) given injector for actual thread.
   *
   * @param injector the injector to set (store).
   */
  protected synchronized void set(Injector injector) {
    injectors.set(injector);
  }

  /**
   * Returns instance of stored injector for actual thread.
   *
   * @return Stored injector for actual thread.
   * @throws InjectorException If no injector was stored for actual thread.
   */
  public synchronized Injector get() throws InjectorException {
    Injector injector = injectors.get();
    if (injector == null) {
      throw new InjectorException("No injector was found");
    }

    return injector;
  }

  /**
   * Removes stored injector for actual thread.
   */
  protected void remove() {
    injectors.remove();
  }

  /**
   * Returns singleton instance of {@link InjectorProvider injector provider}. This method doesn't
   * return the instance of this class because the implementation shouldn't be required. Also brings
   * support for replacements of this class which may contain different implementation.
   *
   * @return Singleton instance of injector provider.
   */
  public static InjectorProvider getInstance() {
    if (instance == null) {
      instance = new InjectorManager();
    }

    return instance;
  }
}
