package cz.zcu.kiv.jop.property;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.property.DirectAccessProperty.DirectSetter;

/**
 * Test of class {@link DirectSetter}.
 *
 * @author Mr.FrAnTA
 */
public class DirectAccessSetterTest {

  /**
   * Test of constructor of {@link DirectSetter} class for given null value as property name.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullName() throws Exception {
    new DirectAccessProperty.DirectSetter<Object>(null, MockSuperObject.class.getDeclaredField(MockSuperObject.PROPERTY1));
  }

  /**
   * Test of constructor of {@link DirectSetter} class for given null value as setter member.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullMethod() throws Exception {
    new DirectAccessProperty.DirectSetter<Object>("property", null);
  }

  /**
   * Test of setters for attributes of {@link DirectSetter}.
   */
  @Test
  public void testDirectSetterAttributes() throws Exception {
    Field setterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectSetter<?> setter = new DirectAccessProperty.DirectSetter<Boolean>(MockSuperObject.PROPERTY1, setterField);

    /*----- Verify attributes -----*/
    Assert.assertEquals(MockSuperObject.class, setter.getDeclaringClass());
    Assert.assertEquals(MockObject.PROPERTY1, setter.getPropertyName());
    Assert.assertEquals(Boolean.TYPE, setter.getPropertyType());
    Assert.assertEquals(setterField, setter.getMember());
    Assert.assertEquals(null, setter.getMethod());
    Assert.assertEquals(null, setter.getMethodName());
  }

  /**
   * Test of method {@link DirectSetter#set} for primitive type.
   */
  @Test
  public void testDirectSetterSet() throws Exception {
    Field setterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectSetter<Boolean> setter = new DirectAccessProperty.DirectSetter<Boolean>(MockSuperObject.PROPERTY1, setterField);

    MockObject mockObject = new MockObject();
    // check unset value
    Assert.assertEquals(false, mockObject.isProperty1());

    // set value
    setter.set(mockObject, true);
    // check set value
    Assert.assertEquals(true, mockObject.isProperty1());

    // set value
    setter.set(mockObject, false);
    // check set value
    Assert.assertEquals(false, mockObject.isProperty1());
  }

  /**
   * Test of method {@link DirectSetter#set} for object type.
   */
  @Test
  public void testDirectSetterSetObject() throws Exception {
    Field setterField = DirectAccessProperty.getField(MockObject.class, MockObject.PROPERTY_I);
    DirectAccessProperty.DirectSetter<Object> setter = new DirectAccessProperty.DirectSetter<Object>(MockSuperObject.PROPERTY_I, setterField);

    MockObject mockObject = new MockObject();
    final String value = "value";

    // check unset value
    Assert.assertNull(mockObject.getpropertyI());

    // set value
    setter.set(mockObject, value);
    // check set value
    Assert.assertEquals(value, mockObject.getpropertyI());

    // set value
    setter.set(mockObject, null);
    // check set value
    Assert.assertNull(mockObject.getpropertyI());
  }

  /**
   * Test of method {@link DirectSetter#set} for null as object instance (call of static setter
   * method). Expected {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testDirectSetterSetForNullObject() throws Exception {
    Field setterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectSetter<Boolean> setter = new DirectAccessProperty.DirectSetter<Boolean>(MockSuperObject.PROPERTY1, setterField);
    setter.set(null, Boolean.TRUE);
  }

  /**
   * Test of method {@link DirectSetter#set} for null as value. Expected
   * {@link PropertyAccessException} because of null value for primitive type.
   */
  @Test(expected = PropertyAccessException.class)
  public void testDirectSetterSetForNullValue() throws Exception {
    Field setterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectSetter<Boolean> setter = new DirectAccessProperty.DirectSetter<Boolean>(MockSuperObject.PROPERTY1, setterField);
    setter.set(new MockObject(), null);
  }

  /**
   * Test of method {@link DirectSetter#set} for object instance which isn't contains the method for
   * which was setter created. Expected {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testDirectSetterSetForInvalidOwner() throws Exception {
    Field setterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectSetter<Object> setter = new DirectAccessProperty.DirectSetter<Object>(MockSuperObject.PROPERTY1, setterField);
    setter.set(new Object(), new Object());
  }

  /**
   * Test of method {@link DirectSetter#set} for value which isn't compatible with value type of
   * property. Expected {@link PropertyAccessException}.
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Test(expected = PropertyAccessException.class)
  public void testDirectSetterSetForInvalidValue() throws Exception {
    Field setterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectSetter setter = new DirectAccessProperty.DirectSetter<Boolean>(MockSuperObject.PROPERTY1, setterField);
    setter.set(new MockObject(), new Object());
  }

}
