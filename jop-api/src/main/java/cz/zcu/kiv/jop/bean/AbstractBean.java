package cz.zcu.kiv.jop.bean;

import java.lang.annotation.Annotation;

import cz.zcu.kiv.jop.util.Preconditions;

/**
 * The abstract implementation of {@link Bean} interface which implements all methods which are
 * common for all beans - the methods which are using methods of {@link Class}.
 * <p>
 * Bean should be created for classes which may be abstract. The bean shouldn't be created for
 * interfaces, enumerations, annotations, arrays, etc. because they are not beans and usually
 * contains no properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class AbstractBean implements Bean {

  /** The class type of bean. */
  protected final Class<?> beanType;

  /**
   * Construct a new bean for given class type.
   *
   * @param beanType the class type of bean.
   * @throws IllegalArgumentException If given class type is <code>null</code> interface,
   *           enumeration or annotation.
   */
  public AbstractBean(Class<?> beanType) {
    Preconditions.checkArgumentNotNull(beanType, "Class type of bean cannot be null");
    Preconditions.checkArgument(!beanType.isAnnotation() && !beanType.isArray() && !beanType.isEnum() && !beanType.isInterface() && !beanType.isPrimitive()
        && !beanType.isSynthetic(), "Invalid class type of bean");

    this.beanType = beanType;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
    return beanType.isAnnotationPresent(annotationType);
  }

  /**
   * {@inheritDoc}
   */
  public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
    return beanType.getAnnotation(annotationType);
  }

  /**
   * {@inheritDoc}
   */
  public Annotation[] getAnnotations() {
    return beanType.getAnnotations();
  }

  /**
   * {@inheritDoc}
   */
  public Annotation[] getDeclaredAnnotations() {
    return beanType.getDeclaredAnnotations();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {
    return beanType.getName();
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getType() {
    return beanType;
  }

}
