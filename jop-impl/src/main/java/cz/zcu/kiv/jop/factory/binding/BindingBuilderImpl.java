package cz.zcu.kiv.jop.factory.binding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * Basic implementation of {@link BindingBuilder} interface. This builder creates the binding which
 * gradually fills via methods with package-only visibility.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of objects which can be bound for some annotation. It's strongly suggested to
 *          choose interface or abstract class as generic type.
 */
public class BindingBuilderImpl<T> implements BindingBuilder<T> {

  // the implementation is here on purpose - we need to use methods of binding
  // which are only in implementation and which are visible only for this package
  /** Binding which is builded by this builder. */
  protected final BindingImpl<T> binding;

  /**
   * Constructs new builder of binding for given annotation.
   *
   * @param annotation the class type of annotation which can be bound to some object.
   */
  public BindingBuilderImpl(Class<? extends Annotation> annotation) {
    binding = new BindingImpl<T>(annotation);
  }

  /**
   * {@inheritDoc}
   */
  public ScopedBindingBuilder to(Class<? extends T> clazz) {
    binding.setType(clazz);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  public void toInstance(T instance) {
    binding.setInstance(instance);
  }

  /**
   * {@inheritDoc}
   */
  public <S extends T> ScopedBindingBuilder toConstructor(Constructor<S> constructor) {
    binding.setConstructor(constructor);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  public void asSingleton() {
    binding.setSingleton(true);
  }

  /**
   * {@inheritDoc}
   */
  public void asEagerSingleton() throws BindingException {
    binding.setSingleton(true);
    binding.getInstance();
  }

  /**
   * Returns builded binding for some annotation.
   *
   * @return Builded binding.
   */
  protected Binding<T> getBinding() {
    return binding;
  }
}
