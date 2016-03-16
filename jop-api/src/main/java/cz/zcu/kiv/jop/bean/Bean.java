package cz.zcu.kiv.jop.bean;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import cz.zcu.kiv.jop.property.Property;

/**
 * Interface which serves as abstraction for (POJO) objects. Each object is formed by properties
 * which can be accessed by their getters and setters or by direct access using reflection. Getters
 * or setters for accessing the properties can be non-public. Also in case of direct access the
 * field can have visibility modifier different to public.
 * <p>
 * Bean can be created only for classes which may be also abstract. The bean shouldn't be created
 * for interfaces, enumerations, annotations, arrays, etc. because they are not beans and usually
 * contains no properties. The bean creation also may be forbidden for {@link Object} class because
 * it contains only (native) methods.
 * <p>
 * Each bean should have access to all properties of bean class and also to all properties of parent
 * bean classes. In case that some property of bean class has same name like property of parent, it
 * should be overlapped. For accessing of properties are prepared methods:
 * <ul>
 * <li>{@link #containsProperty(String)} - returns information whatever the bean or their parents
 * contains property with given name.</li>
 * <li>{@link #containsDeclaredProperty(String)} - returns information whatever the bean contains
 * declared property with given name.</li>
 * <li>{@link #getProperties()} - returns all properties including the properties from parent beans
 * (classes).</li>
 * <li>{@link #getDeclaredProperties()} - returns all declared properties in bean class.</li>
 * <li>{@link #getProperty(String)} - returns property with given name which may be inherited from
 * parent classes. If property with given name is not present the {@link BeanException} may be
 * thrown.</li>
 * <li>{@link #getDeclaredProperty(String)} - returns property with given name from bean class. If
 * property with given name is not present the {@link BeanException} may be thrown.</li>
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Plain_Old_Java_Object">POJO</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface Bean extends AnnotatedElement {

  /**
   * Returns name of this bean. It can be full or simple name of bean class type.
   *
   * @return The bean name.
   */
  public String getName();

  /**
   * Returns class type of bean (bean class).
   *
   * @return The class type of bean.
   */
  public Class<?> getType();

  /**
   * Returns bean for parent class of this bean (class). It may return <code>null</code> value if
   * class type of this bean has no parent or if parent is {@link Object}.
   *
   * @return The bean for parent or <code>null</code>.
   */
  public Bean getParent();

  /**
   * Returns information whatever this bean contains the property with property with given name
   * which may be inherited from parent classes.
   *
   * @param propertyName the name of property.
   * @return <code>true</code> if this bean contains property with given name; <code>false</code>
   *         otherwise.
   */
  public boolean containsProperty(String propertyName);

  /**
   * Returns information whatever this bean contains the property with given name.
   *
   * @param propertyName the name of property.
   * @return <code>true</code> if this bean contains declared property with given name;
   *         <code>false</code> otherwise.
   */
  public boolean containsDeclaredProperty(String propertyName);

  /**
   * Returns property with given name which may be inherited from parent classes. If property with
   * given name is not present the {@link BeanException} may be thrown.</li>
   *
   * @param propertyName the name of property.
   * @return Declared property with given name.
   * @throws BeanException If property with given name is not present.
   */
  public Property<?> getProperty(String propertyName);

  /**
   * Returns declared property of this bean with given name. If property with given name is not
   * present the {@link BeanException} may be thrown.</li>
   *
   * @param propertyName the name of declared property.
   * @return Declared property with given name.
   * @throws BeanException If declared property with given name is not present.
   */
  public Property<?> getDeclaredProperty(String propertyName);

  /**
   * Returns list of properties including the inherited properties from parent beans (classes).
   *
   * @return List of properties.
   */
  public List<Property<?>> getProperties();

  /**
   * Returns list of declared properties of this bean.
   *
   * @return List of declared properties.
   */
  public List<Property<?>> getDeclaredProperties();

}
