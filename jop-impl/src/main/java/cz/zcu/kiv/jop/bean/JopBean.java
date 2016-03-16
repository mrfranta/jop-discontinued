package cz.zcu.kiv.jop.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.zcu.kiv.jop.annotation.Access;
import cz.zcu.kiv.jop.annotation.AccessType;
import cz.zcu.kiv.jop.property.BasicProperty;
import cz.zcu.kiv.jop.property.DirectAccessProperty;
import cz.zcu.kiv.jop.property.Property;

/**
 * Implementation of {@link Bean} which extends the {@link AbstractBean}. This implementation of
 * bean supports annotation {@link Access} which may be used for determination of access type of
 * properties. All created properties are stores declared properties in map of properties which
 * allows simple implementation of methods for accessing of properties:
 * <ul>
 * <li>{@link #containsProperty(String)} - returns information whatever the bean or their parents
 * contains property with given name.</li>
 * <li>{@link #containsDeclaredProperty(String)} - returns information whatever the bean contains
 * declared property with given name.</li>
 * <li>{@link #getProperties()} - returns all properties including the properties from parent beans
 * (classes).</li>
 * <li>{@link #getDeclaredProperties()} - returns all declared properties in bean class.</li>
 * <li>{@link #getProperty(String)} - returns property with given name which may be inherited from
 * parent classes. If property with given name is not present the {@link BeanException} is thrown.</li>
 * <li>{@link #getDeclaredProperty(String)} - returns property with given name from bean class. If
 * property with given name is not present the {@link BeanException} is thrown.</li>
 * </ul>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class JopBean extends AbstractBean {

  /** Parent of this bean. */
  protected final Bean parent;
  /** Declared properties of this bean. */
  protected final Map<String, Property<?>> properties;

  /**
   * Construct a new bean for given class type.
   *
   * @param beanType the class type of bean.
   * @throws BeanException If given class type is not valid.
   */
  public JopBean(Class<?> beanType) {
    super(beanType);

    parent = createParent();
    properties = createProperties();
  }

  /**
   * Creates map of declared properties of this bean. This method analyzes the access types for
   * properties which may be chosen by annotation {@link Access} and then uses method
   * {@link #createProperty} for creation of proper property.
   *
   * @return Map of declared properties of this bean.
   */
  protected Map<String, Property<?>> createProperties() {
    Map<String, Property<?>> properties = new HashMap<String, Property<?>>();

    // access type for properties
    Access access = getAnnotation(Access.class);
    AccessType accessType = (access != null) ? access.value() : AccessType.PROPERTY;

    try {
      for (Field field : getType().getDeclaredFields()) {
        String propertyName = field.getName();

        // access type for actual property
        AccessType propertyAccessType = accessType;
        access = field.getAnnotation(Access.class);
        if (access != null) {
          propertyAccessType = access.value();
        }

        // create and store property
        Property<?> property = createProperty(propertyName, field.getType(), propertyAccessType);
        properties.put(propertyName, property);
      }
    }
    catch (Exception exc) {
      throw new BeanException("Cannot initialize bean properties", exc);
    }

    return properties;
  }

  /**
   * Creates property according to given {@link AccessType}. It returns {@link BasicProperty} for
   * {@link AccessType#PROPERTY} and {@link DirectAccessProperty} for {@link AccessType#FIELD}.
   *
   * @param propertyName the name of property.
   * @param propertyType the class type of property.
   * @param accessType the access type of property.
   * @return Created property according to given access type.
   */
  protected <T> Property<T> createProperty(String propertyName, Class<T> propertyType, AccessType accessType) {
    if (accessType == AccessType.PROPERTY) {
      return new BasicProperty<T>(beanType, propertyName);
    }

    return new DirectAccessProperty<T>(beanType, propertyName);
  }

  /**
   * Creates parent of this bean. Returns <code>null</code> if the parent of this bean class is
   * <code>null</code> or {@link Object}.
   *
   * @return Created parent of this bean or <code>null</code>.
   */
  protected Bean createParent() {
    Class<?> parentClass = getType().getSuperclass();
    if (parentClass != null && parentClass != Object.class) {
      return new JopBean(parentClass);
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public Bean getParent() {
    return parent;
  }

  /**
   * {@inheritDoc}
   */
  public boolean containsProperty(String propertyName) {
    for (Bean bean = this; bean != null; bean = bean.getParent()) {
      if (bean.containsDeclaredProperty(propertyName)) {
        return true;
      }
    }

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean containsDeclaredProperty(String propertyName) {
    return properties.containsKey(propertyName);
  }

  /**
   * Returns property with given name which may be inherited from parent classes. If property with
   * given name is not present the {@link BeanException} is thrown.</li>
   *
   * @param propertyName the name of property.
   * @return Declared property with given name.
   * @throws BeanException If property with given name is not present.
   */
  public Property<?> getProperty(String propertyName) throws BeanException {
    for (Bean bean = this; bean != null; bean = bean.getParent()) {
      if (bean.containsDeclaredProperty(propertyName)) {
        return bean.getDeclaredProperty(propertyName);
      }
    }

    throw new BeanException("Property " + propertyName + " was not found");
  }

  /**
   * Returns declared property of this bean with given name. If property with given name is not
   * present the {@link BeanException} is thrown.</li>
   *
   * @param propertyName the name of declared property.
   * @return Declared property with given name.
   * @throws BeanException If declared property with given name is not present.
   */
  public Property<?> getDeclaredProperty(String propertyName) throws BeanException {
    Property<?> property = properties.get(propertyName);
    if (property == null) {
      throw new BeanException("Property " + propertyName + " was not found");
    }

    return property;
  }

  /**
   * {@inheritDoc}
   */
  public List<Property<?>> getProperties() {
    List<Property<?>> properties = new ArrayList<Property<?>>();
    for (Bean bean = this; bean != null; bean = bean.getParent()) {
      properties.addAll(bean.getDeclaredProperties());
    }

    return properties;
  }

  /**
   * {@inheritDoc}
   */
  public List<Property<?>> getDeclaredProperties() {
    return new ArrayList<Property<?>>(properties.values());
  }

}
