package cz.zcu.kiv.jop.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import cz.zcu.kiv.jop.util.Defaults;
import cz.zcu.kiv.jop.util.Preconditions;

/**
 * Implementation of virtual property which has no declaring class (no owner) and holds only some
 * value. The virtual property only emulates the real one: it has name, declared class type and
 * annotations but has no owner. Also the virtual property has (virtual) getter and setter.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Declared class type of property.
 */
public class VirtualProperty<T> implements Property<T> {

  /**
   * Determines if a de-serialized file is compatible with this class.
   * <p>
   * Maintainers must change this value if and only if the new version of this class is not
   * compatible with old versions. See Oracle docs for <a
   * href="http://docs.oracle.com/javase/1.5.0/docs/guide/ serialization/">details</a>.
   * <p>
   * Not necessary to include in first version of the class, but included here as a reminder of its
   * importance.
   */
  private static final long serialVersionUID = 20160326L;

  /** Name of property. */
  protected final String propertyName;
  /** Declared type of property. */
  protected final Class<T> propertyType;

  /** Holder of property value. */
  private T property;

  /** Map of annotations of virtual property. */
  protected final Map<Class<? extends Annotation>, Annotation> annotations;
  /** Map of declared annotations of virtual property. */
  protected final Map<Class<? extends Annotation>, Annotation> declaredAnnotations;

  /** Property access (getter and setter) for virtual property. */
  protected final VirtualPropertyAccess access;

  /** Helper constant for empty array of annotations. */
  private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];

  /**
   * Constructs the virtual property with given name and class type.
   *
   * @param propertyName the property name.
   * @param propertyType the property class type.
   */
  public VirtualProperty(String propertyName, Class<T> propertyType) {
    this.propertyName = Preconditions.checkArgumentNotNull(propertyName, "Name of property cannot be null");
    this.propertyType = Preconditions.checkArgumentNotNull(propertyType, "Declared type of property cannot be null");

    this.annotations = new LinkedHashMap<Class<? extends Annotation>, Annotation>();
    this.declaredAnnotations = new LinkedHashMap<Class<? extends Annotation>, Annotation>();

    this.access = new VirtualPropertyAccess();
  }

  /**
   * Constructs the virtual property with given name and class type. The virtual property will be
   * annotated by (declared) annotations from given array.
   *
   * @param propertyName the property name.
   * @param propertyType the property class type.
   * @param annotations the array of property annotations.
   */
  public VirtualProperty(String propertyName, Class<T> propertyType, Annotation[] annotations) {
    this(propertyName, propertyType);
    setAnnotations(annotations);
    setDeclaredAnnotations(annotations);
  }

  /**
   * Constructs the virtual property with given name and class type. The virtual property will be
   * annotated by annotations from given array. This constructor also allows to specify subset of
   * annotations which are declared only for this property (they are not inherited).
   *
   * @param propertyName the property name.
   * @param propertyType the property class type.
   * @param annotations the array of all property annotations.
   * @param declaredAnnotations
   */
  public VirtualProperty(String propertyName, Class<T> propertyType, Annotation[] annotations, Annotation[] declaredAnnotations) {
    this(propertyName, propertyType);
    setAnnotations(annotations);
    setDeclaredAnnotations(declaredAnnotations);
  }

  /**
   * Constructs the virtual property with given name and class type. The virtual property will be
   * annotated by (declared) annotations from given collection.
   *
   * @param propertyName the property name.
   * @param propertyType the property class type.
   * @param annotations the collection of property annotations.
   */
  public VirtualProperty(String propertyName, Class<T> propertyType, Collection<Annotation> annotations) {
    this(propertyName, propertyType);
    setAnnotations(annotations);
    setDeclaredAnnotations(annotations);
  }

  /**
   * Constructs the virtual property with given name and class type. The virtual property will be
   * annotated by annotations from given collection. This constructor also allows to specify subset
   * of annotations which are declared only for this property (they are not inherited).
   *
   * @param propertyName the property name.
   * @param propertyType the property class type.
   * @param annotations the collection of property annotations.
   * @param declaredAnnotations the collection of declared property annotations.
   */
  public VirtualProperty(String propertyName, Class<T> propertyType, Collection<Annotation> annotations, Collection<Annotation> declaredAnnotations) {
    this(propertyName, propertyType);
    setAnnotations(annotations);
    setDeclaredAnnotations(declaredAnnotations);
  }

  /**
   * {@inheritDoc}
   * <p>
   * <b>Notice:</b> This method returns always <code>null</code> because the virtual property has no
   * owner.
   */
  public Class<?> getDeclaringClass() {
    return null; // virtual property has no owner
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {
    return propertyName;
  }

  /**
   * {@inheritDoc}
   */
  public Class<T> getType() {
    return propertyType;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
    return annotations.containsKey(annotationType);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
    return (A)annotations.get(annotationType);
  }

  /**
   * {@inheritDoc}
   */
  public Annotation[] getAnnotations() {
    return annotations.values().toArray(EMPTY_ANNOTATION_ARRAY);
  }

  /**
   * Sets given annotations to virtual property. The array should contains all annotations of
   * property (even the declared annotations).
   *
   * @param annotations the annotation of property to set.
   */
  protected void setAnnotations(Annotation[] annotations) {
    this.annotations.clear();

    if (annotations != null && annotations.length > 0) {
      for (Annotation annotation : annotations) {
        this.annotations.put(annotation.annotationType(), annotation);
      }
    }
  }

  /**
   * Sets given annotations to virtual property. The collection should contains all annotations of
   * property (even the declared annotations).
   *
   * @param annotations the annotation of property to set.
   */
  protected void setAnnotations(Collection<Annotation> annotations) {
    this.annotations.clear();

    if (annotations != null && annotations.size() > 0) {
      for (Annotation annotation : annotations) {
        this.annotations.put(annotation.annotationType(), annotation);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public Annotation[] getDeclaredAnnotations() {
    return declaredAnnotations.values().toArray(EMPTY_ANNOTATION_ARRAY);
  }

  /**
   * Sets given declared annotations to virtual property. The array should contains all declared
   * annotations of property.
   *
   * @param declaredAnnotations the annotation of property to set.
   */
  protected void setDeclaredAnnotations(Annotation[] declaredAnnotations) {
    this.declaredAnnotations.clear();

    if (declaredAnnotations != null && declaredAnnotations.length > 0) {
      for (Annotation annotation : declaredAnnotations) {
        this.declaredAnnotations.put(annotation.annotationType(), annotation);
      }
    }
  }

  /**
   * Sets given declared annotations to virtual property. The collection should contains all
   * declared annotations of property.
   *
   * @param declaredAnnotations the annotation of property to set.
   */
  protected void setDeclaredAnnotations(Collection<Annotation> declaredAnnotations) {
    this.declaredAnnotations.clear();

    if (declaredAnnotations != null && declaredAnnotations.size() > 0) {
      for (Annotation annotation : declaredAnnotations) {
        this.declaredAnnotations.put(annotation.annotationType(), annotation);
      }
    }
  }

  /**
   * Returns the <em>getter</em> for the virtual property.
   *
   * @return The <em>getter</em> for the virtual property.
   */
  public Getter<T> getGetter() {
    return access;
  }

  /**
   * Returns the <em>setter</em> for the virtual property.
   *
   * @return The <em>setter</em> for the virtual property.
   */
  public Setter<T> getSetter() {
    return access;
  }

  /**
   * Returns string value of property.
   *
   * @return String value of property.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [propertyName=" + propertyName + "]";
  }

  /**
   * Implementation of virtual property access (getter and setter). The virtual property has no
   * owner (this property owns itself) so this class is not static and for accessing to property
   * doesn't use the reflection but direct access to virtual property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   */
  class VirtualPropertyAccess implements Getter<T>, Setter<T> {

    /**
     * Determines if a de-serialized file is compatible with this class.
     * <p>
     * Maintainers must change this value if and only if the new version of this class is not
     * compatible with old versions. See Oracle docs for <a
     * href="http://docs.oracle.com/javase/1.5.0/docs/guide/ serialization/">details</a>.
     * <p>
     * Not necessary to include in first version of the class, but included here as a reminder of
     * its importance.
     */
    private static final long serialVersionUID = 20160326L;

    /**
     * Constructs access (getter and setter) for virtual property.
     */
    VirtualPropertyAccess() {}

    /**
     * {@inheritDoc}
     */
    public String getPropertyName() {
      return propertyName;
    }

    /**
     * {@inheritDoc}
     */
    public Class<T> getType() {
      return propertyType;
    }

    /**
     * Returns <code>null</code> because virtual property has no access method/member.
     *
     * @return Always <code>null</code>.
     */
    public Member getMember() {
      return null;
    }

    /**
     * Returns <code>null</code> because virtual property has no access method/member.
     *
     * @return Always <code>null</code>.
     */
    public Method getMethod() {
      return null;
    }

    /**
     * Returns <code>null</code> because virtual property has no access method/member.
     *
     * @return Always <code>null</code>.
     */
    public String getMethodName() {
      return null;
    }

    /**
     * Sets given value of virtual property.
     *
     * @param owner the owner of the property (unused because virtual property has no owner).
     * @param value the value which will be set to property.
     * @throws PropertyAccessException If given value is not assignable to property.
     */
    @SuppressWarnings("unchecked")
    public void set(Object owner, T value) throws PropertyAccessException {
      if (value != null) {
        if (!getType().isAssignableFrom(value.getClass())) {
          throw new PropertyAccessException("Given incorrect value type for", null, "virtual property '" + propertyName + "'", true);
        }
        property = value;
      }
      else {
        property = (T)Defaults.getDefaultValue(getType());
      }
    }

    /**
     * Returns actual value of virtual property.
     *
     * @param owner the owner of the property (unused because virtual property has no owner).
     * @return The value of virtual property.
     */
    public T get(Object owner) {
      return property;
    }

    /**
     * Returns a string representation of property accessor.
     *
     * @return String representation of property accessor.
     */
    @Override
    public String toString() {
      return getClass().getName() + " [" + getPropertyName() + ']';
    }
  }
}
