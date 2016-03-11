package cz.zcu.kiv.jop.matcher;

import java.lang.annotation.Annotation;

/**
 * The common interface for all matchers of objects (instances). The instance matcher accepts only
 * classes for which method {@link #supports(Class)} returns <code>true</code>. The instance matcher
 * takes annotation as parameters of method {@link #matches} for matching of instances (for example:
 * annotation by which is annotated the field for which will be searched wanted instance).
 * <p>
 * It may be used for searching of generated instance which matches some conditions. Found object
 * (instance) can be set into populated field.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <P> Annotation type of instance matcher parameters.
 */
public interface InstanceMatcher<P extends Annotation> {

  /**
   * Returns information whether instances matcher can {@link #matches} instances of the given
   * {@code clazz}.
   * <p>
   * This method is <i>typically</i> implemented like so:
   *
   * <pre class="code">
   * return Foo.class.isAssignableFrom(clazz);
   * </pre>
   *
   * Where {@code Foo} is the class (or superclass) of the actual object instance that is to be
   * checked.
   *
   * @param clazz the the class type to check.
   * @return <code>true</code> if instances matcher can {@link #matches} instances of given class
   *         type; <code>false</code> otherwise.
   */
  boolean supports(Class<?> clazz);

  /**
   * Checks whatever the given object matches custom conditions.
   *
   * @param obj the object (instance) to check.
   * @param params the parameters for matching of given object (for example parameters for
   *          conditions).
   * @return <code>true</code> in case that given object matches to conditions; <code>false</code>
   *         otherwise.
   * @throws InstanceMatcherException if some error occurs during checking of given object or if
   *           given parameters are not valid.
   */
  public boolean matches(Object obj, P params) throws InstanceMatcherException;

}
