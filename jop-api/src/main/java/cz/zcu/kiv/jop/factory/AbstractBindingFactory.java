package cz.zcu.kiv.jop.factory;

import java.lang.annotation.Annotation;

import cz.zcu.kiv.jop.binding.Binder;
import cz.zcu.kiv.jop.binding.BinderImpl;
import cz.zcu.kiv.jop.binding.Binding;
import cz.zcu.kiv.jop.binding.BindingBuilder;
import cz.zcu.kiv.jop.binding.BindingException;
import cz.zcu.kiv.jop.util.Preconditions;

/**
 * Abstract implementation of {@link BindingFactory} interface which implements
 * method {@link #createInstance(Annotation)} which provides created instances
 * of bound class to given annotation as parameter.
 * <p>
 * Also this factory extends {@link AbstractFactory} which implements method
 * {@link #createInstance(Class)} which creates of instances of given classes.
 * <p>
 * This factory uses {@link Binder} to create {@link Binding Bindings} between
 * annotations and classes and method. Factories which extends this class has to
 * implement method {@link #defaultConfiguration()} which serves for bindings
 * creation. There is also possibility to create custom bindings in method
 * {@link #customConfiguration()}.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of interface which instances will be created by this factory.
 */
public abstract class AbstractBindingFactory<T> extends AbstractFactory<T> implements BindingFactory<T> {

  /** Binder which stores bindings between annotations and classes. */
  final Binder<T> binder;
  /** Information whether the binding factory was configured. */
  boolean configured;

  /**
   * Constructs and configures a new binding factory.
   */
  public AbstractBindingFactory() {
    binder = createBinder();
    configure();
  }

  /**
   * Creates binder which will be used for storing of bindings between
   * annotations and classes. This method is called from constructor and should
   * not be called from another place.
   *
   * @return Created binder which stores bindings between annotations and
   *         classes.
   */
  protected Binder<T> createBinder() {
    return new BinderImpl<T>();
  }

  /**
   * Returns binder which stores bindings between annotations and classes. In
   * case that configuration was finished this method throws
   * {@link IllegalStateException} because it's not expected that bindings will
   * be changed.
   *
   * @return Binder which stores bindings between annotations and classes.
   * @throws IllegalStateException If configuration of factory was finished.
   */
  protected final Binder<T> getBinder() {
    Preconditions.checkState(!configured, "Binding factory is already configured -> cannot change bindings");

    return binder;
  }

  /**
   * In case that factory wasn't configured yet it will configure the bindings
   * calling methods {@link #defaultConfiguration()} for default configuration
   * of bindings and {@link #customConfiguration()} for custom bindings.
   */
  protected final void configure() {
    if (!configured) {
      defaultConfiguration();
      customConfiguration();
      configured = true;
    }
  }

  /**
   * Configures default bindings between annotations and classes. This method
   * should be marked as final in factory which implements it. For customization
   * of factory is prepared method {@link #customConfiguration()} in which can
   * be some bindings re-binded.
   */
  protected abstract void defaultConfiguration();

  /**
   * Custom configuration of bindings between annotations and classes. This
   * method can be overridden in classes which extends the factory - it serves
   * for customization.
   */
  protected void customConfiguration() {
    // to override
  }

  /**
   * Creates builder for binding of given annotation type. In case that given
   * annotation type was already bound the exception can be thrown.
   *
   * @param annotation the class type of annotation of which will be created
   *          binding (builder).
   * @return Binding builder for binding creation.
   * @throws BindingException If some binding for given annotation already
   *           exists.
   */
  protected BindingBuilder<T> bind(Class<? extends Annotation> annotation) {
    return getBinder().bind(annotation);
  }

  /**
   * Creates builder for binding of given annotation type. In case that given
   * annotation type was already bound no exception is thrown but already
   * created binding is replaced with this one.
   *
   * @param annotation the class type of annotation of which will be created
   *          binding (builder).
   * @return Binding builder for binding creation.
   */
  protected BindingBuilder<T> rebind(Class<? extends Annotation> annotation) {
    return getBinder().rebind(annotation);
  }

  /**
   * Returns created binding (even if it wasn't finished by their builder) for
   * given <code>annotation</code>.
   *
   * @param annotation the class type of annotation for which will be returned
   *          binding.
   * @return Found binding for given annotation type;<code>null</code> in case
   *         that exception won't be thrown (not recommended).
   * @throws BindingException It may throws binding exception in case that
   *           binding for given annotation type was not found.
   */
  protected Binding<T> getBinding(Class<? extends Annotation> annotation) {
    return binder.getBinding(annotation);
  }

  /**
   * {@inheritDoc}
   */
  public <A extends Annotation> T createInstance(A annotation) throws FactoryException {
    Binding<T> binding = null;
    try {
      binding = getBinding(annotation.getClass());

      return binding.getInstance();
    }
    catch (BindingException exc) {
      throw new FactoryException(exc);
    }
  }

}
