package cz.zcu.kiv.jop.binding;

/**
 * This interface serves as builder for bindings between some annotation and
 * some class. It supports chaining of methods (Fluent interface). The binding
 * builder should be generally created by {@link Binder} for some annotation.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent
 *      interface</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of objects which can be bound for some annotation. It's
 *          strongly suggested to choose interface or abstract class as generic
 *          type.
 */
public interface BindingBuilder<T> {

  /**
   * Binds given class type of object to annotation for which was created
   * binding builded by this binder.
   *
   * @param clazz the class type of object which will be bound to annotation.
   * @return Instance of this builder for possibility of chaining.
   */
  public BindingBuilder<T> to(Class<? extends T> clazz);

  /**
   * Binds given instance to annotation for which was created binding builded by
   * this binder.
   *
   * @param instance the instance of object which will be bound to annotation.
   * @return Instance of this builder for possibility of chaining.
   */
  public BindingBuilder<T> to(T instance);

  /**
   * Marks bound object as singleton.
   */
  public void asSingleton();

  /**
   * Marks bound object as eager singleton. It's possible to create (eager)
   * singleton instance of bound object in this method.
   *
   * @throws BindingException It may try to create instance of bound object and
   *           the exception can be thrown if some error occurs during obtaining
   *           of singleton instance of bound object.
   */
  public void asEagerSingleton();

  /**
   * Returns builded binding between some annotation and some class.
   *
   * @return Builded binding.
   */
  public Binding<T> getBinding();

}
