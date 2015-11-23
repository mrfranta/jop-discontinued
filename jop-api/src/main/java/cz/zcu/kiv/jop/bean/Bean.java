package cz.zcu.kiv.jop.bean;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import cz.zcu.kiv.jop.property.Property;

/**
 * Interface which serves as abstraction for (POJO) objects. Each object is
 * formed by properties which can be accessed by their getters and setters or by
 * direct access using reflection. Getters or setters for accessing the
 * properties can be non-public. Also in case of direct access the field can
 * have visibility modifier different to public.
 * <p>
 * Bean should be created for classes which may be abstract. The bean shouldn't
 * be created for interfaces, enumerations, annotations, arrays, etc. because
 * they are not beans and usually contains no properties.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Plain_Old_Java_Object">POJO</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface Bean extends AnnotatedElement {

  /**
   * Returns name of this bean. It can be full name of bean class type.
   *
   * @return The bean name.
   */
  public String getName();

  /**
   * Returns class type of bean.
   *
   * @return The class type of bean.
   */
  public Class<?> getType();

  /**
   * Returns bean for parent class of class type of this bean.
   *
   * @return The bean for parent.
   */
  public Bean getParent();

  /**
   * Returns declared property of this bean with given name. If this bean
   * doesn't contain property with given name, the <code>null</code> value will
   * be returned.
   *
   * @param propertyName the name of declared property.
   * @return Declared property with given name or <code>null</code>.
   */
  public Property<?> getProperty(String propertyName);

  /**
   * Returns list of declared properties of this bean.
   *
   * @return List of declared properties.
   */
  public List<Property<?>> getProperties();

}
