package cz.zcu.kiv.jop.ioc;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton implementation of {@link InjectorProvider injector provider} which provides singleton
 * instance of injector which may be used for dependency injection. In this implementation also may
 * prepare the injector according to available implementation of dependency injection. Generally
 * this library may cooperate with several implementations of dependency injection. Currently is
 * implemented only integration of injector using Google Guice.
 *
 * @author Mr.FrAnTA
 */
public class InjectorManager implements InjectorProvider {

  /** Stored instances of provided injectors. */
  protected final Map<Thread, Injector> injectors = new HashMap<Thread, Injector>();

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
    injectors.put(Thread.currentThread(), injector);
  }

  /**
   * Returns instance of stored injector for actual thread.
   *
   * @return Stored injector for actual thread.
   * @throws InjectorException If no injector was stored for actual thread.
   */
  @Override
  public synchronized Injector get() throws InjectorException {
    Injector injector = injectors.get(Thread.currentThread());
    if (injector == null) {
      throw new InjectorException("No injector was found");
    }

    return injector;
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
