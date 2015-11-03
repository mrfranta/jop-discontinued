package cz.zcu.kiv.jop.class_provider;

import java.lang.annotation.Annotation;

/**
 * The interface for factory which serve for creation of {@link ClassProvider
 * ClassProviders}. The class providers should be created using this factory.
 * The created class providers can be also cached so the returned instances will
 * be "singletons".
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface ClassProviderFactory {

  /**
   * Creates and returns appropriate class provider for given annotation. The
   * class providers can be cached so the returned instance can be "singleton".
   * In case that there was no appropriate implementation of class provider
   * found for given annotation the exception may be thrown - the
   * <code>null</code> value should not be returned.
   *
   * @param annotation the class provider annotation for which will be created
   *          appropriate class provider.
   * @return Created class provider for given annotation.
   * @throws ClassProviderFactoryException if some error occurs during class
   *           provider creation.
   */
  public <A extends Annotation> ClassProvider<A> createClassProvider(A annotation) throws ClassProviderFactoryException;

}
