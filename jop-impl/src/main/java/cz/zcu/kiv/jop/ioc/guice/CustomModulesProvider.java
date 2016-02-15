package cz.zcu.kiv.jop.ioc.guice;

import java.util.List;

import com.google.inject.Module;

/**
 * This interface allows to attach custom modules into {@link GuiceInjector injector}. Modules
 * shouldn't contains bindings from {@link CoreModule} or {@link ExplicitBindingsModule}. Provided
 * modules can be used as extension for used injector which contains custom or extended bindings
 * because the standard modules for injector uses only simple bindings.
 * <p>
 * The implementation of this interface has to be in same package and the name has to be
 * <code>CustomModulesProviderImpl</code>. Also it has to contains parameterless constructor. The
 * implementation will be loaded dynamically by injector.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface CustomModulesProvider {

  /**
   * Returns list of custom modules which will be used for creation of {@link GuiceInjector
   * injector}.
   *
   * @return List of custom modules.
   */
  public List<Module> getCustomModules();

}
