package cz.zcu.kiv.jop;

import cz.zcu.kiv.jop.ioc.Injector;
import cz.zcu.kiv.jop.ioc.InjectorException;
import cz.zcu.kiv.jop.ioc.InjectorManager;

/**
 * Static class which serves as provider of implementation of {@link ObjectPopulator}. This provider
 * uses {@link Injector} for creation and provision of Object populator instances.
 * <p>
 * Do not create instances of object populator directly because it's depend on another parts of this
 * library (which are depend on another parts and etc.) and usage of injector ensure that all
 * dependencies (with their dependencies) will be injected into instance of object populator. Also
 * the Injector should contains information about implementations of dependencies which usually has
 * form of interface.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public final class ObjectPopulatorProvider {

  /**
   * Private constructor in combination with abstract modifier of this class makes it static.
   */
  private ObjectPopulatorProvider() {}

  /**
   * Provides instance of {@link ObjectPopulator} implementation. This method prepares
   * {@link Injector} which uses for creation and provision of instance of correct object populator
   * implementation.
   *
   * @return Instance of {@link ObjectPopulator} implementation.
   * @throws ObjectPopulatorException If instance of {@link ObjectPopulator} cannot be provided.
   */
  public static ObjectPopulator getObjectPopulator() throws ObjectPopulatorException {
    try {
      Injector injector = InjectorManager.getInstance().get();

      return injector.getInstance(ObjectPopulator.class);
    }
    catch (InjectorException exc) {
      throw new ObjectPopulatorException("Cannot get instance of object populator", exc);
    }
  }
}
