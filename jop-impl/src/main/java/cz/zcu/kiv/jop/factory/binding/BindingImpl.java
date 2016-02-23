package cz.zcu.kiv.jop.factory.binding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.ioc.Injector;
import cz.zcu.kiv.jop.ioc.InjectorManager;
import cz.zcu.kiv.jop.util.ReflectionUtils;

/**
 * Basic implementation of {@link Binding} interface which allows to create binding between some
 * annotation and some class which is same like or extends the type given as generic parameter.
 * <p>
 * Generally is binding created by {@link BindingBuilder} which gradually fills parameters of
 * binding via methods with package-only visibility.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of objects which can be bound for some annotation. It's strongly suggested to
 *          choose interface or abstract class as generic type.
 */
public class BindingImpl<T> implements Binding<T> {

  /** Class type of annotation for which was created this binding. */
  protected final Class<? extends Annotation> annotation;

  /** Class type of object which was bound to annotation. */
  protected Class<? extends T> type;
  /** Constructor of type which will be used for construction of object bound to annotation. */
  protected Constructor<? extends T> constructor;
  /** Instance of object which was bound to annotation. */
  protected T instance;

  /** Information whether the bound object will be singleton. */
  protected boolean isSingleton;
  /** Information whether the bound object will be always singleton (for bound instances). */
  protected boolean isStrictSingleton;

  /** Message for error which occurred during binding creation. */
  protected String error;

  /**
   * Constructs new binding for given class type of annotation.
   *
   * @param annotation the class type of annotation.
   */
  protected BindingImpl(Class<? extends Annotation> annotation) {
    if (annotation == null) {
      throw new BindingException("Annotation type cannot be null");
    }
    this.annotation = annotation;
  }

  /**
   * Constructs new binding between given class type of annotation and class type of object which
   * was bound to annotation.
   *
   * @param annotation the class type of annotation.
   * @param type the class type of object which will be bound to annotation.
   */
  public BindingImpl(Class<? extends Annotation> annotation, Class<? extends T> type) {
    this(annotation);
    setType(type);
  }

  /**
   * Constructs new binding between given class type of annotation and class type of object which
   * was bound to annotation. Based on the argument <code>isSingleton</code> the bound object will
   * be singleton.
   *
   * @param annotation the class type of annotation.
   * @param type the class type of object which will be bound to annotation.
   * @param isSingleton information whether the bound object will be singleton.
   */
  public BindingImpl(Class<? extends Annotation> annotation, Class<? extends T> type, boolean isSingleton) {
    this(annotation);
    setType(type);
    setSingleton(isSingleton);
  }

  /**
   * Constructs new binding between given class type of annotation and constructor of object which
   * was bound to annotation.
   *
   * @param annotation the class type of annotation.
   * @param constructor the constructor of object which will be bound to annotation.
   */
  public BindingImpl(Class<? extends Annotation> annotation, Constructor<? extends T> constructor) {
    this(annotation);
    setConstructor(constructor);
  }

  /**
   * Constructs new binding between given class type of annotation and constructor of object which
   * was bound to annotation. Based on the argument <code>isSingleton</code> the bound object will
   * be singleton.
   *
   * @param annotation the class type of annotation.
   * @param constructor the constructor of object which will be bound to annotation.
   * @param isSingleton information whether the bound object will be singleton.
   */
  public BindingImpl(Class<? extends Annotation> annotation, Constructor<? extends T> constructor, boolean isSingleton) {
    this(annotation);
    setConstructor(constructor);
    setSingleton(isSingleton);
  }

  /**
   * Constructs new binding between given class type of annotation and instance of object which was
   * bound to annotation. The bound object will be singleton.
   *
   * @param annotation the class type of annotation.
   * @param instance the instance of object which will be bound to annotation.
   */
  public BindingImpl(Class<? extends Annotation> annotation, T instance) {
    this(annotation);
    setInstance(instance);
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
   * Sets given class type of object which was bound to annotation for which was created this
   * binding.
   *
   * @param type the class type of object which will be bound to annotation.
   */
  void setType(Class<? extends T> type) {
    clearBinding(); // clears binding
    checkClassType(type); // fills error
    this.type = type;
  }

  /**
   * Sets given constructor of object which was bound to annotation for which was created this
   * binding.
   *
   * @param constructor the constructor to set.
   */
  <S extends T> void setConstructor(Constructor<S> constructor) {
    clearBinding(); // clears binding
    if (constructor == null) {
      this.error = "Constructor cannot be null";
      return;
    }

    if (constructor.getParameterTypes().length > 0) {
      this.error = "Constructor has to be parameterless";
      return;
    }

    this.type = constructor.getDeclaringClass();
    this.constructor = constructor;
  }

  /**
   * Sets given instance which was bound to annotation for which was created this binding.
   *
   * @param instance the instance of object which will be bound to annotation.
   */
  @SuppressWarnings("unchecked")
  void setInstance(T instance) {
    clearBinding(); // clears binding

    if (instance == null) {
      this.error = "Instance cannot be null";
      return;
    }

    this.type = (Class<? extends T>)instance.getClass();
    this.instance = instance;
    this.isSingleton = true;
    this.isStrictSingleton = true; // strict singleton!
  }

  /**
   * Returns information whatever the bound type is annotated by {@link Singleton} annotation - this
   * type is always singleton.
   *
   * @return <code>true</code> if bound type is annotated by {@link Singleton} annotation;
   *         <code>false</code> otherwise.
   */
  final boolean isAnnotatedAsSingleton() {
    return (type != null && type.getAnnotation(Singleton.class) != null);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSingleton() {
    return isSingleton || isStrictSingleton || isAnnotatedAsSingleton();
  }

  /**
   * Sets information whether the bound object will be singleton or each call of
   * {@link #getInstance()} method will return new instance. If the bound class type is annotated by
   * {@link Singleton} annotation or if the binding was made for prepared instance then this method
   * has no effect.
   *
   * @param isSingleton information whether the bound object will be singleton.
   */
  void setSingleton(boolean isSingleton) {
    if (!isStrictSingleton && !isAnnotatedAsSingleton()) {
      this.isSingleton = isSingleton;
    }
  }

  /**
   * Returns instance of bound object to annotation for which was created this binding. The instance
   * can be same or different for each call (depends on {@link #isSingleton()} flag). This method
   * suppress all exceptions thrown during object instance creation because it serves to creation of
   * eager singletons.
   *
   * @return Instance of bound object to annotation for which was created this binding or
   *         <code>null</code> in case of some error.
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
  public T getInstance() throws BindingException {
    if (error != null) {
      throw new BindingException(error);
    }

    T ret = instance;
    if (ret == null) {
      try {
        Injector injector = InjectorManager.getInstance().get();
        if (constructor == null) {
          ret = injector.getInstance(type);
          if (isSingleton()) {
            instance = ret;
          }
        }
        else {
          ret = ReflectionUtils.createInstance(constructor);
          injector.injectMembers(ret); // lazy injection
          if (isSingleton()) {
            instance = ret;
          }
        }
      }
      catch (Exception exc) {
        throw new BindingException(exc.getMessage());
      }
    }

    return ret;
  }

  /**
   * Checks the given class type whatever is correct for binding.
   *
   * @param clazz the class type to check.
   * @return <code>true</code> if given class type is correct; <code>false</code> otherwise.
   */
  protected boolean checkClassType(Class<?> clazz) {
    if (clazz == null) {
      this.error = "Class type cannot be null";
    }
    else if (Modifier.isAbstract(clazz.getModifiers())) {
      this.error = "Class type cannot be abstract class";
    }
    else if (clazz.isInterface()) {
      this.error = "Class type cannot be interface";
    }
    else if (clazz.isAnnotation()) {
      this.error = "Class type cannot be annotation";
    }
    else if (clazz.isArray()) {
      this.error = "Class type cannot be array";
    }
    else if (clazz.isEnum()) {
      this.error = "Class type cannot be enumeration";
    }
    else if (clazz.isPrimitive()) {
      this.error = "Class type cannot be primitive";
    }
    else if (clazz.isSynthetic()) {
      this.error = "Class type cannot be synthetic";
    }
    else {
      return true;
    }

    return false;
  }

  /**
   * Clears binding. This method should be used before each set of bound class type, constructor or
   * instance.
   */
  protected void clearBinding() {
    this.type = null;
    this.constructor = null;
    this.instance = null;
    this.isStrictSingleton = false;
    this.error = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getError() {
    return error;
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
