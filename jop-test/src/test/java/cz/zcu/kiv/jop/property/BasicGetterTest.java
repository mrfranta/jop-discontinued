package cz.zcu.kiv.jop.property;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.property.BasicProperty.BasicGetter;

/**
 * Test of class {@link BasicGetter}.
 *
 * @author Mr.FrAnTA
 */
public class BasicGetterTest {

  /**
   * Test of constructor of {@link BasicGetter} class for given null value as property name.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullName() throws Exception {
    new BasicProperty.BasicGetter<Object>(null, MockSuperObject.class.getDeclaredMethod("isProperty1"));
  }

  /**
   * Test of constructor of {@link BasicGetter} class for given null value as getter member.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullMethod() throws Exception {
    new BasicProperty.BasicGetter<Object>("property", null);
  }

  /**
   * Test of getters for attributes of {@link BasicGetter}.
   */
  @Test
  public void testBasicGetterAttributes() {
    Method getterMethod = BasicProperty.getGetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicGetter<?> getter = new BasicProperty.BasicGetter<Boolean>(MockSuperObject.PROPERTY1, getterMethod);

    /*----- Verify attributes -----*/
    Assert.assertEquals(MockSuperObject.class, getter.getDeclaringClass());
    Assert.assertEquals(MockObject.PROPERTY1, getter.getPropertyName());
    Assert.assertEquals(Boolean.TYPE, getter.getType());
    Assert.assertEquals(getterMethod, getter.getMember());
    Assert.assertEquals(getterMethod, getter.getMethod());
    Assert.assertEquals("isProperty1", getter.getMethodName());
  }

  /**
   * Test of method {@link BasicGetter#get}.
   */
  @Test
  public void testBasicGetterGet() throws Exception {
    Method getterMethod = BasicProperty.getGetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicGetter<?> getter = new BasicProperty.BasicGetter<Boolean>(MockSuperObject.PROPERTY1, getterMethod);

    MockObject mockObject = new MockObject();
    Assert.assertEquals(false, getter.get(mockObject));

    mockObject.setProperty1(true);
    Assert.assertEquals(true, getter.get(mockObject));
  }

  /**
   * Test of method {@link BasicGetter#get} for null as object instance (call of static getter
   * method). Expected {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testBasicGetterGetForNullObject() throws Exception {
    Method getterMethod = BasicProperty.getGetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicGetter<?> getter = new BasicProperty.BasicGetter<Boolean>(MockSuperObject.PROPERTY1, getterMethod);
    getter.get(null);
  }

  /**
   * Test of method {@link BasicGetter#get} for object instance which isn't contains the method for
   * which was getter created. Expected {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testBasicGetterGetForInvalidObject() throws Exception {
    Method getterMethod = BasicProperty.getGetterMethod(MockSuperObject.class, MockSuperObject.PROPERTY1);
    BasicProperty.BasicGetter<?> getter = new BasicProperty.BasicGetter<Boolean>(MockSuperObject.PROPERTY1, getterMethod);
    getter.get(new Object());
  }

  /**
   * Test of method {@link BasicGetter#get} for method which throws exception. Expected
   * {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testBasicGetterGetWithException() throws Exception {
    Method getterMethod = BasicProperty.getGetterMethod(ExceptionMockObject.class, ExceptionMockObject.PROPERTY_E);
    BasicProperty.BasicGetter<?> getter = new BasicProperty.BasicGetter<Object>(ExceptionMockObject.PROPERTY_E, getterMethod);
    getter.get(new ExceptionMockObject());
  }

}
