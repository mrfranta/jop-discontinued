package cz.zcu.kiv.jop.property;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.property.BasicProperty.BasicSetter;

/**
 * Test of class {@link BasicSetter}.
 *
 * @author Mr.FrAnTA
 */
public class BasicSetterTest {

  /**
   * Test of constructor of {@link BasicSetter} class for given null value as property name.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullName() throws Exception {
    new BasicProperty.BasicSetter<Object>(null, MockSuperObject.class.getDeclaredMethod("setProperty1", Boolean.TYPE));
  }

  /**
   * Test of constructor of {@link BasicSetter} class for given null value as setter member.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullMethod() throws Exception {
    new BasicProperty.BasicSetter<Object>("property", null);
  }

  /**
   * Test of setters for attributes of {@link BasicSetter}.
   */
  @Test
  public void testBasicSetterAttributes() {
    Method setterMethod = BasicProperty.getSetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicSetter<?> setter = new BasicProperty.BasicSetter<Boolean>(MockSuperObject.PROPERTY1, setterMethod);

    /*----- Verify attributes -----*/
    Assert.assertEquals(MockSuperObject.class, setter.getDeclaringClass());
    Assert.assertEquals(MockObject.PROPERTY1, setter.getPropertyName());
    Assert.assertEquals(Boolean.TYPE, setter.getType());
    Assert.assertEquals(setterMethod, setter.getMember());
    Assert.assertEquals(setterMethod, setter.getMethod());
    Assert.assertEquals("setProperty1", setter.getMethodName());
  }

  /**
   * Test of method {@link BasicSetter#set} for primitive type.
   */
  @Test
  public void testBasicSetterSet() throws Exception {
    Method setterMethod = BasicProperty.getSetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicSetter<Boolean> setter = new BasicProperty.BasicSetter<Boolean>(MockSuperObject.PROPERTY1, setterMethod);

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
   * Test of method {@link BasicSetter#set} for object type.
   */
  @Test
  public void testBasicSetterSetObject() throws Exception {
    Method setterMethod = BasicProperty.getSetterMethod(MockObject.class, MockObject.PROPERTY_I);
    BasicProperty.BasicSetter<Object> setter = new BasicProperty.BasicSetter<Object>(MockSuperObject.PROPERTY_I, setterMethod);

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
   * Test of method {@link BasicSetter#set} for null as object instance (call of static setter
   * method). Expected {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testBasicSetterSetForNullObject() throws Exception {
    Method setterMethod = BasicProperty.getSetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicSetter<Boolean> setter = new BasicProperty.BasicSetter<Boolean>(MockSuperObject.PROPERTY1, setterMethod);
    setter.set(null, Boolean.TRUE);
  }

  /**
   * Test of method {@link BasicSetter#set} for null as value. Expected
   * {@link PropertyAccessException} because of null value for primitive type.
   */
  @Test(expected = PropertyAccessException.class)
  public void testBasicSetterSetForNullValue() throws Exception {
    Method setterMethod = BasicProperty.getSetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicSetter<Boolean> setter = new BasicProperty.BasicSetter<Boolean>(MockSuperObject.PROPERTY1, setterMethod);
    setter.set(new MockObject(), null);
  }

  /**
   * Test of method {@link BasicSetter#set} for object instance which isn't contains the method for
   * which was setter created. Expected {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testBasicSetterSetForInvalidOwner() throws Exception {
    Method setterMethod = BasicProperty.getSetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicSetter<Object> setter = new BasicProperty.BasicSetter<Object>(MockSuperObject.PROPERTY1, setterMethod);
    setter.set(new Object(), new Object());
  }

  /**
   * Test of method {@link BasicSetter#set} for value which isn't compatible with value type of
   * property. Expected {@link PropertyAccessException}.
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Test(expected = PropertyAccessException.class)
  public void testBasicSetterSetForInvalidValue() throws Exception {
    Method setterMethod = BasicProperty.getSetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicSetter setter = new BasicProperty.BasicSetter<Boolean>(MockSuperObject.PROPERTY1, setterMethod);
    setter.set(new MockObject(), new Object());
  }

  /**
   * Test of method {@link BasicSetter#set} for method which throws exception. Expected
   * {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testBasicSetterSetWithException() throws Exception {
    Method setterMethod = BasicProperty.getSetterMethod(ExceptionMockObject.class, ExceptionMockObject.PROPERTY_E);
    BasicProperty.BasicSetter<Object> setter = new BasicProperty.BasicSetter<Object>(ExceptionMockObject.PROPERTY_E, setterMethod);
    setter.set(new ExceptionMockObject(), new Object());
  }

}
