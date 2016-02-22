package cz.zcu.kiv.jop.ioc;

import cz.zcu.kiv.jop.ioc.guice.GuiceInjector;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Singleton implementation of {@link InjectorProvider injector provider} which provides singleton
 * instance of injector which may be used for dependency injection. In this implementation also may
 * prepare the injector according to available implementation of dependency injection. Generally
 * this library may cooperate with several implementations of dependency injection. Currently is
 * implemented only integration of injector using Google Guice.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @see <a href="https://github.com/google/guice">Google Guice</a>
 */
public class InjectorManager implements InjectorProvider {

  /** Instance of provided injector. */
  protected final Injector injector;

  /** Singleton instance of this this class. */
  private static InjectorManager instance;

  /**
   * Constructs singleton instance of this class.
   *
   * @throws InjectorException If some error occurs during construction of this class (generally if
   *           some error occurs during injector preparation).
   */
  private InjectorManager() throws InjectorException {
    this.injector = prepareInjector();
  }

  /**
   * Prepares injector according to available implementation of dependency injection. Generally this
   * library may cooperate with several types of dependency injection. Currently is implemented only
   * integration of injector using Google Guice.
   *
   * @return Prepared injector.
   * @throws InjectorException If some error occurs during injector preparation.
   */
  final Injector prepareInjector() throws InjectorException {
    // check Google Guice existence on classpath
    // for now, there is no another available injector
    if (!ReflectionUtils.isClassExist("com.google.inject.Guice")) {
      throw new InjectorException("No injector found");
    }

    // NOTICE: in future, there may be added passing this instance as parameter
    // of injector to be possible inject the injector provider.
    return new GuiceInjector();
  }

  /**
   * Returns instance of provided injector. Each call of this method should returns the same
   * injector.
   *
   * @return Provided injector.
   */
  public Injector get() {
    return injector;
  }

  /**
   * Returns singleton instance of {@link InjectorProvider injector provider}. This method doesn't
   * return the instance of this class because the implementation shouldn't be required. Also brings
   * support for replacements of this class which may contain different implementation.
   *
   * @return Singleton instance of injector provider.
   * @throws InjectorException If some error occurs during preparation of injector provider.
   *           Generally in case of first call of this method may be thrown because of problems with
   *           provided injector construction.
   */
  public static InjectorProvider getInstance() throws InjectorException {
    if (instance == null) {
      instance = new InjectorManager();
    }

    return instance;
  }
}
