package cz.zcu.kiv.jop.property;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.property.DirectAccessProperty.DirectGetter;

/**
 * Test of class {@link DirectGetter}.
 *
 * @author Mr.FrAnTA
 */
public class DirectGetterTest {

  /**
   * Test of constructor of {@link DirectGetter} class for given null value as property name.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullName() throws Exception {
    new DirectAccessProperty.DirectGetter<Object>(null, MockSuperObject.class.getDeclaredField(MockSuperObject.PROPERTY1));
  }

  /**
   * Test of constructor of {@link DirectGetter} class for given null value as getter member.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullMethod() throws Exception {
    new DirectAccessProperty.DirectGetter<Object>("property", null);
  }

  /**
   * Test of getters for attributes of {@link DirectGetter}.
   */
  @Test
  public void testDirectGetterAttributes() throws Exception {
    Field getterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectGetter<?> getter = new DirectAccessProperty.DirectGetter<Boolean>(MockSuperObject.PROPERTY1, getterField);

    /*----- Verify attributes -----*/
    Assert.assertEquals(MockSuperObject.class, getter.getDeclaringClass());
    Assert.assertEquals(MockObject.PROPERTY1, getter.getPropertyName());
    Assert.assertEquals(Boolean.TYPE, getter.getType());
    Assert.assertEquals(getterField, getter.getMember());
    Assert.assertEquals(null, getter.getMethod());
    Assert.assertEquals(null, getter.getMethodName());
  }

  /**
   * Test of method {@link DirectGetter#get}.
   */
  @Test
  public void testDirectGetterGet() throws Exception {
    Field getterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectGetter<?> getter = new DirectAccessProperty.DirectGetter<Boolean>(MockSuperObject.PROPERTY1, getterField);

    MockObject mockObject = new MockObject();
    Assert.assertEquals(false, getter.get(mockObject));

    mockObject.setProperty1(true);
    Assert.assertEquals(true, getter.get(mockObject));
  }

  /**
   * Test of method {@link DirectGetter#get} for null as object instance (call of static getter
   * method). Expected {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testDirectGetterGetForNullObject() throws Exception {
    Field getterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectGetter<?> getter = new DirectAccessProperty.DirectGetter<Boolean>(MockSuperObject.PROPERTY1, getterField);
    getter.get(null);
  }

  /**
   * Test of method {@link DirectGetter#get} for object instance which isn't contains the method for
   * which was getter created. Expected {@link PropertyAccessException}.
   */
  @Test(expected = PropertyAccessException.class)
  public void testDirectGetterGetForInvalidObject() throws Exception {
    Field getterField = DirectAccessProperty.getField(MockSuperObject.class, MockSuperObject.PROPERTY1);
    DirectAccessProperty.DirectGetter<?> getter = new DirectAccessProperty.DirectGetter<Boolean>(MockSuperObject.PROPERTY1, getterField);
    getter.get(new Object());
  }

}
