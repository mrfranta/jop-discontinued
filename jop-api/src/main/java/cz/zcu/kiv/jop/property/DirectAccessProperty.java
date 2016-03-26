package cz.zcu.kiv.jop.property;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The implementation of <em>Object</em>'s property. It provides the getter and setter, which use
 * the {@link Field} for manipulation with property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Declared class type of property.
 */
public class DirectAccessProperty<T> extends AbstractProperty<T> {

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

  /**
   * Constructs a direct access property.
   *
   * @param declaringClass the class type of a property owner.
   * @param propertyName the name of property.
   */
  public DirectAccessProperty(Class<?> declaringClass, String propertyName) {
    super(declaringClass, propertyName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Getter<T> createGetter() throws GetterNotFoundException {
    try {
      return new DirectGetter<T>(propertyName, getField());
    }
    catch (PropertyNotFoundException exc) {
      throw new GetterNotFoundException(exc, declaringClass, propertyName);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Setter<T> createSetter() throws SetterNotFoundException {
    try {
      Field field = getField();
      if (field != null && Modifier.isFinal(field.getModifiers())) {
        throw new SetterNotFoundException("Cannot create setter for read-only field", declaringClass, propertyName);
      }

      return new DirectSetter<T>(propertyName, getField());
    }
    catch (PropertyNotFoundException exc) {
      throw new SetterNotFoundException(exc, declaringClass, propertyName);
    }
  }

  /**
   * Implementation of {@code Getter} interface which uses {@link Field} for direct access to
   * property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   *
   * @param <T> Declared class type of property.
   */
  public static class DirectGetter<T> extends AbstractPropertyAccess<T, Field> implements Getter<T> {

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

    /** Logger used for logging. */
    private static final Log logger = LogFactory.getLog(DirectAccessProperty.DirectGetter.class);

    /**
     * Constructs a getter.
     *
     * @param propertyName the name of property.
     * @param field the field which will be used for access to property.
     */
    DirectGetter(String propertyName, Field field) {
      super(propertyName, field);
    }

    /**
     * {@inheritDoc}
     */
    public Class<?> getType() {
      return member.getType();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(Object owner) throws PropertyAccessException {
      try {
        return (T)member.get(owner);
      }
      catch (NullPointerException exc) {
        throw createGetterAccessException("Static call of non-static", exc);
      }
      catch (IllegalAccessException exc) {
        // shouldn't occur
        throw createGetterAccessException("Illegal access occured during call of", exc);
      }
      catch (IllegalArgumentException exc) {
        String ownerName = (owner == null) ? null : owner.getClass().getName();
        logger.error("Given incorrect owner '" + ownerName + "' for getter of property: " + getDeclaringClassName() + '.' + getPropertyName());
        throw createGetterAccessException("Given incorrect owner for calling", exc);
      }
      catch (Exception exc) {
        throw createGetterAccessException("An exception occured during call of", exc);
      }
    }

  }

  /**
   * Implementation of {@code Setter} interface which uses {@link Field} for direct access to
   * property value.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   *
   * @param <T> Declared class type of property.
   */
  public static class DirectSetter<T> extends AbstractPropertyAccess<T, Field> implements Setter<T> {

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

    /** Logger used for logging. */
    private static final Log logger = LogFactory.getLog(DirectAccessProperty.DirectSetter.class);

    /**
     * Constructs a setter.
     *
     * @param propertyName the name of property.
     * @param field the field which will be used for access to property.
     */
    DirectSetter(String propertyName, Field field) {
      super(propertyName, field);
    }

    /**
     * {@inheritDoc}
     */
    public Class<?> getType() {
      return member.getType();
    }

    /**
     * {@inheritDoc}
     */
    public void set(Object owner, T value) throws PropertyAccessException {
      try {
        member.set(owner, value);
      }
      catch (NullPointerException exc) {
        if (owner == null) {
          throw createGetterAccessException("Static call of non-static", exc);
        }
        else {
          throw createGetterAccessException("Null pointer exception occured inside", exc);
        }
      }
      catch (IllegalAccessException exc) {
        // shouldn't occur
        throw createGetterAccessException("Illegal access occured during call of", exc);
      }
      catch (IllegalArgumentException exc) {
        if (value == null && getType().isPrimitive()) {
          throw createSetterAccessException("Null value was assigned to a property of primitive type while calling", exc);
        }
        else if (owner != null && !getDeclaringClass().isAssignableFrom(owner.getClass())) {
          String ownerName = owner.getClass().getName();
          logger.error("Given incorrect owner '" + ownerName + "' for setter of property: " + getDeclaringClassName() + '.' + getPropertyName());
          throw createGetterAccessException("Given incorrect owner for calling", exc);
        }
        else {
          logger.error("Given incorrect value type for setter of property: " + getDeclaringClassName() + '.' + getPropertyName()
              + " expected type: " + getType().getName() + ", given value type: " + (value == null ? null : value.getClass().getName()));
          throw createSetterAccessException("Given incorrect value type for", exc);
        }
      }
      catch (Exception exc) {
        throw createGetterAccessException("An exception occured during call of", exc);
      }
    }
  }
}
