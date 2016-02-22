package cz.zcu.kiv.jop.ioc;

import javax.inject.Provider;

/**
 * This provider interface can be used for obtaining instance of actual injector. Each call of
 * method {@link #get()} should return same instance of injector.
 * <p>
 * Implementation of this interface may or may not be injectable (generally is supposed that this
 * interface is not injectable). Generally it serves only for obtaining injector in instances which
 * wasn't created by injector (instances which are not in injectable context). In injectable context
 * is suggested to use injection of {@link Injector} instance.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface InjectorProvider extends Provider<Injector> {

}
