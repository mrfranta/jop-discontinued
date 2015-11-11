package cz.zcu.kiv.jop.matcher;

import java.lang.annotation.Annotation;

/**
 * The interface for factory which serve for creation of {@link InstanceMatcher
 * InstanceMatchers}. The instance matchers should be created using this
 * factory. The created matchers can be also cached so the returned instances
 * will be "singletons".
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface InstanceMatcherFactory {

  /**
   * Creates and returns instance matcher for given annotation. The matchers can
   * be cached so the returned instance can be "singleton". In case that there
   * was no appropriate implementation of matcher found for given annotation the
   * exception may be thrown - the <code>null</code> value should not be
   * returned.
   *
   * @param annotation the instance matcher annotation for which will be created
   *          appropriate instance matcher.
   * @return Created instance matcher for given annotation.
   * @throws InstanceMatcherFactoryException if some error occurs during
   *           instance matcher creation.
   */
  public <A extends Annotation> TypedInstanceMatcher<?, A> createInstanceMatcher(A annotation) throws InstanceMatcherFactoryException;

}
