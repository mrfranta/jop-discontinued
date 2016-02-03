package cz.zcu.kiv.jop.ioc;

/**
 * Common interface for injector of dependencies (DI). An injector contains
 * graph of objects that make up structure of this library (or your program).
 * <p>
 * An injector {@link #getInstance(Class) provides assembled instances} of
 * classes with all dependencies.
 * <p>
 * An injector can also {@link #injectMembers(Object) inject the dependencies}
 * of already-constructed instances. This can be used to interoperate with
 * objects created by other frameworks or services.
 * <p>
 * An injector may inject itself into another objects.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @see <a href="https://en.wikipedia.org/wiki/Dependency_injection">Dependency
 *      injection</a>
 */
public interface Injector {

  /**
   * Returns assembled instance of given class with injected dependencies.
   *
   * @param clazz the class which instance will be provided.
   * @return Assembled instance of given class.
   * @throws InjectorException if some error occurs while Injector providing an
   *           instance or during injection of dependencies into some instance.
   */
  public <T> T getInstance(Class<T> clazz);

  /**
   * Injects dependencies into the fields and methods of given instance. Ignores
   * the presence or absence of an injectable constructor.
   * <p>
   * Whenever an Injector creates an instance, it performs this method
   * automatically (after first performing constructor injection), so if it's
   * possible let Injector create all objects.
   *
   * @param instance the instance for members injection.
   * @throws InjectorException if some error occurs during injection of
   *           dependencies into some instance.
   */
  public void injectMembers(Object instance);

}
