package cz.zcu.kiv.jop.populator;

import java.lang.reflect.Array;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.defaults.Default;
import cz.zcu.kiv.jop.annotation.populator.ArrayValue;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.Setter;
import cz.zcu.kiv.jop.session.RandomGeneratorSession;

/**
 * Implementation of property populator for population of array properties. This property populator
 * uses {@link ArrayValue} parameters and supports chaining of populators.
 * <p>
 * Array value populator firstly prepares length and component type of populated array. Then starts
 * serial or parallel population of array using invocations of chained populators or value
 * generator. Filled array is populated into given property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ArrayValuePopulator extends AbstractPropertyPopulator<ArrayValue> {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(ArrayValuePopulator.class);

  /** Constant for number of elements from which is used multi-thread solution. */
  protected static final int MULTITHREAD_THRESHOLD = 100;

  /**
   * Returns information whether the declared type of given property is supported by this property
   * populator.
   * <p>
   * Supported class types:
   * <ul>
   * <li>{@link Object};</li>
   * <li>one-dimensional array.</li>
   */
  public boolean supports(Property<?> property) {
    Class<?> propertyType = property.getType();

    // property has to be Object or one-dimensional array
    return (propertyType == Object.class || (propertyType.isArray() && !propertyType.getComponentType().isArray()));
  }

  /**
   * Returns target length of populated array according to given parameters.
   *
   * @param params the parameters of property populator of array values.
   * @return Target length of array.
   * @throws PropertyPopulatorException If some error occurs during processing of parameters.
   */
  protected int getLength(ArrayValue params) throws PropertyPopulatorException {
    if (params.length() >= 0) {
      logger.debug("The exact length set. Minimal length " + params.minLength() + " and maximal length " + params.maxLength() + " will be ignored");
      return params.length();
    }
    else {
      int minLength = params.minLength();
      if (minLength < 0) {
        logger.warn("Minimal length of array is lesser than 0. Changing to minLength = 0");
        minLength = 0;
      }

      int maxLength = params.maxLength();
      if (maxLength >= 0 && maxLength < minLength) {
        throw new PropertyPopulatorException("Maximal length has to be greater than or equals to minimal length");
      }

      Random rand = randomGeneratorSession.getRandomGenerator(params);
      if (maxLength < 0) {
        maxLength = minLength + rand.nextInt(Integer.MAX_VALUE - minLength);
        logger.debug("Maximal length is not set, generated maximal length: " + maxLength);
      }

      return minLength + rand.nextInt(maxLength - minLength);
    }
  }

  /**
   * Returns target component type for given array property which will be used for creation of
   * populated array. If the given <code>targetType</code> is {@link Default} the declared component
   * type of <code>propertyType</code> is returned. The <code>targetType</code> returned in case
   * that is not {@link Default}.
   *
   * @param propertyType the declared class type of property.
   * @param targetType the target component type.
   * @return Target component type of array property.
   */
  protected Class<?> getComponentType(Class<?> propertyType, Class<?> targetType) {
    if (targetType == Default.class) {
      return propertyType.getComponentType();
    }
    else {
      return targetType;
    }
  }

  /**
   * Prepares length and component type of populated array. Then starts serial or parallel
   * population of array using invocations of chained populators or value generator. Filled array is
   * populated into given property.
   */
  @SuppressWarnings("unchecked")
  public void populate(Property<?> property, Object owner, ArrayValue params) throws PropertyPopulatorException {
    checkPropertyNotNull(property); // check property
    checkParamsNotNull(params); // check parameters

    int length = getLength(params);

    // get component type and create new instance of array
    logger.debug("Creating array with length: " + length);
    Class<?> componentType = getComponentType(property.getType(), params.target());
    Object array = Array.newInstance(componentType, length);

    // set array into property
    try {
      Setter<Object> setter = (Setter<Object>)property.getSetter();
      setter.set(owner, array);
    }
    catch (Exception exc) {
      throw new PropertyPopulatorException("Cannot populate array value into property: " + property, exc);
    }

    // fill the array
    if (length < MULTITHREAD_THRESHOLD) {
    for (int i = 0; i < length; i++) {
      Object value = propertyPopulatorInvoker.invokeNextPopulator(property, componentType);
      Array.set(array, i, value);
    }
    }
    else {
      // prepares holder of filler job
      ArrayFillJob job = new ArrayFillJob(property, componentType, array, length);

      int threadsCount = (int)Math.log10(length);
      ArrayFiller[] fillers = new ArrayFiller[threadsCount];
      for (int i = 0; i < threadsCount; i++) {
        fillers[i] = new ArrayFiller(job);
        fillers[i].start();
      }

      for (int i = 0; i < threadsCount; i++) {
        try {
          fillers[i].join();
        }
        catch (InterruptedException exc) {
          // doesn't matter
        }
      }
    }
  }

  /**
   * The holder of job for parallel filling (populating) of array.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   */
  protected class ArrayFillJob {

    /** The populated property. */
    protected final Property<?> property;
    /** The component type of array. */
    protected final Class<?> componentType;
    /** The filled (populated) array. */
    protected final Object array;
    /** The length of array. */
    protected final int length;

    /** Actually populated index. */
    private int actualIndex;
    /** The potential error which occured during filling (population) of array. */
    private PropertyPopulatorException error;

    /**
     * Contructs holder of parallel job.
     *
     * @param property the populated property.
     * @param componentType the component type of array.
     * @param array the filled (populated) array.
     * @param length the length of array.
     */
    public ArrayFillJob(Property<?> property, Class<?> componentType, Object array, int length) {
      this.property = property;
      this.componentType = componentType;
      this.array = array;
      this.length = length;

      // prepare local fields
      this.actualIndex = 0;
      this.error = null;
    }

    /**
     * Returns next index for fill (population). It returns negative value if there is no next
     * available index.
     *
     * @return Next index.
     */
    protected synchronized int next() {
      if (error != null) {
        return -1;
      }

      if (actualIndex < length) {
        return actualIndex++;
      }

      return -1;
    }

    /**
     * Sets filling (populating) error.
     *
     * @param error the error to set.
     */
    protected synchronized void setError(PropertyPopulatorException error) {
      if (this.error == null) {
        this.error = error;
      }
    }
  }

  /**
   * The thread for parallel filling (populating) of some array property.
   *
   * @author Mr.FrAnTA
   * @since 1.0.0
   */
  protected class ArrayFiller extends Thread {

    /** The holder of filling (populating) job. */
    protected final ArrayFillJob job;

    /**
     * Constructs thread for filling (populating) of array.
     *
     * @param job the holder of filling (populating) job.
     */
    public ArrayFiller(ArrayFillJob job) {
      this.job = job;
    }

    /**
     * Receives in loop indexes for fill (population) from filling job. If the index is negative,
     * breaks the filling (populating) loop. If the index is positive or zero the chainded populator
     * or value generator is invoked and the returned value is inserted into array.
     */
    @Override
    public void run() {
      int index = -1;
      while ((index = job.next()) >= 0) {
        System.err.println(index);
        try {
          Object value = propertyPopulatorInvoker.invokeNextPopulator(job.property, job.componentType);
          Array.set(job.array, index, value);
        }
        catch (PropertyPopulatorException exc) {
          job.setError(exc);
          break;
        }
      }
    }
  }

  // ----- Injection part ------------------------------------------------------

  /** Session which stores random generators. */
  protected RandomGeneratorSession randomGeneratorSession;

  /**
   * Sets (injects) session which stores random generators.
   *
   * @param randomGeneratorSession the session to set (inject).
   */
  @Inject
  public final void setRandomGeneratorSession(RandomGeneratorSession randomGeneratorSession) {
    this.randomGeneratorSession = randomGeneratorSession;
  }
}
