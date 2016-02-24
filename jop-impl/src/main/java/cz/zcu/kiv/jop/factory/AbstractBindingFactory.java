package cz.zcu.kiv.jop.factory;

import java.lang.annotation.Annotation;

import cz.zcu.kiv.jop.factory.binding.Binder;
import cz.zcu.kiv.jop.factory.binding.BinderImpl;
import cz.zcu.kiv.jop.factory.binding.Binding;
import cz.zcu.kiv.jop.factory.binding.BindingBuilder;
import cz.zcu.kiv.jop.factory.binding.BindingException;
import cz.zcu.kiv.jop.ioc.callback.InitException;
import cz.zcu.kiv.jop.ioc.callback.Initializable;
import cz.zcu.kiv.jop.util.Preconditions;

/**
 * Abstract implementation of {@link BindingFactory} interface which implements method
 * {@link #createInstance(Annotation)} which provides created instances of bound class to given
 * annotation as parameter.
 * <p>
 * Also this factory extends {@link AbstractFactory} which implements method
 * {@link #createInstance(Class)} which creates of instances of given classes.
 * <p>
 * This factory uses {@link Binder} to create {@link Binding Bindings} between annotations and
 * objects of type <code>T</code>. Factories which extends this class has to implement method
 * {@link #configure()} which mainly serves for preparation of bindings. There is also possibility
 * to create custom bindings in method {@link #customConfigure()}.
 * <p>
 * TThis abstract implementation expects that it will be provided by injection. Because of that
 * implements callback interface {@link Initializable}. In case that the class will be constructed
 * manually, it should be called method {@link #init()} or {@link #configure(Binder)} from
 * constructor.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of interface which instances will be created by this factory.
 */
public abstract class AbstractBindingFactory<T> extends AbstractFactory<T> implements BindingFactory<T>, Initializable {

  /** Binder which stores bindings between annotations and classes. */
  private Binder<T> binder;
  /** Information whether was binding factory already configured. */
  private boolean configured;

  /**
   * Initializes factory. This method creates new binder and then calls the method
   * {@link #configure(Binder)}.
   *
   * @throws InitException If initialization of binding factory fails.
   */
  public final void init() throws InitException {
    try {
      configure(createBinder());
    }
    catch (Exception exc) {
      throw new InitException("Initialization of factory failed", exc);
    }
  }

  /**
   * {@inheritDoc}
   */
  public final void configure(Binder<T> binder) throws FactoryException {
    Preconditions.checkState(this.binder == null, "Re-entry is not allowed");
    this.binder = binder;
    try {
      configure();
      customConfigure();
    }
    catch (BindingException exc) {
      throw new FactoryException(exc.getMessage());
    }
    finally {
      configured = true;
    }
  }

  /**
   * Returns binder which stores bindings between annotations and classes. In case that
   * configuration was finished this method throws {@link IllegalStateException} because it's not
   * expected that bindings will be changed.
   *
   * @return Binder which stores bindings between annotations and classes.
   * @throws IllegalStateException If configuration of factory was already finished.
   */
  protected final Binder<T> getBinder() {
    Preconditions.checkState(!configured, "Binding factory is already configured so bindings cannot be changed");
    return binder;
  }

  /**
   * Configures default bindings between annotations and classes. This method should be marked as
   * final in factory which implements it. For customization of factory is prepared method
   * {@link #customConfiguration()} in which can be some bindings re-binded.
   *
   * @throws BindingException If some error occurs during configuration of binding factory.
   */
  protected abstract void configure() throws BindingException;

  /**
   * Custom configuration of bindings between annotations and classes. This method can be overridden
   * in classes which extends the factory - it serves for customization.
   *
   * @throws BindingException If some error occurs during configuration of binding factory.
   */
  protected void customConfigure() throws BindingException {
    // to override
  }

  /**
   * Creates binder which will be used for storing of bindings between annotations and classes. This
   * method is called from constructor and should not be called from another place.
   *
   * @return Created binder which stores bindings between annotations and classes.
   */
  protected Binder<T> createBinder() {
    return new BinderImpl<T>();
  }

  /**
   * Check conditions for given annotation type. This method is mainly intended for extensions of
   * binding factory. This method check by default only whether the given annotation isn't
   * <code>null</code>. In overridden versions can be applied more complicated conditions.
   *
   * @param annotation the annotation type to check.
   * @throws BindingException if entered annotation isn't valid.
   */
  protected void checkAnnotation(Class<? extends Annotation> annotation) throws BindingException {
    if (annotation == null) {
      throw new BindingException("Annotation type cannot be null");
    }
  }

  /**
   * Creates builder for binding of given annotation type. In case that given annotation type was
   * already bound the exception can be thrown.
   *
   * @param annotation the class type of annotation of which will be created binding (builder).
   * @return Binding builder for binding creation.
   * @throws BindingException If given annotation is not valid or if some binding for given
   *           annotation already exists.
   */
  protected BindingBuilder<T> bind(Class<? extends Annotation> annotation) throws BindingException {
    checkAnnotation(annotation);

    return getBinder().bind(annotation);
  }

  /**
   * Creates builder for binding of given annotation type. In case that given annotation type was
   * already bound no exception is thrown but already created binding is replaced with this one.
   *
   * @param annotation the class type of annotation of which will be created binding (builder).
   * @return Binding builder for binding creation.
   * @throws BindingException If given annotation is not valid.
   */
  protected BindingBuilder<T> rebind(Class<? extends Annotation> annotation) {
    checkAnnotation(annotation);

    return getBinder().rebind(annotation);
  }

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
  protected Binding<T> getBinding(Class<? extends Annotation> annotation) {
    checkAnnotation(annotation);

    return getBinder().getBinding(annotation);
  }

  /**
   * Returns instance of bound object for given annotation.
   *
   * @param annotation the annotation for which will be returned the bound object.
   * @return Instance of bound object for given annotation.
   * @throws BindingException If given annotation is not valid or if no binding was found for given
   *           annotation or if some error occurs during creation of instance of bound object.
   */
  protected final T getInstance(Annotation annotation) throws BindingException {
    Class<? extends Annotation> annotationType = (annotation == null) ? null : annotation.annotationType();
    checkAnnotation(annotationType);

    Binding<T> binding = binder.getBinding(annotationType);
    if (binding == null) {
      throw new BindingException("No binding found for annotation '" + annotationType + "'");
    }

    return binding.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  public <A extends Annotation> T createInstance(A annotation) throws FactoryException {
    try {
      return getInstance(annotation);
    }
    catch (Exception exc) {
      throw new FactoryException(exc);
    }
  }

}
