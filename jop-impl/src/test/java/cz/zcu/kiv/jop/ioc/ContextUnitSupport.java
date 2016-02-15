package cz.zcu.kiv.jop.ioc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Module;

import cz.zcu.kiv.jop.ioc.guice.TestInjector;

/**
 * This class serves as support for jUnit tests. It allows to set modules for which will be created
 * injector. This support class can be used in jUnit tests for injecting of mocked dependencies.
 *
 * @author Mr.FrAnTA
 */
public class ContextUnitSupport {

  /** List of modules with bindings for injector. */
  protected final List<Module> modules = new ArrayList<Module>();

  /**
   * Contructs support class for jUnit tests for given modules.
   *
   * @param modules the list of modules (bindings).
   */
  public ContextUnitSupport(Module... modules) {
    this.modules.addAll(Arrays.asList(modules));
  }

  /**
   * Adds given module - the module is added as first.
   *
   * @param module the module to add.
   */
  public void addModule(Module module) {
    modules.add(0, module);
  }

  /**
   * Removes given module.
   *
   * @param module the module to remove.
   */
  public void removeModule(Module module) {
    modules.remove(module);
  }

  /**
   * Creates injector which may be used in jUnit tests for injecting of mocked dependencies.
   *
   * @return Created injector.
   */
  public Injector createInjector() {
    return new TestInjector(modules);
  }

}
