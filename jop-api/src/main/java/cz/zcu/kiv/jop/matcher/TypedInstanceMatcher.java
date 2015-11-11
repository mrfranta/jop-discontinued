package cz.zcu.kiv.jop.matcher;

import java.lang.annotation.Annotation;

/**
 * The common interface for all matchers of objects (instances). This interface
 * has type parameter for type of accepted type of checked objects.
 * <p>
 * It may be used for searching of generated instance which matches some
 * conditions. Found object (instance) can be set into populated field.
 * <p>
 * Second type parameter is for annotation type of instance matcher parameters.
 * The parameters for {@link #matches} method are always stored in annotation
 * (for example: by which is annotated the field for which will be searched
 * wanted instance).
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of checked objects (instances).
 * @param <P> Annotation type of instance matcher parameters.
 */
public interface TypedInstanceMatcher<T, P extends Annotation> {

  /**
   * Checks whatever the given object matches custom conditions.
   *
   * @param obj the object (instance) to check.
   * @param params the parameters for matching of given object (for example
   *          parameters for conditions).
   * @return <code>true</code> in case that given object matches to conditions;
   *         <code>false</code> otherwise.
   * @throws InstanceMatcherException if some error occurs during checking of
   *           given object or if given parameters are not valid.
   */
  public boolean matches(T obj, P params) throws InstanceMatcherException;

}
