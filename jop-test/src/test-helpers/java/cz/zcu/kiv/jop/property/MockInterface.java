package cz.zcu.kiv.jop.property;

/**
 * Mock interface for testing of properties.
 *
 * @author Mr.FrAnTA
 */
public interface MockInterface {

  /** name of Xth property (property from interface). */
  public String PROPERTY_I = "propertyI";

  /**
   * Getter for Xth property (property from interface). Also getter has old-school name.
   *
   * @return Value of Xth property.
   */
  public Object getpropertyI();

  /**
   * Setter for Xth property. Also setter has old-school name.
   *
   * @param propertyI the value to set.
   */
  public void setpropertyI(Object propertyI);

}
