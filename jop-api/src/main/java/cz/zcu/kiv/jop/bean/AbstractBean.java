package cz.zcu.kiv.jop.bean;

import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The abstract implementation of {@link Bean} interface which implements all methods which are
 * common for all beans - the methods which are using methods of {@link Class}.
 * <p>
 * Bean can be created only for classes which may be also abstract. The bean shouldn't be created
 * for interfaces, enumerations, annotations, arrays, etc. because they are not beans and usually
 * contains no properties. The bean creation is not forbidden for {@link Object} class even if it
 * contains only (native) methods.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class AbstractBean implements Bean {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(AbstractBean.class);

  /** The class type of bean. */
  protected final Class<?> beanType;

  /**
   * Construct a new bean for given class type.
   *
   * @param beanType the class type of bean.
   * @throws BeanException If given class type is not valid.
   */
  public AbstractBean(Class<?> beanType) {
    checkClassType(beanType);

    if (beanType == Object.class) {
      logger.warn("Class type of bean is Object which has no properties");
    }

    this.beanType = beanType;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {
    return getType().getName();
  }

  /**
   * {@inheritDoc}
   */
  public final Class<?> getType() {
    return beanType;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
    return getType().isAnnotationPresent(annotationType);
  }

  /**
   * {@inheritDoc}
   */
  public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
    return getType().getAnnotation(annotationType);
  }

  /**
   * {@inheritDoc}
   */
  public Annotation[] getAnnotations() {
    return getType().getAnnotations();
  }

  /**
   * {@inheritDoc}
   */
  public Annotation[] getDeclaredAnnotations() {
    return getType().getDeclaredAnnotations();
  }

  /**
   * Returns a hash code value for this bean. This method is supported for the benefit of hash
   * tables such as those provided by {@link java.util.HashMap}.
   *
   * @return A hash code value for this bean.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((beanType == null) ? 0 : beanType.hashCode());

    return result;
  }

  /**
   * Indicates whether some object is "equal to" this bean.
   *
   * @param obj the reference object with which to compare.
   * @return <code>true</code> if this bean is the same as the obj argument; <code>false</code>
   *         otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Bean)) {
      return false;
    }

    return getType() == ((Bean)obj).getType();
  }

  /**
   * Returns string value of this bean.
   *
   * @return String value of this bean.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [beanType=" + (getType() != null ? getType().getName() : null) + "]";
  }

  /**
   * Checks the given class type whatever is valid for bean.
   *
   * @param clazz the class type to check.
   */
  protected static void checkClassType(Class<?> clazz) {
    if (clazz == null) {
      throw new BeanException("Class type of bean cannot be null");
    }
    else if (clazz.isInterface()) {
      throw new BeanException("Class type of bean cannot be interface");
    }
    else if (clazz.isAnnotation()) {
      throw new BeanException("Class type of bean cannot be annotation");
    }
    else if (clazz.isArray()) {
      throw new BeanException("Class type of bean cannot be array");
    }
    else if (clazz.isEnum()) {
      throw new BeanException("Class type of bean cannot be enumeration");
    }
    else if (clazz.isPrimitive()) {
      throw new BeanException("Class type of bean cannot be primitive");
    }
    else if (clazz.isSynthetic()) {
      throw new BeanException("Class type of bean cannot be synthetic");
    }
  }

}
