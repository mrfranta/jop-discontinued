package cz.zcu.kiv.jop.ioc.guice;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Binder;
import com.google.inject.ConfigurationException;
import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.ProvisionException;
import com.google.inject.Stage;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;

import cz.zcu.kiv.jop.ioc.Injector;
import cz.zcu.kiv.jop.ioc.InjectorException;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * The implementation of injector using implementation of IoC from Google Guice library. This class
 * initializes required modules (bindings) for Guice injector and then serves as wrapper of Google
 * Guice methods.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @see <a href="https://github.com/google/guice">Google Guice</a>
 */
@Singleton
public class GuiceInjector implements Injector {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(GuiceInjector.class);

  /** Google Guice injector. */
  protected final com.google.inject.Injector injector;

  /**
   * Constructs injector with default modules (bindings).
   *
   * @throws InjectorException If some error occurs during injector creation.
   */
  public GuiceInjector() throws InjectorException {
    List<Module> modules = new ArrayList<Module>();

    // bind injector to this instance
    modules.add(createInjectorModule());

    // core bindings overriden by explicit bindings
    modules.add(Modules.override(new CoreModule()).with(new ExplicitBindingsModule()));

    // custom modules
    try {
      Class<?> customModulesProviderImpl = Class.forName(CustomModulesProvider.class.getName() + "Impl");
      Object instance = ReflectionUtils.createInstance(customModulesProviderImpl);
      List<Module> customModules = ((CustomModulesProvider)instance).getCustomModules();
      if (customModules != null && !customModules.isEmpty()) {
        modules.addAll(customModules);
      }
    }
    catch (ClassNotFoundException exc) {
      logger.debug("Cannot get custom modules: Class not found: " + exc.getMessage());
    }
    catch (Exception exc) {
      logger.debug("Cannot get custom modules: " + exc.getMessage());
    }

    try {
      injector = Guice.createInjector(Stage.PRODUCTION, modules);
    }
    catch (CreationException exc) {
      throw new InjectorException(exc);
    }
  }

  /**
   * Constructs injector with given modules (bindings).
   * <p>
   * <strong>Notice:</strong> This constructor should be used only for tests because the library
   * should use standard construction of injector from public constructor. Also this constructor
   * adds one additional module for binding of injector interface to this instance.
   *
   * @param modules the modules (bindings) for injector.
   *
   * @throws InjectorException If some error occurs during injector creation.
   */
  GuiceInjector(Module... modules) throws InjectorException {
    // add binding of injector to this instance
    Module[] injectorModules = new Module[modules.length + 1];
    injectorModules[0] = createInjectorModule();
    System.arraycopy(modules, 0, injectorModules, 1, modules.length);

    try {
      injector = Guice.createInjector(injectorModules);
    }
    catch (CreationException exc) {
      throw new InjectorException(exc);
    }
  }

  /**
   * Constructs injector with given list of modules (bindings).
   * <p>
   * <strong>Notice:</strong> This constructor should be used only for tests because the library
   * should use standard construction of injector from public constructor. Also this constructor
   * adds one additional module for binding of injector interface to this instance.
   *
   * @param modules the list of modules (bindings) for injector.
   *
   * @throws InjectorException If some error occurs during injector creation.
   */
  GuiceInjector(List<Module> modules) throws InjectorException {
    // add binding of injector to this instance
    List<Module> injectorModules = new ArrayList<Module>(modules);
    injectorModules.add(0, createInjectorModule());

    try {
      injector = Guice.createInjector(injectorModules);
    }
    catch (CreationException exc) {
      throw new InjectorException(exc);
    }
  }

  /**
   * Creates module which contains binding of injector to this instance.
   *
   * @return Module for binding of injector.
   */
  protected final Module createInjectorModule() {
    return (new Module() {
      public void configure(Binder binder) {
        binder.bind(Injector.class).toInstance(GuiceInjector.this);
      }
    });
  }

  /**
   * {@inheritDoc}
   * <p>
   * This method serves only as wrapper of Guice injector's method
   * {@link com.google.inject.Injector#getInstance(Class) getInstance(Class)}.
   */
  public <T> T getInstance(Class<T> clazz) throws InjectorException {
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
   * {@link com.google.inject.Injector#getInstance(Key) getInstance(Key.get(Class,
   * Names.named(String)))}.
   */
  public <T> T getInstance(Class<T> clazz, String name) throws InjectorException {
    try {
      return injector.getInstance(Key.get(clazz, Names.named(name)));
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
  public void injectMembers(Object instance) throws InjectorException {
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
