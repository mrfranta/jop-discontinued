package cz.zcu.kiv.jop.matcher;

import cz.zcu.kiv.jop.annotation.Empty;

/**
 * Abstract implementation of {@link TypedInstanceMatcher} interface which don't
 * need parameters for matching of objects (instances). Also implementations of
 * this class can be used in
 * {@link cz.zcu.kiv.jop.annotation.matcher.CustomInstanceMatcher
 * CustomInstanceMatcher} annotation.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of checked objects (instances).
 */
public abstract class SimpleTypedInstanceMatcher<T> implements TypedInstanceMatcher<T, Empty> {

  /**
   * Checks whatever the given object matches custom conditions. The method is
   * final and serves only as bridge to method which is without parameters -
   * {@link #matches(Object)}.
   *
   * @param obj the object (instance) to check.
   * @param params the parameters for matching of given object (unused).
   * @return <code>true</code> in case that given object matches to conditions;
   *         <code>false</code> otherwise.
   * @throws InstanceMatcherException if some error occurs during checking of
   *           given object or if given parameters are not valid.
   */
  public final boolean matches(T obj, Empty params) {
    return matches(obj);
  }

  /**
   * Checks whatever the given object matches custom conditions.
   *
   * @param obj the object (instance) to check.
   * @return <code>true</code> in case that given object matches to conditions;
   *         <code>false</code> otherwise.
   * @throws InstanceMatcherException if some error occurs during checking of
   *           given object.
   */
  public abstract boolean matches(T obj);

}
