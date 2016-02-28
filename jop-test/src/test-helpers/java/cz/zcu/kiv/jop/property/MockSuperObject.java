package cz.zcu.kiv.jop.property;

import cz.zcu.kiv.jop.annotation.Bar;
import cz.zcu.kiv.jop.annotation.Foo;

/**
 * Mock object for testing of properties.
 *
 * @author Mr.FrAnTA
 */
public abstract class MockSuperObject implements MockInterface {

  /** name of 1st property. */
  public static final String PROPERTY1 = "property1";

  /** name of 2nd property. */
  public static final String PROPERTY2 = "property2";

  /** 1st property: public. */
  @Foo(0)
  public boolean property1;

  /** 2nd property: protected. */
  @Foo(0)
  @Bar(1)
  protected int property2;

  /**
   * Getter for 1st property.
   *
   * @return Value of 1st property.
   */
  public boolean isProperty1() {
    return property1;
  }

  /**
   * Setter for 1st property.
   *
   * @param property1 the value to set.
   */
  public void setProperty1(boolean property1) {
    this.property1 = property1;
  }

  /**
   * Getter for 2nd property.
   *
   * @return Value of 2nd property.
   */
  protected int getProperty2() {
    return property2;
  }

  /**
   * Setter for 2nd property.
   *
   * @param property2 the value to set.
   */
  protected void setProperty2(int property2) {
    this.property2 = property2;
  }

}
