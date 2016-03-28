package cz.zcu.kiv.jop.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.annotation.Bar;
import cz.zcu.kiv.jop.annotation.ComplexAnnotation;
import cz.zcu.kiv.jop.annotation.Foo;
import cz.zcu.kiv.jop.annotation.FooImpl;
import cz.zcu.kiv.jop.annotation.Marker;

/**
 * Tests of helper static class {@link AnnotationUtils}.
 *
 * @author Mr.FrAnTA
 */
@Foo(0)
@Bar(1)
public class AnnotationUtilsTest {

  /** Helper field which is annotated by not-marked annotation. */
  @Foo(0)
  @Deprecated
  @Named("fieldWithNotMarked")
  private Object fieldWithNotMarked;

  /** Helper field which is annotated by marked annotation. */
  @Bar(0)
  @Deprecated
  @Named("fieldWithMarked")
  private Object fieldWithMarked;

  /**
   * Helper method which provides instance of annotation which marks the helper field with given
   * name.
   *
   * @param fieldName the helper field name.
   * @param annotation the annotation type which instance will be returned.
   * @return The annotation which marks the helper field with given name.
   */
  protected <T extends Annotation> T getAnnotationInstance(String fieldName, Class<? extends T> annotation) {
    try {
      Field field = getClass().getDeclaredField(fieldName);
      return field.getAnnotation(annotation);
    }
    catch (Exception exc) {
      throw new AssertionError();
    }
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Annotation, Class)} for null value
   * as annotation. Expected illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIsAnnotatedAnnotationForNullAnnotation() {
    AnnotationUtils.isAnnotatedAnnotation((Annotation)null, Marker.class);
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Annotation, Class)} for null value
   * as marker annotation. Expected illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIsAnnotatedAnnotationForNullMarker() {
    AnnotationUtils.isAnnotatedAnnotation(new FooImpl(0), null);
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Annotation, Class)} for not-marked
   * annotation.
   */
  @Test
  public void testIsAnnotatedAnnotationForUnmarked() {
    Annotation annotation = getAnnotationInstance("fieldWithNotMarked", Foo.class);
    Assert.assertFalse(AnnotationUtils.isAnnotatedAnnotation(annotation, Marker.class));
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Annotation, Class)} for marked
   * annotation.
   */
  @Test
  public void testIsAnnotatedAnnotationForMarked() {
    Annotation annotation = getAnnotationInstance("fieldWithMarked", Bar.class);
    Assert.assertTrue(AnnotationUtils.isAnnotatedAnnotation(annotation, Marker.class));
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Class, Class)} for null value as
   * annotation. Expected illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIsAnnotatedAnnotationTypeForNullAnnotation() {
    AnnotationUtils.isAnnotatedAnnotation((Class<? extends Annotation>)null, Marker.class);
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Class, Class)} for null value as
   * marker annotation. Expected illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIsAnnotatedAnnotationTypeForNullMarker() {
    AnnotationUtils.isAnnotatedAnnotation(Foo.class, null);
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Class, Class)} for not-marked
   * annotation type.
   */
  @Test
  public void testIsAnnotatedAnnotationTypeForUnmarked() {
    Assert.assertFalse(AnnotationUtils.isAnnotatedAnnotation(Foo.class, Marker.class));
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Class, Class)} for marked
   * annotation type.
   */
  @Test
  public void testIsAnnotatedAnnotationTypeForMarked() {
    Assert.assertTrue(AnnotationUtils.isAnnotatedAnnotation(Bar.class, Marker.class));
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotationPresent} for null value as element.
   * Expected illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIsAnnotatedAnnotationPresentForNullElement() {
    AnnotationUtils.isAnnotatedAnnotationPresent(null, Marker.class);
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotationPresent} for null value as marker
   * annotation type. Expected illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIsAnnotatedAnnotationPresentForNullAnnotation() {
    AnnotationUtils.isAnnotatedAnnotationPresent(getClass(), null);
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotationPresent} for element with not-marked
   * annotations.
   */
  @Test
  public void testIsAnnotatedAnnotationPresentForElementWithNotMarkedAnnotations() throws Throwable {
    Assert.assertFalse(AnnotationUtils.isAnnotatedAnnotationPresent(getClass().getDeclaredField("fieldWithNotMarked"), Marker.class));
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotationPresent} for element with marked
   * annotation.
   */
  @Test
  public void testIsAnnotatedAnnotationPresentForElementWithMarkedAnnotations() {
    Assert.assertTrue(AnnotationUtils.isAnnotatedAnnotationPresent(getClass(), Marker.class));
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotatedAnnotations} for null value as element.
   * Expected illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotatedAnnotationsForNullElement() {
    AnnotationUtils.getAnnotatedAnnotations(null, Marker.class);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotatedAnnotations} for null value as marker
   * annotation type. Expected illegal argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotatedAnnotationsForNullAnnotation() {
    AnnotationUtils.getAnnotatedAnnotations(getClass(), null);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotatedAnnotations} for element with not-marked
   * annotations.
   */
  @Test
  public void testGetAnnotatedAnnotationsForElementWithNotMarkedAnnotations() throws Throwable {
    Annotation[] expected = new Annotation[0];
    Assert.assertArrayEquals(expected, AnnotationUtils.getAnnotatedAnnotations(getClass().getDeclaredField("fieldWithNotMarked"), Marker.class));
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotatedAnnotations} for element with marked
   * annotation.
   */
  @Test
  public void testGetAnnotatedAnnotationsForElementWithMarkedAnnotations() {
    Class<?> clazz = getClass();
    Annotation[] annotations = new Annotation[] {clazz.getAnnotation(Bar.class)};
    Assert.assertArrayEquals(annotations, AnnotationUtils.getAnnotatedAnnotations(clazz, Marker.class));
  }

  // ---- Test of method getAnnotationProxy ------------------------------------

  @ComplexAnnotation
  private int fieldWithDefaults;

  @ComplexAnnotation(value = "null", bool = false, integers = {0, 1, 2}, strings = {})
  private int fieldWithCustoms;

  /**
   * Helper method for equality of basic attributes of two annotations.
   *
   * @param expected the expected annotation.
   * @param annotation the tested annotation.
   */
  private static void assertEquals(Annotation expected, Annotation annotation) {
    Assert.assertEquals(expected.annotationType(), annotation.annotationType());
    Assert.assertEquals(annotation, annotation); // a == a
    Assert.assertEquals(annotation, expected); // a == e
    Assert.assertEquals(expected, annotation); // e == a
    Assert.assertEquals(expected.hashCode(), annotation.hashCode());
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for null values. Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationProxyForNulls() {
    AnnotationUtils.getAnnotationProxy(null, null);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for null value as annotation type.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationProxyForNullAnnotation() {
    AnnotationUtils.getAnnotationProxy(null, new HashMap<String, Object>());
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for creation of proxy with null
   * parameters - default values will be used. It will be checked with annotation for
   * {@link #fieldWithDefaults} which contains the annotation with default values.
   */
  @Test
  public void testGetAnnotationProxyForDefaultValuesWithNullParams() {
    ComplexAnnotation expected = getAnnotationInstance("fieldWithDefaults", ComplexAnnotation.class);
    ComplexAnnotation annotation = AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, null);

    Assert.assertEquals(expected.value(), annotation.value());
    Assert.assertEquals(expected.bool(), annotation.bool());
    Assert.assertArrayEquals(expected.integers(), annotation.integers());
    Assert.assertArrayEquals(expected.strings(), annotation.strings());
    assertEquals(expected, annotation);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for creation of proxy with empty
   * parameters - default values will be used. It will be checked with annotation for
   * {@link #fieldWithDefaults} which contains the annotation with default values.
   */
  @Test
  public void testGetAnnotationProxyForDefaultValuesWithEmptyParams() {
    ComplexAnnotation expected = getAnnotationInstance("fieldWithDefaults", ComplexAnnotation.class);
    ComplexAnnotation annotation = AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, new HashMap<String, Object>());

    Assert.assertEquals(expected.value(), annotation.value());
    Assert.assertEquals(expected.bool(), annotation.bool());
    Assert.assertArrayEquals(expected.integers(), annotation.integers());
    Assert.assertArrayEquals(expected.strings(), annotation.strings());
    assertEquals(expected, annotation);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for creation of proxy with custom
   * parameters. It will be checked with annotation for {@link #fieldWithCustoms} which contains the
   * annotation with custom parameters.
   */
  @Test
  public void testGetAnnotationProxyForCustomValuesWithParams() {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("value", "null");
    params.put("bool", false);
    params.put("integers", new int[] {0, 1, 2});
    params.put("strings", new String[0]);

    ComplexAnnotation expected = getAnnotationInstance("fieldWithCustoms", ComplexAnnotation.class);
    ComplexAnnotation annotation = AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, params);

    Assert.assertEquals(expected.value(), annotation.value());
    Assert.assertEquals(expected.bool(), annotation.bool());
    Assert.assertArrayEquals(expected.integers(), annotation.integers());
    Assert.assertArrayEquals(expected.strings(), annotation.strings());
    assertEquals(expected, annotation);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for creation of proxy with custom
   * parameters. It will be checked with annotation for {@link #fieldWithCustoms} which contains the
   * annotation with custom parameters. Also checks unboxing for primitive types.
   */
  @Test
  public void testGetAnnotationProxyForCustomValuesWithParamsAndBoxing() {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("value", "null");
    params.put("bool", new Boolean(false));
    params.put("integers", new Integer[] {0, 1, 2});
    params.put("strings", new String[0]);

    ComplexAnnotation expected = getAnnotationInstance("fieldWithCustoms", ComplexAnnotation.class);
    ComplexAnnotation annotation = AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, params);

    Assert.assertEquals(expected.value(), annotation.value());
    Assert.assertEquals(expected.bool(), annotation.bool());
    Assert.assertArrayEquals(expected.integers(), annotation.integers());
    Assert.assertArrayEquals(expected.strings(), annotation.strings());
    assertEquals(expected, annotation);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for creation of proxy for parameters
   * with missing value (or with the <code>null</code> value). Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationProxyForMissingParameter() {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("value", null);
    AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, params);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for incompatible value type of some
   * parameter. Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationProxyForIncompatibleParameter() {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("bool", 1);
    AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, params);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for incompatible value type of some
   * parameter. The parameter is not array but was given array. Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationProxyForIncompatibleNotArrayParameter() {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("bool", new boolean[0]);
    AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, params);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for incompatible value type of some
   * parameter. The parameter is array but was not given array. Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationProxyForIncompatibleArrayParameter() {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("integers", "value");
    AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, params);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotationProxy} for incompatible value type of some
   * parameter. The parameter is array but was given array of incorrect type. Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationProxyForIncompatibleArrayParameter2() {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("integers", new String[0]);
    AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, params);
  }

  /**
   * Test of immutability of created annotation proxy in method
   * {@link AnnotationUtils#getAnnotationProxy}.
   */
  @Test
  public void testGetAnnotationProxyImmutability() {
    int[] expected = new int[] {1, 2, 3};
    int[] integers = expected.clone();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("integers", integers);

    ComplexAnnotation annotation = AnnotationUtils.getAnnotationProxy(ComplexAnnotation.class, params);

    integers[1] = 4; // change one value

    Assert.assertArrayEquals(expected, annotation.integers());

    // returned value - change it
    integers = annotation.integers();
    integers[1] = 4; // change one value

    Assert.assertArrayEquals(expected, annotation.integers());
  }

}
