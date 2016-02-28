package cz.zcu.kiv.jop.property;

/**
 * Mock object which extends {@link MockSuperObject} for testing of properties.
 *
 * @author Mr.FrAnTA
 */
public class MockObject extends MockSuperObject {

  /** name of 3rd property. */
  public static final String PROPERTY3 = "property3";

  /** name of 4th property. */
  public static final String PROPERTY4 = "property4";

  /** 3rd property: private. */
  private final double property3 = 0.0;
  /** 4th property: default. */
  String property4;

  /** 5th property: public, from interface. */
  public Object propertyI;

  /**
   * Getter for 3rd property.
   *
   * @return Value of 3rd property.
   */
  @SuppressWarnings("unused")
  private double getProperty3() {
    return property3;
  }

  /**
   * Setter for 4th property.
   *
   * @param property4 the value to set.
   */
  void setProperty4(String property4) {
    this.property4 = property4;
  }

  /**
   * {@inheritDoc}
   */
  public Object getpropertyI() {
    return propertyI;
  }

  /**
   * {@inheritDoc}
   */
  public void setpropertyI(Object propertyI) {
    this.propertyI = propertyI;
  }

}
