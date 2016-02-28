package cz.zcu.kiv.jop.property;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.annotation.Bar;
import cz.zcu.kiv.jop.annotation.Foo;

/**
 * Test of class {@link AbstractProperty}.
 *
 * @author Mr.FrAnTA
 */
public class AbstractPropertyTest {

  /**
   * Test of constructor of {@link AbstractProperty} for null value as declaring class.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullClass() {
    new AbstractPropertyMock<Object>(null, "property");
  }

  /**
   * Test of constructor of {@link AbstractProperty} for null value as property name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorForNullName() {
    new AbstractPropertyMock<Object>(MockObject.class, null);
  }
  
  /**
   * Test of methods {@link AbstractProperty#getDeclaringClass()},
   * {@link AbstractProperty#getName()} and {@link AbstractProperty#getField()} for not-existing
   * property.
   */
  @Test(expected = PropertyNotFoundException.class)
  public void testGettersForNotExistingProperty() throws Exception {
    AbstractPropertyMock<?> property = new AbstractPropertyMock<Object>(MockObject.class, "property");
    Assert.assertEquals(MockObject.class, property.getDeclaringClass());
    Assert.assertEquals("property", property.getName());
    Assert.assertEquals(MockSuperObject.class.getDeclaredField(MockSuperObject.PROPERTY1), property.getField());
  }

  /**
   * Test of methods {@link AbstractProperty#getDeclaringClass()},
   * {@link AbstractProperty#getName()} and {@link AbstractProperty#getField()} for existing
   * property.
   */
  @Test
  public void testGettersForExistingProperty() throws Exception {
    AbstractPropertyMock<?> property = new AbstractPropertyMock<Object>(MockObject.class, "property1");
    Assert.assertEquals(MockObject.class, property.getDeclaringClass());
    Assert.assertEquals("property1", property.getName());
    Assert.assertEquals(MockSuperObject.class.getDeclaredField(MockSuperObject.PROPERTY1), property.getField());
  }

  /**
   * Test of methods from interface {@link AbstractProperty#getAnnotation} for not-existing
   * property. Expected {@link PropertyRuntimeException}.
   */
  @Test(expected = PropertyRuntimeException.class)
  public void testGetAnnotationForNotExistingProperty() throws Exception {
    AbstractPropertyMock<?> property = new AbstractPropertyMock<Object>(MockObject.class, "property");
    property.getAnnotation(Foo.class);
  }

  /**
   * Test of methods from interface {@link AbstractProperty#isAnnotationPresent} for not-existing
   * property. Expected {@link PropertyRuntimeException}.
   */
  @Test(expected = PropertyRuntimeException.class)
  public void testIsAnnotationPresentForNotExistingProperty() throws Exception {
    AbstractPropertyMock<?> property = new AbstractPropertyMock<Object>(MockObject.class, "property");
    property.isAnnotationPresent(Foo.class);
  }

  /**
   * Test of methods from interface {@link AbstractProperty#getAnnotations} for not-existing
   * property. Expected {@link PropertyRuntimeException}.
   */
  @Test(expected = PropertyRuntimeException.class)
  public void testGetAnnotationsForNotExistingProperty() throws Exception {
    AbstractPropertyMock<?> property = new AbstractPropertyMock<Object>(MockObject.class, "property");
    property.getAnnotations();
  }

  /**
   * Test of methods from interface {@link AbstractProperty#getDeclaredAnnotations} for not-existing
   * property. Expected {@link PropertyRuntimeException}.
   */
  @Test(expected = PropertyRuntimeException.class)
  public void testGetDeclaredAnnotationsForNotExistingProperty() throws Exception {
    AbstractPropertyMock<?> property = new AbstractPropertyMock<Object>(MockObject.class, "property");
    property.getDeclaredAnnotations();
  }

  /**
   * Test of methods from interface {@link AnnotatedElement} for existing property.
   */
  @Test
  public void testAnnotatedElementMethodsForExistingProperty() throws Exception {
    AbstractPropertyMock<?> property = new AbstractPropertyMock<Object>(MockObject.class, "property1");
    Field field = MockSuperObject.class.getDeclaredField(MockSuperObject.PROPERTY1);

    Assert.assertEquals(field.getAnnotation(Foo.class), property.getAnnotation(Foo.class));
    Assert.assertEquals(field.getAnnotation(Bar.class), property.getAnnotation(Bar.class));
    Assert.assertEquals(field.isAnnotationPresent(Foo.class), property.isAnnotationPresent(Foo.class));
    Assert.assertEquals(field.isAnnotationPresent(Foo.class), property.isAnnotationPresent(Foo.class));
    Assert.assertArrayEquals(field.getAnnotations(), property.getAnnotations());
    Assert.assertArrayEquals(field.getDeclaredAnnotations(), property.getDeclaredAnnotations());
  }
  
  /**
   * Test of method {@link AbstractProperty#getField(Class, String)} for null class. Expected
   * {@link PropertyNotFoundException}.
   */
  @Test(expected = PropertyNotFoundException.class)
  public void testGetFieldForNullClass() throws PropertyNotFoundException {
    AbstractPropertyMock.getField(null, "property");
  }

  /**
   * Test of method {@link AbstractProperty#getField(Class, String)} for non-existing property.
   * Expected {@link PropertyNotFoundException}.
   */
  @Test(expected = PropertyNotFoundException.class)
  public void testGetFieldForNonExistingField() throws PropertyNotFoundException {
    AbstractPropertyMock.getField(MockObject.class, "property");
  }

  /**
   * Test of method {@link AbstractProperty#getField(Class, String)} for existing property.
   */
  @Test
  public void testGetFieldForExistingField() throws Exception {
    Field property1 = AbstractPropertyMock.getField(MockObject.class, "property1");
    Field property2 = AbstractPropertyMock.getField(MockObject.class, "property2");
    Field property3 = AbstractPropertyMock.getField(MockObject.class, "property3");
    Field property4 = AbstractPropertyMock.getField(MockObject.class, "property4");

    Assert.assertEquals(MockSuperObject.class.getDeclaredField(MockSuperObject.PROPERTY1), property1);
    Assert.assertEquals(MockSuperObject.class.getDeclaredField(MockSuperObject.PROPERTY2), property2);
    Assert.assertEquals(MockObject.class.getDeclaredField(MockObject.PROPERTY3), property3);
    Assert.assertEquals(MockObject.class.getDeclaredField(MockObject.PROPERTY4), property4);
  }


  /**
   * Mock object for abstract property.
   *
   * @author Mr.FrAnTA
   *
   * @param <T> Declared class type of property.
   */
  private static class AbstractPropertyMock<T> extends AbstractProperty<T> {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 20160225L;

    /**
     * Constructs an mocked property.
     *
     * @param declaringClass the class type of a property owner.
     * @param propertyName the name of property.
     */
    public AbstractPropertyMock(Class<?> declaringClass, String propertyName) {
      super(declaringClass, propertyName);
    }

    /**
     * @return null, won't be tested.
     */
    @Override
    protected Getter<T> createGetter() throws GetterNotFoundException {
      return null;
    }

    /**
     * @return null, won't be tested.
     */
    @Override
    protected Setter<T> createSetter() throws SetterNotFoundException {
      return null;
    }

  }
}
