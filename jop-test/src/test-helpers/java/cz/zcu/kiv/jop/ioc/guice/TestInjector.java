package cz.zcu.kiv.jop.ioc.guice;

import java.util.List;

import com.google.inject.Module;

import cz.zcu.kiv.jop.ioc.InjectorException;

/**
 * Extension of Google Guice injector which contains public constructor which allows construct it
 * for given list of modules.
 *
 * @author Mr.FrAnTA
 */
public class TestInjector extends GuiceInjector {

  /**
   * Constructs injector for given list of modules (bindings). Also this constructor adds one
   * additional module for binding of injector interface to this instance.
   *
   * @param modules list of modules (bindings) for injector.
   *
   * @throws InjectorException If some error occurs during injector creation.
   */
  public TestInjector(List<Module> modules) throws InjectorException {
    super(modules);
  }

}
