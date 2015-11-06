package cz.zcu.kiv.jop.binding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

import cz.zcu.kiv.jop.util.Preconditions;

/**
 * Basic implementation of {@link BindingBuilder} interface. This builder
 * creates the binding which gradually fills via methods with package-only
 * visibility.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of objects which can be bound for some annotation. It's
 *          strongly suggested to choose interface or abstract class as generic
 *          type.
 */
public class BindingBuilderImpl<T> implements BindingBuilder<T> {

  // the implementation is here on purpose - we need to use methods of binding
  // which are only in implementation and which are visible only for this package
  /** Binding which is created by this builder. */
  protected final BindingImpl<T> binding;

  /**
   * Constructs new builder of binding for given annotation.
   *
   * @param annotation the class type of annotation which can be bound to some
   *          object.
   */
  public BindingBuilderImpl(Class<? extends Annotation> annotation) {
    binding = new BindingImpl<T>(annotation);
  }

  /**
   * {@inheritDoc}
   */
  public BindingBuilder<T> to(Class<? extends T> clazz) {
    Preconditions.checkArgumentNotNull(clazz, "Class type cannot be null");
    Preconditions.checkArgument(!clazz.isInterface(), "Class type cannot be interface");
    Preconditions.checkArgument(!Modifier.isAbstract(clazz.getModifiers()), "Class type cannot be abstract");

    binding.setType(clazz);

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public BindingBuilder<T> to(T instance) {
    Preconditions.checkArgumentNotNull(instance, "Instance cannot be null");
    binding.setInstance(instance);
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
   * {@inheritDoc}
   */
  public Binding<T> getBinding() {
    return binding;
  }

}
