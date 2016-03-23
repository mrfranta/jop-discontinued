package cz.zcu.kiv.jop.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
   * Returns information whether the given annotated element is annotated by some annotation which
   * is marked by given (marker) annotation.
   *
   * @param element the annotated element.
   * @param annotatedBy the annotation type which annotates searched annotations.
   * @return <code>true</code> if given element is annotated by annotation which is marked by given
   *         (marker) annotation; <code>false</code> otherwise.
   */
  public static boolean isAnnotatedAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotatedBy) {
    Preconditions.checkArgumentNotNull(element, "Annotated element cannot be null");
    Preconditions.checkArgumentNotNull(annotatedBy, "Annotation cannot be null");

    Annotation[] annotations = element.getAnnotations();
    for (int i = 0; i < annotations.length; i++) {
      Annotation[] annotationAnnotations = annotations[i].annotationType().getAnnotations();
      for (int j = 0; j < annotationAnnotations.length; j++) {
        Class<? extends Annotation> aType = annotationAnnotations[j].annotationType();
        if (annotatedBy.equals(aType)) {
          return true;
        }
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

  /**
   * Creates proxy (implementation) for given annotation type with given parameters.
   *
   * @param annotation the annotation type for which will be created proxy.
   * @param parameters the parameters (values) of annotation members.
   * @return The proxy (implementation) of given annotation type.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Annotation> T getAnnotationProxy(Class<T> annotation, Map<String, Object> parameters) {
    Preconditions.checkArgument(annotation != null, "Annotation cannot be null");
    AnnotationProxy proxy = new AnnotationProxy(annotation, parameters);

    return (T)Proxy.newProxyInstance(annotation.getClassLoader(), new Class<?>[] {annotation, Annotation.class}, proxy);
  }

  /**
   * The invocation handler for proxy implementation of annotation.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   */
  static class AnnotationProxy implements InvocationHandler {

    /** Annotation type for which was created proxy (invocation handler). */
    private final Class<? extends Annotation> annotationType;
    /** Map of values for annotation members. */
    private final Map<String, Object> memberValues;
    /** Array of (methods for) annotation members. */
    private final Method[] memberMethods;

    /**
     * Constructs invocation handler for proxy implementation of annotation.
     *
     * @param annotation the annotation type for which was created proxy (invocation handler).
     * @param values the values of annotation implementation.
     */
    AnnotationProxy(Class<? extends Annotation> annotation, Map<String, Object> values) {
      Preconditions.checkArgument(annotation != null, "Annotation type cannot be null");

      Map<String, Object> vals = (values == null) ? new HashMap<String, Object>() : values;
      Method[] members = annotation.getDeclaredMethods();

      this.annotationType = annotation;
      this.memberMethods = members;
      this.memberValues = prepareMemberValues(members, vals);
    }

    /**
     * Prepares values for members of annotation.
     *
     * @param members the array of (methods for) annotation members.
     * @param values the map of custom values for annotation members.
     * @return The map of values for annotation members.
     */
    private Map<String, Object> prepareMemberValues(Method[] members, Map<String, Object> values) {
      Map<String, Object> memberValues = new LinkedHashMap<String, Object>();
      for (Method member : members) {
        String memberName = member.getName();
        if (values.containsKey(memberName)) {
          Object memberValue = prepareMemberValue(member, values.get(memberName));

          if (memberValue == null) {
            throw new IllegalArgumentException("Member value cannot be null");
          }

          memberValues.put(memberName, memberValue);
        }
        else {
          memberValues.put(memberName, member.getDefaultValue());
        }
      }

      return memberValues;
    }

    /**
     * Prepares value for member represented by given member.
     *
     * @param member the (method for) annotation member.
     * @param value the raw value to prepare.
     * @return Prepared value for annotation member.
     */
    private Object prepareMemberValue(Method member, Object value) {
      String memberName = member.getName();

      if (value == null) {
        // possible unboxing
        return Defaults.getDefaultValue(PrimitiveUtils.unwrap(member.getReturnType()));
      }
      else {
        Class<?> memberType = member.getReturnType();
        Class<?> valueType = value.getClass();
        if (memberType.isAssignableFrom(valueType)) {
          // we have to clone array
          if (valueType.isArray()) {
            return ArrayUtils.cloneArray(value);
          }

          return value;
        }
        else if (memberType.isPrimitive() && memberType.isAssignableFrom(PrimitiveUtils.unwrap(valueType))) {
          return value; // auto-unbox value
        }
        else {
          // auto-unbox for array values
          if (memberType.isArray() && valueType.isArray() && memberType.getComponentType().equals(PrimitiveUtils.unwrap(valueType.getComponentType()))) {
            return ArrayUtils.toPrimitiveArray(value);
          }

          throw new IllegalArgumentException("Given incompatible type of member: " + memberName);
        }
      }
    }

    /**
     * Invocation of annotation method.
     *
     * @param proxy the instance of this proxy class.
     * @param method the invoked method.
     * @param args the arguments for method invocation.
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String methodName = method.getName();
      Class<?>[] argsClassses = method.getParameterTypes();

      if ((methodName.equals("equals")) && (args.length == 1) && (argsClassses[0] == Object.class)) {
        return equalsImpl(proxy, args[0]);
      }
      assert (argsClassses.length == 0);
      if (methodName.equals("toString")) {
        return toStringImpl();
      }
      if (methodName.equals("hashCode")) {
        return hashCodeImpl();
      }
      if (methodName.equals("annotationType")) {
        return annotationType;
      }

      Object memberValue = this.memberValues.get(methodName);
      if ((memberValue.getClass().isArray()) && (Array.getLength(memberValue) != 0)) {
        memberValue = ArrayUtils.cloneArray(memberValue);
      }

      return memberValue;
    }

    /**
     * Implementation of generic hash code method which returns a hash code value for the
     * annotation. This method is supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     *
     * @return A hash code value for this annotation.
     */
    private int hashCodeImpl() {
      int hashCode = 0;

      for (Map.Entry<String, Object> entry : this.memberValues.entrySet()) {
        int memberHashCode = 0;
        Object value = entry.getValue();
        if (value != null) {
          if (!value.getClass().isArray()) {
            memberHashCode = value.hashCode();
          }
          else {
            memberHashCode = ArrayUtils.hashCode(value);
          }
        }

        hashCode += (127 * entry.getKey().hashCode() ^ memberHashCode);
      }

      return hashCode;
    }

    /**
     * Implementation of generic equals method which indicates whether some object is "equal to"
     * this annotation.
     *
     * @param thisObj the reference to this object.
     * @param obj the reference object with which to compare.
     * @return <code>true</code> if this annotation is the same as the obj argument;
     *         <code>false</code> otherwise.
     */
    private boolean equalsImpl(Object thisObj, Object obj) {
      if (obj == thisObj) {
        return true;
      }

      if (!(annotationType.isInstance(obj))) {
        return false;
      }

      for (Method member : memberMethods) {
        String memberName = member.getName();
        Object value1 = memberValues.get(memberName);
        Object value2 = null;

        try {
          value2 = member.invoke(obj);
        }
        catch (Exception exc) {
          return false;
        }

        if (value1 == null) {
          if (value2 != null) {
            return false;
          }
        }

        Class<?> clazz = member.getReturnType();
        if (!(clazz.isArray())) {
          if (!value1.equals(value2)) {
            return false;
          }
          continue;
        }

        if (!clazz.equals(value2.getClass())) {
          return false;
        }

        if (!ArrayUtils.equals(value1, value2)) {
          return false;
        }
      }

      return true;
    }

    /**
     * Implementation of generic toString method which returns string value of annotation type.
     *
     * @return String value of annotation type.
     */
    private String toStringImpl() {
      StringBuilder sb = new StringBuilder(128);
      sb.append('@').append(annotationType.getName()).append('(');

      Set<Map.Entry<String, Object>> entrySet = memberValues.entrySet();
      int n = 0;
      for (Map.Entry<String, Object> entry : entrySet) {
        if (n > 0) {
          sb.append(", ");
        }

        sb.append(entry.getKey());
        sb.append('=');

        Object value = entry.getValue();
        if (value == null) {
          sb.append("null");
        }
        else {
          if (!value.getClass().isArray()) {
            sb.append(String.valueOf(value));
          }
          else {
            sb.append('[');
            for (int i = 0; i < Array.getLength(value); i++) {
              if (i > 0) {
                sb.append(", ");
              }
              sb.append(String.valueOf(Array.get(value, i)));
            }
            sb.append(']');
          }
        }

        n++;
      }

      return sb.append(')').toString();
    }
  }
}
