package cz.zcu.kiv.jop.property;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of class {@link BasicProperty}.
 *
 * @author Mr.FrAnTA
 */
public class BasicPropertyTest {

  /**
   * Test of constructor of {@link BasicProperty} for null value as declaring class.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullClass() {
    new BasicProperty<Object>(null, "property");
  }

  /**
   * Test of constructor of {@link BasicProperty} for null value as property name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullName() {
    new BasicProperty<Object>(MockObject.class, null);
  }

  /**
   * Test of method {@link BasicProperty#getGetter} for property with non-existing getter.
   */
  @Test(expected = GetterNotFoundException.class)
  public void testGetGetterForNonExisting() throws Exception {
    BasicProperty<Object> property = new BasicProperty<Object>(MockObject.class, MockObject.PROPERTY4);
    property.getGetter();
  }

  /**
   * Test of method {@link BasicProperty#getGetter} for property with existing getter.
   */
  @Test
  public void testGetGetterForExisting() throws Exception {
    BasicProperty<Object> property = new BasicProperty<Object>(MockObject.class, MockObject.PROPERTY1);
    Getter<Object> getter = property.getGetter();

    Assert.assertNotNull(getter);
    // check equality of returned getters
    Assert.assertEquals(getter, property.getGetter());
  }

  /**
   * Test of method {@link BasicProperty#getSetter} for property with non-existing setter.
   */
  @Test(expected = SetterNotFoundException.class)
  public void testGetSetterForNonExisting() throws Exception {
    BasicProperty<Object> property = new BasicProperty<Object>(MockObject.class, MockObject.PROPERTY3);
    property.getSetter();
  }

  /**
   * Test of method {@link BasicProperty#getSetter} for property with existing setter.
   */
  @Test
  public void testGetSetterForExisting() throws Exception {
    BasicProperty<Object> property = new BasicProperty<Object>(MockObject.class, MockObject.PROPERTY1);
    Setter<Object> setter = property.getSetter();

    Assert.assertNotNull(setter);
    // check equality of returned setters
    Assert.assertEquals(setter, property.getSetter());
  }

  /**
   * Text of method {@link BasicProperty#getGetterMethod} for non-existing property.
   */
  @Test
  public void testGetGetterMethodForNonExistingProperty() throws Exception {
    Assert.assertNull(BasicProperty.getGetterMethod(MockSuperObject.class, "property"));
  }

  /**
   * Text of method {@link BasicProperty#getGetterMethod} for existing boolean property.
   */
  @Test
  public void testGetGetterMethodForExistingBooleanProperty() throws Exception {
    Assert.assertEquals(MockSuperObject.class.getDeclaredMethod("isProperty1"),
        BasicProperty.getGetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1));
  }

  /**
   * Text of method {@link BasicProperty#getGetterMethod} for existing property.
   */
  @Test
  public void testGetGetterMethodForExistingProperty() throws Exception {
    Assert.assertEquals(MockSuperObject.class.getDeclaredMethod("getProperty2"),
        BasicProperty.getGetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY2));
  }

  /**
   * Text of method {@link BasicProperty#getGetterOrNull} for non-existing getter.
   */
  @Test
  public void testGetGetterOrNullForNonExistingProperty() throws Exception {
    Assert.assertNull(BasicProperty.getGetterOrNull(MockObject.class, "property"));
  }

  /**
   * Text of method {@link BasicProperty#getGetterOrNull} for existing getters.
   */
  @Test
  public void testGetGetterOrNullForExistingProperty() throws Exception {
    Assert.assertNotNull(BasicProperty.getGetterOrNull(MockObject.class, MockSuperObject.PROPERTY1));
    Assert.assertNotNull(BasicProperty.getGetterOrNull(MockObject.class, MockSuperObject.PROPERTY2));
    Assert.assertNotNull(BasicProperty.getGetterOrNull(MockObject.class, MockObject.PROPERTY3));
  }

  /**
   * Text of method {@link BasicProperty#getGetterOrNull} for existing getters.
   */
  @Test
  public void testGetGetterOrNullFromInterface() throws Exception {
    Assert.assertNotNull(BasicProperty.getGetterOrNull(MockObject.class, MockInterface.PROPERTY_I));
  }

  /**
   * Text of method {@link BasicProperty#getSetterOrNull} for non-existing property.
   */
  @Test
  public void testGetSetterMethodForNonExistingProperty() throws Exception {
    Assert.assertNull(BasicProperty.getSetterMethod(MockSuperObject.class, "property"));
  }

  /**
   * Text of method {@link BasicProperty#getSetterOrNull} for existing boolean property.
   */
  @Test
  public void testGetSetterMethodForExistingBooleanProperty() throws Exception {
    Assert.assertEquals(MockSuperObject.class.getDeclaredMethod("setProperty1", Boolean.TYPE),
        BasicProperty.getSetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1));
  }

  /**
   * Text of method {@link BasicProperty#getSetterOrNull} for existing property.
   */
  @Test
  public void testGetSetterMethodForExistingProperty() throws Exception {
    Assert.assertEquals(MockSuperObject.class.getDeclaredMethod("setProperty2", Integer.TYPE),
        BasicProperty.getSetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY2));
  }

  /**
   * Text of method {@link BasicProperty#getSetterOrNull} for non-existing setter.
   */
  @Test
  public void testGetSetterOrNullForNonExistingProperty() throws Exception {
    Assert.assertNull(BasicProperty.getSetterOrNull(MockObject.class, "property"));
  }

  /**
   * Text of method {@link BasicProperty#getSetterOrNull} for existing setters.
   */
  @Test
  public void testGetSetterOrNullForExistingProperty() throws Exception {
    Assert.assertNotNull(BasicProperty.getSetterOrNull(MockObject.class, MockSuperObject.PROPERTY1));
    Assert.assertNotNull(BasicProperty.getSetterOrNull(MockObject.class, MockSuperObject.PROPERTY2));
    Assert.assertNotNull(BasicProperty.getSetterOrNull(MockObject.class, MockObject.PROPERTY4));
  }

  /**
   * Text of method {@link BasicProperty#getSetterOrNull} for existing getters.
   */
  @Test
  public void testGetSetterOrNullFromInterface() throws Exception {
    Assert.assertNotNull(BasicProperty.getSetterOrNull(MockObject.class, MockInterface.PROPERTY_I));
  }

}
