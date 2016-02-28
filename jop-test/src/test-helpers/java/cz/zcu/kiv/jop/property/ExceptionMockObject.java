package cz.zcu.kiv.jop.property;

/**
 * Mock object which contains property which throws the exception.
 *
 * @author Mr.FrAnTA
 */
public class ExceptionMockObject extends MockObject {

  /** name of Xth property (property throwing exception). */
  public static final String PROPERTY_E = "propertyE";

  /**
   * Getter for Xth property (property throwing exception).
   *
   * @return Value of Xth property.
   */
  public Object getPropertyE() {
    throw new UnsupportedOperationException();
  }

  /**
   * Setter for Xth property.
   *
   * @param propertyE the value to set.
   */
  public void setPropertyE(Object property1) {
    throw new UnsupportedOperationException();
  }

}
