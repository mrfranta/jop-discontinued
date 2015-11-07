package cz.zcu.kiv.jop.class_provider;

import java.lang.annotation.Annotation;

/**
 * This interface is extension of {@link ClassProvider} interface which has
 * additional generic parameter for type for which will be provided the class
 * types. The provided class types can be also for types which extends the
 * defined type - for example when the <code>T</code> will be
 * <code>Animal</code> (class) then the provider can provide class type
 * <code>Class&lt;Cat&gt;</code> or <code>Class&lt;Dog&gt;</code> in case that
 * classes <code>Cat</code> and <code>Dog</code> extends the <code>Animal</code>
 * class.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type for which will be provided the class types. The provided
 *          class types can be also for types which extends the defined type.
 * @param <P> Annotation type of class provider parameters.
 */
public interface TypedClassProvider<T, P extends Annotation> extends ClassProvider<P> {

  /**
   * {@inheritDoc}
   */
  public Class<? extends T> get(P params) throws ClassProviderException;

}
