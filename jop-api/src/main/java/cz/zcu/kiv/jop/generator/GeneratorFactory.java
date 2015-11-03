package cz.zcu.kiv.jop.generator;

import java.lang.annotation.Annotation;

/**
 * The interface for factory which serve for creation of {@link Generator}s. The
 * generators should be created using this factory. The created generators can
 * be also cached so the returned instances will be "singletons".
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface GeneratorFactory {

  /**
   * Creates and returns generator for given annotation. The generators can be
   * cached so the returned instance can be "singleton". In case that there is
   * no implementation of generator found for given annotation the exception is
   * thrown - the <code>null</code> value should not be returned.
   *
   * @param annotation the generator annotation for which will be created
   *          generator.
   * @return Created generator for given annotation.
   * @throws GeneratorFactoryException
   */
  public <A extends Annotation> Generator<?, A> createGenerator(A annotation) throws GeneratorFactoryException;

}
