package cz.zcu.kiv.jop.binding;

import java.lang.annotation.Annotation;

/**
 * This interface serves as binding between some annotation and some class which
 * is same like or extends the type given as generic parameter. The bindings can
 * be used in factories where some annotation can be bound to implementation of
 * some interface and then the factory can easily provide bound (singleton)
 * implementation for requested annotation.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @param <T> Type of objects which can be bound for some annotation. It's
 *          strongly suggested to choose interface or abstract class as generic
 *          type.
 */
public interface Binding<T> {

  /**
   * Returns class type of annotation for which was created this binding.
   *
   * @return Class type of annotation.
   */
  public Class<Annotation> getAnnotation();

  /**
   * Returns class type of object which was bound to annotation for which was
   * created this binding.
   *
   * @return Class type of bound object.
   */
  public Class<T> getType();

  /**
   * Returns information whether the bound object will be singleton or each call
   * of {@link #getInstance()} method will return new instance.
   *
   * @return <code>true</code> if bound object will be singleton;
   *         <code>false</code> otherwise.
   */
  public boolean isSingleton();

  /**
   * Returns instance of bound object to annotation for which was created this
   * binding. The instance can be same or different for each call (depends on
   * {@link #isSingleton()} flag). In case of some problem during getting of
   * object instance the exception can be thrown.
   *
   * @return Instance of bound object to annotation for which was created this
   *         binding.
   * @throws BindingException If some error occurs during getting of object
   *           instance.
   */
  public T getInstance() throws BindingException;

}
