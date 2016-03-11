package cz.zcu.kiv.jop.ioc.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.name.Names;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import cz.zcu.kiv.jop.class_provider.ClassProviderFactory;
import cz.zcu.kiv.jop.class_provider.ClassProviderFactoryImpl;
import cz.zcu.kiv.jop.class_provider.ClassProviderInvoker;
import cz.zcu.kiv.jop.class_provider.ClassProviderInvokerImpl;
import cz.zcu.kiv.jop.generator.ValueGeneratorFactory;
import cz.zcu.kiv.jop.generator.ValueGeneratorFactoryImpl;
import cz.zcu.kiv.jop.generator.ValueGeneratorInvoker;
import cz.zcu.kiv.jop.generator.ValueGeneratorInvokerImpl;
import cz.zcu.kiv.jop.ioc.NamedScopes;
import cz.zcu.kiv.jop.ioc.callback.Initializable;
import cz.zcu.kiv.jop.matcher.InstanceMatcherFactory;
import cz.zcu.kiv.jop.matcher.InstanceMatcherFactoryImpl;
import cz.zcu.kiv.jop.session.ClassLoaderSession;
import cz.zcu.kiv.jop.session.ClassLoaderSessionImpl;
import cz.zcu.kiv.jop.session.ExtendedRandomGeneratorSessionImpl;
import cz.zcu.kiv.jop.session.RandomGeneratorSession;
import cz.zcu.kiv.jop.session.RandomGeneratorSessionImpl;
import cz.zcu.kiv.jop.util.Preconditions;

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
    // binding of factories
    bind(ClassProviderFactory.class).to(ClassProviderFactoryImpl.class);
    bind(ValueGeneratorFactory.class).to(ValueGeneratorFactoryImpl.class);
    bind(InstanceMatcherFactory.class).to(InstanceMatcherFactoryImpl.class);

    // binding of interlayers
    bind(ClassProviderInvoker.class).to(ClassProviderInvokerImpl.class);
    bind(ValueGeneratorInvoker.class).to(ValueGeneratorInvokerImpl.class);

    // binding of sessions
    bind(ClassLoaderSession.class).to(ClassLoaderSessionImpl.class);
    bind(RandomGeneratorSession.class).to(RandomGeneratorSessionImpl.class);
    bind(RandomGeneratorSession.class).annotatedWith(Names.named(NamedScopes.EXTENDED_IMPL)).to(ExtendedRandomGeneratorSessionImpl.class);

    // bind listener of classes implementing callback interfaces.
    bindListener(new SubclassesMatcher(Initializable.class), new TypeListener() {
      public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        encounter.register(new CallbacksInjectionListener<I>());
      }
    });
  }

  /**
   * Matcher of (sub)classes of some superclass.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   */
  static class SubclassesMatcher extends AbstractMatcher<TypeLiteral<?>> {

    /** The superclass for which will be searched (sub)classes. */
    private final Class<?> superclass;

    /**
     * Constructs matcher for given superclass.
     *
     * @param superclass the superclass for matcher.
     */
    public SubclassesMatcher(Class<?> superclass) {
      this.superclass = Preconditions.checkArgumentNotNull(superclass, "Superclass cannot be null");
    }

    /**
     * Returns information whether the given type is subclass of superclass for which was this
     * matcher created.
     *
     * @param type the type to check.
     * @return <code>true</code> if given type is (sub)class of superclass; <code>false</code>
     *         otherwise.
     */
    public boolean matches(TypeLiteral<?> type) {
      return superclass.isAssignableFrom(type.getRawType());
    }

  }

  /**
   * Injection listener which listens injections events and handles injections callbacks.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   *
   * @param <I> type for injections into instances.
   */
  static class CallbacksInjectionListener<I> implements InjectionListener<I> {
    /**
     * {@inheritDoc}
     */
    public void afterInjection(I injectee) {
      if (injectee instanceof Initializable) {
        ((Initializable)injectee).init();
      }
    }
  }

}
