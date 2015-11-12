package cz.zcu.kiv.jop.binding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.util.Preconditions;

/**
 * Basic implementation of {@link Binding} interface which allows to create
 * binding between some annotation and some class which is same like or extends
 * the type given as generic parameter.
 * <p>
 * Generally is binding created by {@link BindingBuilder} which gradually fills
 * parameters of binding via methods with package-only visibility.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of objects which can be bound for some annotation. It's
 *          strongly suggested to choose interface or abstract class as generic
 *          type.
 */
public class BindingImpl<T> implements Binding<T> {

  /** Class type of annotation for which was created this binding. */
  protected final Class<? extends Annotation> annotation;
  /** Class type of object which was bound to annotation. */
  protected Class<? extends T> type;
  /** Instance of object which was bound to annotation. */
  protected transient T instance;
  /** Information whether the bound object will be singleton. */
  protected boolean isSingleton;

  /**
   * Constructs new binding for given class type of annotation.
   *
   * @param annotation the class type of annotation.
   */
  protected BindingImpl(Class<? extends Annotation> annotation) {
    this(annotation, null, false);
  }

  /**
   * Constructs new binding between given class type of annotation and class
   * type of object which was bound to annotation.
   *
   * @param annotation the class type of annotation.
   * @param type the class type of object which will be bound to annotation.
   */
  public BindingImpl(Class<? extends Annotation> annotation, Class<? extends T> type) {
    this(annotation, type, false);
  }

  /**
   * Constructs new binding between given class type of annotation and class
   * type of object which was bound to annotation. Based on the argument
   * <code>isSingletion<code> the bound object will be singleton.
   *
   * @param annotation the class type of annotation.
   * @param type the class type of object which will be bound to annotation.
   * @param isSingleton information whether the bound object will be singleton.
   */
  public BindingImpl(Class<? extends Annotation> annotation, Class<? extends T> type, boolean isSingleton) {
    this.annotation = Preconditions.checkArgumentNotNull(annotation, "Annotation type cannot be null");
    this.type = type;
    this.isSingleton = isSingleton;
  }

  /**
   * Constructs new binding between given class type of annotation and instance
   * of object which was bound to annotation. The bound object will be
   * singleton.
   *
   * @param annotation the class type of annotation.
   * @param instance the instance of object which will be bound to annotation.
   */
  @SuppressWarnings("unchecked")
  public BindingImpl(Class<? extends Annotation> annotation, T instance) {
    this(annotation, (Class<T>)instance.getClass(), true);
    this.instance = instance;
  }

  /**
   * {@inheritDoc}
   */
  public Class<? extends Annotation> getAnnotation() {
    return annotation;
  }

  /**
   * {@inheritDoc}
   */
  public Class<? extends T> getType() {
    return type;
  }

  /**
   * Sets given class type of object which was bound to annotation for which was
   * created this binding.
   *
   * @param type the class type of object which will be bound to annotation.
   */
  void setType(Class<? extends T> type) {
    this.type = type;
  }

  /**
   * Sets given instance which was bound to annotation for which was created
   * this binding.
   *
   * @param instance the instance of object which will be bound to annotation.
   */
  void setInstance(T instance) {
    this.instance = instance;
    this.isSingleton = true;
  }

  /**
   * Returns information whatever the bound type is annotated by
   * {@link Singleton} annotation - this type is always singleton.
   *
   * @return <code>true</code> if bound type is annotated by {@link Singleton}
   *         annotation; <code>false</code> otherwise.
   */
  final boolean isAnnotatedAsSingleton() {
    return (type != null && type.getAnnotation(Singleton.class) != null);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSingleton() {
    return isSingleton || isAnnotatedAsSingleton();
  }

  /**
   * Sets information whether the bound object will be singleton or each call of
   * {@link #getInstance()} method will return new instance.
   *
   * @param isSingleton information whether the bound object will be singleton.
   */
  void setSingleton(boolean isSingleton) {
    this.isSingleton = isSingleton;
  }

  /**
   * Returns instance of bound object to annotation for which was created this
   * binding. The instance can be same or different for each call (depends on
   * {@link #isSingleton()} flag). This method suppress all exceptions thrown
   * during object instance creation because it serves to creation of eager
   * singletons.
   *
   * @return Instance of bound object to annotation for which was created this
   *         binding or <code>null</code> in case of some error.
   */
  protected T getInstanceQuietly() {
    try {
      return getInstance();
    }
    catch (BindingException exc) {
      // quiet mode
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public T getInstance() {
    if (instance == null) {
      if (type == null) {
        throw new BindingException("No class bound for annotation " + annotation.getName());
      }

      Constructor<? extends T> constructor = null;
      try {
        constructor = type.getDeclaredConstructor();
      }
      catch (Exception exc) {
        throw new BindingException("Cannot get declared parameterless constructor of " + type.getName());
      }

      constructor.setAccessible(true);

      try {
        if (!isSingleton()) {
          return constructor.newInstance();
        }

        instance = constructor.newInstance();
      }
      catch (Exception exc) {
        throw new BindingException("Cannot create new instance of " + type.getName());
      }
    }

    return instance;
  }

  /**
   * Returns a string representation of binding.
   *
   * @return String representation of binding.
   */
  @Override
  public String toString() {
    //@formatter:off
    return getClass().getName() +
        " [annotation=" + (annotation == null ? null : annotation.getName()) +
        ", class = " + (type == null ? null : type.getName()) + "]";
    //@formatter:on
  }

}
