package cz.zcu.kiv.jop.factory.binding;

import java.lang.reflect.Constructor;

/**
 * This interface serves as builder for bindings between some annotation and some class. It supports
 * chaining of methods (Fluent interface). The binding builder should be generally created by
 * {@link Binder} for some annotation.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Type of objects which can be bound for some annotation. It's strongly suggested to
 *          choose interface or abstract class as generic type.
 */
public interface BindingBuilder<T> extends ScopedBindingBuilder {

  /**
   * Binds given class type of object to annotation for which was created binding which is builded
   * by this binder.
   *
   * @param clazz the class type of object which will be bound to annotation.
   * @return Instance of scoped binding builder which may set the scope of binding.
   */
  public ScopedBindingBuilder to(Class<? extends T> clazz);

  /**
   * Binds given instance to annotation for which was created binding which is builded by this
   * binder.
   *
   * @param instance the instance of object which will be bound to annotation.
   */
  public void toInstance(T instance);

  /**
   * Binds given constructor to annotation for which was created binding which is builded by this
   * binder.
   *
   * @param constructor the constructor of object which will be bound to annotation.
   * @return Instance of scoped binding builder which may set the scope of binding.
   */
  <S extends T> ScopedBindingBuilder toConstructor(Constructor<S> constructor);

}
