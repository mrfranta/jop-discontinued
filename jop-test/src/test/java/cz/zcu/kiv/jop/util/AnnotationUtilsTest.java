package cz.zcu.kiv.jop.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.inject.Named;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.annotation.Bar;
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
  protected Annotation getAnnotationInstnace(String fieldName, Class<? extends Annotation> annotation) {
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
    Annotation annotation = getAnnotationInstnace("fieldWithNotMarked", Foo.class);
    Assert.assertFalse(AnnotationUtils.isAnnotatedAnnotation(annotation, Marker.class));
  }

  /**
   * Test of method {@link AnnotationUtils#isAnnotatedAnnotation(Annotation, Class)} for marked
   * annotation.
   */
  @Test
  public void testIsAnnotatedAnnotationForMarked() {
    Annotation annotation = getAnnotationInstnace("fieldWithMarked", Bar.class);
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
    AnnotationUtils.getAnnotatedAnnotations(getClass().getDeclaredField("fieldWithNotMarked"), Marker.class);
  }

  /**
   * Test of method {@link AnnotationUtils#getAnnotatedAnnotations} for element with marked
   * annotation.
   */
  @Test
  public void testGetAnnotatedAnnotationsForElementWithMarkedAnnotations() {
    Class<?> clazz = getClass();
    Annotation[] annotations = new Annotation[] {clazz.getAnnotation(Bar.class)};
    Assert.assertArrayEquals(annotations, AnnotationUtils.getAnnotatedAnnotations(getClass(), Marker.class));
  }

}
