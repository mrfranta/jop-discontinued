package cz.zcu.kiv.jop.ioc.guice;

import javax.inject.Singleton;

import com.google.inject.Binder;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.ProvisionException;
import com.google.inject.util.Modules;

import cz.zcu.kiv.jop.ioc.Injector;
import cz.zcu.kiv.jop.ioc.InjectorException;

/**
 * The implementation of injector using implementation of IoC from Google Guice library. This class
 * initializes required modules (bindings) for Guice injector and then serves as wrapper of Google
 * Guice methods.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class GuiceInjector implements Injector {

  /** Google Guice injector. */
  protected final com.google.inject.Injector injector;

  /**
   * Constructs injector with default modules (bindings).
   */
  public GuiceInjector() {
    // bind injector to this instance
    Module iocModule = new Module() {
      public void configure(Binder binder) {
        binder.bind(Injector.class).toInstance(GuiceInjector.this);
      }
    };

    // core bindings overriden by explicit bindings
    Module jopModule = Modules.override(new CoreModule()).with(new ExplicitBindingsModule());

    injector = Guice.createInjector(iocModule, jopModule);
  }

  /**
   * Constructs injector with given modules (bindings).
   * <p>
   * <strong>Notice:</strong> This constructor should be used only for tests because the library
   * should use standard construction of injector from public constructor.
   *
   * @param modules the modules (bindings) for injector.
   */
  GuiceInjector(Module... modules) {
    injector = Guice.createInjector(modules);
  }

  /**
   * {@inheritDoc}
   * <p>
   * This method serves only as wrapper of Guice injector's method
   * {@link com.google.inject.Injector#getInstance(Class) getInstance(Class)}.
   */
  public <T> T getInstance(Class<T> clazz) {
    try {
      return injector.getInstance(clazz);
    }
    catch (ConfigurationException exc) {
      throw new InjectorException(exc);
    }
    catch (ProvisionException exc) {
      throw new InjectorException(exc);
    }
  }

  /**
   * {@inheritDoc}
   * <p>
   * This method serves only as wrapper of Guice injector's method
   * {@link com.google.inject.Injector#injectMembers(Object) injectMembers(Object)}.
   */
  public void injectMembers(Object instance) {
    try {
      injector.injectMembers(instance);
    }
    catch (ConfigurationException exc) {
      throw new InjectorException(exc);
    }
    catch (ProvisionException exc) {
      throw new InjectorException(exc);
    }
  }

}
