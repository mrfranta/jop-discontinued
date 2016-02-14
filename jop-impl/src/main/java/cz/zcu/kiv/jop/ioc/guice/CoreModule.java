package cz.zcu.kiv.jop.ioc.guice;

import com.google.inject.AbstractModule;

import cz.zcu.kiv.jop.session.ClassLoaderSession;
import cz.zcu.kiv.jop.session.ClassLoaderSessionImpl;
import cz.zcu.kiv.jop.session.RandomGeneratorSession;
import cz.zcu.kiv.jop.session.RandomGeneratorSessionImpl;

/**
 * Core module of this library which contains binding of API interfaces to their (default)
 * implementations. Do not overlap this class in classpath. Use the functionality of
 * {@link ExplicitBindingsModule} for customization of bindings which replaces bindings from this
 * module. Also the module for explicit bindings may be used for additional custom bindings.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public final class CoreModule extends AbstractModule {

  /**
   * Configures module - binds all core interfaces to their (default) implementations.
   */
  @Override
  public void configure() {
    // binding of sessions
    bind(ClassLoaderSession.class).to(ClassLoaderSessionImpl.class);
    bind(RandomGeneratorSession.class).to(RandomGeneratorSessionImpl.class);
  }

}
