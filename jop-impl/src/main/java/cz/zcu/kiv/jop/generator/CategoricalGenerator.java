package cz.zcu.kiv.jop.generator;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Random;

import cz.zcu.kiv.jop.util.ArrayUtils;
import cz.zcu.kiv.jop.util.PrimitiveUtils;

/**
 * Generic implementation of categorical generator which may be used for any class type or for
 * parameters annotation. The parameter annotation is in method {@link #getValue(Annotation)}
 * analyzed by reflection and its values and probabilities are used for generating of random value.
 * <p>
 * This class also contains static method {@link #getValue(Random, Object[], double[]) getValue} for
 * possibility to use categorical generator without the need to extend this class.
 * <p>
 * This class is abstract because it has to be extended to possibility use the method
 * {@link #getValueType()} which uses reflection for analysis of generic parameters. This solution
 * in not a best because it may cause the performance issues or it may return incorrect class type.
 * To prevent this, don't propagate generic parameter outside extending classes. For class from next
 * example the method returns the <code>Number</code> class:
 *
 * <pre>
 * public class SomeCategoricalGenerator&lt;N extends Number&gt; extends CategoricalGenerator&lt;N, FooAnnotation&gt; {}
 * </pre>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class CategoricalGenerator<T, P extends Annotation> extends AbstractValueGenerator<T, P> {

  /**
   * {@inheritDoc}
   * <p>
   * <strong>Warning:</strong> This method is using reflection to get the value type and it may have
   * performance issues and also may return incorrect value type. It's better to override this
   * method and return the value type manually.
   */
  @SuppressWarnings("unchecked")
  public Class<T> getValueType() {
    for (Class<?> clazz = getClass(); clazz.getSuperclass() != null; clazz = clazz.getSuperclass()) {
      Type genericSuperclass = clazz.getGenericSuperclass();
      if (genericSuperclass instanceof ParameterizedType) {
        Type[] typeArguments = ((ParameterizedType)genericSuperclass).getActualTypeArguments();
        if (typeArguments.length > 0) {
          // we are looking for first type argument
          Type type = typeArguments[0];
          try {
            if (type instanceof ParameterizedType) {
              // type argument is parameterized type, we have to get raw type
              return (Class<T>)((ParameterizedType)type).getRawType();
            }
            else if (type instanceof GenericArrayType) {
              // type argument is array type, we have to analyze generic component
              Type componentType = ((GenericArrayType)type).getGenericComponentType();

              // analyze the dimensions
              StringBuilder sb = new StringBuilder();
              sb.append('[');
              while (componentType instanceof GenericArrayType) {
                sb.append('[');
                componentType = ((GenericArrayType)componentType).getGenericComponentType();
              }

              // analyze the component class
              Class<?> componentClass = null;
              if (componentType instanceof ParameterizedType) {
                componentClass = (Class<?>)((ParameterizedType)componentType).getRawType();
              }
              else {
                componentClass = (Class<?>)componentType;
              }

              // finish the class name
              if (componentClass.isPrimitive()) {
                sb.append(PrimitiveUtils.abbreviate(componentClass));
              }
              else {
                sb.append('L').append(componentClass.getName()).append(';');
              }

              // find the class
              return (Class<T>)Class.forName(sb.toString());
            }
            else if (type instanceof TypeVariable) {
              // type argument is type variable, we will return first bound
              Type[] bounds = ((TypeVariable<?>)type).getBounds();
              if (bounds.length > 0) {
                if (bounds[0] instanceof ParameterizedType) {
                  return (Class<T>)((ParameterizedType)bounds[0]).getRawType();
                }
                else {
                  return (Class<T>)bounds[0];
                }
              }
            }
            else {
              return (Class<T>)type;
            }
          }
          catch (ClassCastException exc) {
            exc.printStackTrace();
            // only for catching
          }
          catch (ClassNotFoundException exc) {
            // only for catching
          }
        }
      }
    }

    return (Class<T>)Object.class;
  }

  /**
   * Returns generated value with categorical distribution according to given parameters.
   * <p>
   * This method uses reflection to read required values from parameters and then returns one value
   * with categorical distribution. The values has to be stored in annotation parameter
   * <code>value()</code> or <code>values()</code>. Probabilities has to be stored in annotation
   * parameter <code>probabilities()</code> which has to return array of floats or doubles.
   *
   * @param params the parameters for generation of value.
   * @return Generated value.
   * @throws ValueGeneratorException If given annotations are <code>null</code>, if the annotation
   *           doesn't contain required parameters or the parameters doesn't contain the correct
   *           values (if the <code>value()</code> contains no values, if probabilities are not
   *           array of floats or doubles).
   */
  protected T getRandomValue(P params) throws ValueGeneratorException {
    checkParamsNotNull(params); // check not null

    T[] values = getValues(params);
    double[] probabilities = getProbabilities(params);
    Random rand = getRandomGenerator(params);

    return getValue(rand, values, probabilities);
  }

  /**
   * Returns parameter of given annotation type. Returns <code>null</code> if the given annotation
   * doesn't contains the parameter with given name.
   *
   * @param annotation the annotation type.
   * @param name the name of parameter.
   * @return Method for parameter or <code>null</code>.
   */
  final Method getParameter(Class<? extends Annotation> annotation, String name) {
    try {
      return annotation.getMethod(name);
    }
    catch (Exception exc) {
      return null;
    }
  }

  /**
   * Returns values parameter of given parameters annotation.
   *
   * @param params the parameters annotation.
   * @return The array of values.
   * @throws ValueGeneratorException If cannot get values from parameters annotation.
   */
  @SuppressWarnings("unchecked")
  final T[] getValues(P params) throws ValueGeneratorException {
    // get annotation type
    Class<? extends Annotation> paramsClass = params.annotationType();

    // prepare method for parameter value() or values().
    Method valueMethod = getParameter(paramsClass, "value");
    if (valueMethod == null) { // fall-back for values
      valueMethod = getParameter(paramsClass, "values");
    }

    if (valueMethod == null) {
      throw new ValueGeneratorException("Parameters doesn't contains parameter for values");
    }

    try {
      Object valuesRaw = valueMethod.invoke(params);

      return (T[])ArrayUtils.toObjectArray(valuesRaw);
    }
    catch (Exception exc) {
      throw new ValueGeneratorException("Cannot get values from parameters: " + exc.getMessage());
    }
  }

  /**
   * Returns double array of probabilities.
   *
   * @param params the parameters annotation.
   * @return The double array of probabilities.
   * @throws ValueGeneratorException If cannot get probabilities from parameters annotation.
   */
  final double[] getProbabilities(P params) throws ValueGeneratorException {
    // get annotation type
    Class<? extends Annotation> paramsClass = params.annotationType();

    // prepare method for parameter probabilities()
    Method probabilitiesMethod = getParameter(paramsClass, "probabilities");
    if (probabilitiesMethod == null) {
      throw new ValueGeneratorException("Parameters doesn't contains parameter for probabilities");
    }

    try {
      Object probabilitiesRaw = probabilitiesMethod.invoke(params);
      if (probabilitiesRaw instanceof double[]) {
        return (double[])probabilitiesRaw;
      }
      else if (probabilitiesRaw instanceof float[]) {
        float[] floatProbabilities = (float[])probabilitiesRaw;

        double[] probabilities = new double[floatProbabilities.length];
        for (int i = 0; i < floatProbabilities.length; i++) {
          probabilities[i] = floatProbabilities[i];
        }

        return probabilities;
      }
      else {
        throw new IllegalArgumentException("Invalid type of parameter");
      }
    }
    catch (Exception exc) {
      throw new ValueGeneratorException("Cannot get probabilities from parameters: " + exc.getMessage());
    }
  }

  /**
   * Returns one of given values with categorical distribution. This method will
   * {@link #normalizeProbabilities normalize} given probabilities so the array of probabilities
   * will represent the interval [0,1]. Then generate random number between 0 and 1 and then
   * searches in interval of probabilities for index of value which will be returned.
   * <p>
   * <b>Example:</b> We have values [0, 1, 2] and array of probabilities [0.25, 0.5, 0.25] for which
   * will interval looks like:
   *
   * <pre>
   *  |-------------------,---------------------------------------,-------------------|
   * 0.0        0        0.25                 1                  0.75       2        1.0
   * </pre>
   *
   * The random generator will returns the value 0.25378 which belongs the probability interval
   * between 0.25 and 0.75 so the generator returns the value 1.
   *
   * @param random the random generator.
   * @param values the values for selection.
   * @param probabilities the array of values probabilities.
   * @return Random value from given values.
   * @throws ValueGeneratorException If given random generator is <code>null</code>; if values are
   *           <code>null</code> or empty.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Categorical_distribution">Categorical
   *      distribution</a>
   */
  public static <T> T getValue(Random random, T[] values, double[] probabilities) throws ValueGeneratorException {
    if (random == null) {
      throw new ValueGeneratorException("Random generator cannot be null");
    }

    if (values == null || values.length == 0) {
      throw new ValueGeneratorException("No such values");
    }

    // normalize probabilities
    double[] probs = normalizeProbabilities(probabilities, values.length);

    // get random value
    double rand = random.nextDouble();
    double lower = 0.0, upper = 0.0; // lower and upper bounds
    for (int i = 0; i < probs.length; i++) {
      upper = lower + probs[i];

      if (rand <= upper) {
        return values[i];
      }

      lower = upper;
    }

    return values[values.length - 1];
  }

  /**
   * Normalizes given array of probabilities. In case that the given array is null or empty, it will
   * return the array with equals probabilities. If the array of probabilities has different length
   * than the number of values, it will be truncated or expanded by zero values.
   * <p>
   * During normalization are recalculated probabilities to values which summary gives the value
   * 1.0. In other words this method returns always the array of values which summary is equals to
   * 1.0. One exception is the case when given values count is empty because in that case this
   * method returns empty array.
   *
   * @param probabilities the array of probabilities.
   * @param valuesCount the number of values.
   * @return The normalized array of probabilities.
   * @throws ValueGeneratorException If the array of probabilities contains negative value(s).
   */
  public static double[] normalizeProbabilities(double[] probabilities, int valuesCount) throws ValueGeneratorException {
    double[] normalized = new double[valuesCount];

    // no values, no probabilities.
    if (valuesCount == 0) {
      return normalized;
    }

    if (probabilities == null || probabilities.length == 0) {
      // same probabilities
      double probability = 1.0 / valuesCount;
      Arrays.fill(normalized, probability);
    }
    else {
      // fill the normalized array
      Arrays.fill(normalized, 0.0);
      // copy probabilities into array of normalized probabilities
      System.arraycopy(probabilities, 0, normalized, 0, Math.min(probabilities.length, valuesCount));

      // normalize probabilities
      double summary = 0.0;
      for (int i = 0; i < normalized.length; i++) {
        if (normalized[i] < 0.0) {
          throw new ValueGeneratorException("Negative probability at: " + i);
        }

        summary += normalized[i];
      }

      // all values has probabilities 0.0 -> each will have equal probability
      if (summary == 0.0) {
        Arrays.fill(normalized, 1.0 / valuesCount);
      }
      else if (summary != 1.0) {
        for (int i = 0; i < normalized.length; i++) {
          normalized[i] = normalized[i] / summary;
        }
      }
    }

    return normalized;
  }
}
