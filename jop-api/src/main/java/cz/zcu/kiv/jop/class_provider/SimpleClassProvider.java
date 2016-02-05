package cz.zcu.kiv.jop.class_provider;

import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;

/**
 * Abstract implementation of {@link ClassProvider} interface which don't need parameters for
 * providing of classes. Also implementations of this class can be used in
 * {@link cz.zcu.kiv.jop.annotation.class_provider.CustomClassProvider CustomClassProvider}
 * annotation.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class SimpleClassProvider implements ClassProvider<EmptyParameters> {

  /**
   * Provides the the class type according to given parameters. This method is final and serves only
   * as bridge to method which is without parameters - {@link #get()}.
   *
   * @param params the parameters for choice of provided class (unused).
   * @return Provided class type.
   * @throws ClassProviderException if some error occurs during providing of class type.
   */
  public final Class<?> get(EmptyParameters params) throws ClassProviderException {
    return get();
  }

  /**
   * Provides the the class type.
   *
   * @return Provided class type.
   * @throws ClassProviderException if some error occurs during providing of class type.
   */
  public abstract Class<?> get() throws ClassProviderException;

}
