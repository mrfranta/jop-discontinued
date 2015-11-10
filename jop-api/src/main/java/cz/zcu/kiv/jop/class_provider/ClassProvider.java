package cz.zcu.kiv.jop.class_provider;

import java.lang.annotation.Annotation;

/**
 * The common interface for all class providers. It may be used for getting of
 * specific class type that is used for new instance creation in case that
 * declaring type of field (into which will be new instance created) is
 * interface or abstract class.
 * <p>
 * Type parameter is for annotation type of parameters for class provider. The
 * parameters for {@link #get method} which provides the class type are always
 * stored in annotation (for example: by which is annotated the field for which
 * will be provided the class type).
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <P> Annotation type of class provider parameters.
 */
public interface ClassProvider<P extends Annotation> {

  /**
   * Provides the the class type according to given parameters.
   *
   * @param params the parameters for choice of provided class.
   * @return Provided class type.
   * @throws ClassProviderException if some error occurs during providing of
   *           class type or if given parameters are not valid.
   */
  public Class<?> get(P params) throws ClassProviderException;

}
