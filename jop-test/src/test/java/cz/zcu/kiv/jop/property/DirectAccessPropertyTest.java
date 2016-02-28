package cz.zcu.kiv.jop.property;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of class {@link DirectAccessProperty}.
 *
 * @author Mr.FrAnTA
 */
public class DirectAccessPropertyTest {

  /**
   * Test of constructor of {@link DirectAccessProperty} for null value as declaring class.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullClass() {
    new DirectAccessProperty<Object>(null, "property");
  }

  /**
   * Test of constructor of {@link DirectAccessProperty} for null value as property name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullName() {
    new DirectAccessProperty<Object>(MockObject.class, null);
  }

  /**
   * Test of method {@link DirectAccessProperty#getGetter} for non-existing field.
   */
  @Test(expected = GetterNotFoundException.class)
  public void testGetGetterForNonExisting() throws Exception {
    DirectAccessProperty<Object> property = new DirectAccessProperty<Object>(MockObject.class, "property");
    property.getGetter();
  }

  /**
   * Test of method {@link DirectAccessProperty#getGetter} for existing field.
   */
  @Test
  public void testGetGetterForExisting() throws Exception {
    DirectAccessProperty<Object> property = new DirectAccessProperty<Object>(MockObject.class, MockObject.PROPERTY1);
    Getter<Object> getter = property.getGetter();

    Assert.assertNotNull(getter);
    // check equality of returned getters
    Assert.assertEquals(getter, property.getGetter());
  }

  /**
   * Test of method {@link DirectAccessProperty#getSetter} for non-existing field.
   */
  @Test(expected = SetterNotFoundException.class)
  public void testGetSetterForNonExisting() throws Exception {
    DirectAccessProperty<Object> property = new DirectAccessProperty<Object>(MockObject.class, "property");
    property.getSetter();
  }

  /**
   * Test of method {@link DirectAccessProperty#getSetter} for final field.
   */
  @Test(expected = SetterNotFoundException.class)
  public void testGetSetterForFinal() throws Exception {
    DirectAccessProperty<Object> property = new DirectAccessProperty<Object>(MockObject.class, MockObject.PROPERTY3);
    property.getSetter();
  }

  /**
   * Test of method {@link DirectAccessProperty#getSetter} for existing field.
   */
  @Test
  public void testGetSetterForExisting() throws Exception {
    DirectAccessProperty<Object> property = new DirectAccessProperty<Object>(MockObject.class, MockObject.PROPERTY1);
    Setter<Object> setter = property.getSetter();

    Assert.assertNotNull(setter);
    // check equality of returned setters
    Assert.assertEquals(setter, property.getSetter());
  }

}
