package cz.zcu.kiv.jop.factory.binding;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * This interface serves as container of created bindings between some annotation and some class. It
 * contains common methods for creation of bindings via {@link BindingBuilder BindingBuilders} or
 * for their obtaining.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Type of objects which can be bound for some annotation. It's strongly suggested to
 *          choose interface or abstract class as generic type.
 */
public interface Binder<T> {

  /**
   * Creates builder for binding of given annotation type. In case that given annotation type was
   * already bound the exception can be thrown.
   *
   * @param annotation the class type of annotation of which will be created binding (builder).
   * @return Binding builder for binding creation.
   * @throws BindingException If given annotation is not valid or if some binding for given
   *           annotation already exists.
   */
  public BindingBuilder<T> bind(Class<? extends Annotation> annotation) throws BindingException;

  /**
   * Creates builder for binding of given annotation type. In case that given annotation type was
   * already bound no exception is thrown but already created binding is replaced with this one.
   *
   * @param annotation the class type of annotation of which will be created binding (builder).
   * @return Binding builder for binding creation.
   * @throws BindingException If given annotation is not valid.
   */
  public BindingBuilder<T> rebind(Class<? extends Annotation> annotation) throws BindingException;

  /**
   * Returns created binding (even if it wasn't finished by their builder) for given
   * <code>annotation</code>.
   *
   * @param annotation the class type of annotation for which will be returned binding.
   * @return Found binding for given annotation type;<code>null</code> in case that exception won't
   *         be thrown (not recommended).
   * @throws BindingException If given annotation is not valid. It also may throws binding exception
   *           in case that binding for given annotation type was not found.
   */
  public Binding<T> getBinding(Class<? extends Annotation> annotation) throws BindingException;

  /**
   * Returns all created bindings (even if they wasn't finished by their builder) by this binder.
   *
   * @return All created bindings.
   */
  public List<Binding<T>> getBindings();

}
