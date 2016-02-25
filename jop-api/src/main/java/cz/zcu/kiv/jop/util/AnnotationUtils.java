package cz.zcu.kiv.jop.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper static class for operations with annotations.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class AnnotationUtils {

  /**
   * Private constructor in combination with abstract modifier of this class makes it static.
   */
  private AnnotationUtils() {}

  /**
   * Returns information whether the given annotation type <code>annotation</code> is annotated by
   * given annotation type <code>annotatedBy</code>.
   *
   * @param annotation the annotation type to check.
   * @param annotatedBy the annotation type which may annotates the given annotation.
   * @return <code>true</code> if given annotation type <code>annotatedBy</code> annotates given
   *         annotation type <code>annotation</code>; <code>false</code> otherwise.
   */
  public static boolean isAnnotatedAnnotation(Annotation annotation, Class<? extends Annotation> annotatedBy) {
    return isAnnotatedAnnotation(annotation == null ? null : annotation.annotationType(), annotatedBy);
  }

  /**
   * Returns information whether the given annotation <code>annotation</code> is annotated by given
   * annotation type <code>annotatedBy</code>.
   *
   * @param annotation the annotation to check.
   * @param annotatedBy the annotation type which may annotates the given annotation.
   * @return <code>true</code> if given annotation type <code>annotatedBy</code> annotates given
   *         annotation <code>annotation</code>; <code>false</code> otherwise.
   */
  public static boolean isAnnotatedAnnotation(Class<? extends Annotation> annotation, Class<? extends Annotation> annotatedBy) {
    Preconditions.checkArgument(annotation != null && annotatedBy != null, "Annotation cannot be null");

    Annotation[] annotations = annotation.getAnnotations();
    for (Annotation a : annotations) {
      Class<? extends Annotation> aType = a.annotationType();
      if (annotatedBy.equals(aType)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns array of annotations which annotates the given annotated element and which are
   * annotated by given annotation type.
   *
   * @param element the annotated element.
   * @param annotatedBy the annotation type which annotates searched annotations.
   * @return The array of annotations of given element which are annotated by given annotation.
   */
  public static Annotation[] getAnnotatedAnnotations(AnnotatedElement element, Class<? extends Annotation> annotatedBy) {
    Preconditions.checkArgumentNotNull(element, "Annotated element cannot be null");
    Preconditions.checkArgumentNotNull(annotatedBy, "Annotation cannot be null");

    Annotation[] annotations = element.getAnnotations();
    List<Annotation> annotatedAnnotations = new ArrayList<Annotation>(annotations.length);
    for (int i = 0; i < annotations.length; i++) {
      Annotation[] annotationAnnotations = annotations[i].annotationType().getAnnotations();
      for (int j = 0; j < annotationAnnotations.length; j++) {
        Class<? extends Annotation> aType = annotationAnnotations[j].annotationType();
        if (annotatedBy.equals(aType)) {
          annotatedAnnotations.add(annotations[i]);
        }
      }
    }

    return annotatedAnnotations.toArray(new Annotation[0]);
  }

}
