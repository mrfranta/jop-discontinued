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

@Singleton
public class ArrayValuePopulator extends AbstractPropertyPopulator<ArrayValue> {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(ArrayValuePopulator.class);

  //protected static final int MULTITHREAD_THRESHOLD = 50;

  /**
   *
   */
  public boolean supports(Property<?> property) {
    Class<?> propertyType = property.getType();

    // property has to be Object or one-dimensional array
    return (propertyType == Object.class || (propertyType.isArray() && !propertyType.getComponentType().isArray()));
  }

  /**
   *
   * @param params
   * @return
   * @throws PropertyPopulatorException
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
   * Returns component type for given array property. The component type will be used for creation
   * of populated array.
   *
   * @param property the array property.
   * @param params the parameters of populator.
   * @return Component type of array property.
   */
  protected Class<?> getComponentType(Property<?> property, ArrayValue params) {
    if (params.target() == Default.class) {
      return property.getType().getComponentType();
    }
    else {
      return params.target();
    }
  }

  @SuppressWarnings("unchecked")
  public void populate(Property<?> property, Object owner, ArrayValue params) throws PropertyPopulatorException {
    checkPropertyNotNull(property); // check property
    checkParamsNotNull(params); // check parameters

    int length = getLength(params);

    // get component type and create new instance of array
    logger.debug("Creating array with length: " + length);
    Class<?> componentType = getComponentType(property, params);
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
    //if (length < MULTITHREAD_THRESHOLD) {
    for (int i = 0; i < length; i++) {
      Object value = propertyPopulatorInvoker.invokeNextPopulator(property, componentType);
      Array.set(array, i, value);
    }
    //}
    /*else {
      ArrayFillJob job = new ArrayFillJob(property, componentType, array, length);
      job.start();
      try {
        job.join();
      }
      catch (InterruptedException exc) {
        // doesn't matter
      }
    }*/
  }

  /*
  protected class ArrayFillJob extends Thread {

    protected final Property<?> property;
    protected final Class<?> componentType;
    protected final Object array;
    protected final int length;
    private int index;

    public ArrayFillJob(Property<?> property, Class<?> componentType, Object array, int length) {
      this.property = property;
      this.componentType = componentType;
      this.array = array;
      this.length = length;
      this.index = 0;
    }

    protected int getFillersCount(int length) {
      int count = 2;
      for (int i = length; i > 10; i = i / 10) {
        count++;
      }

      return count;
    }

    @Override
    public void run() {
      ArrayFiller[] fillers = new ArrayFiller[getFillersCount(length)];
      for (int i = 0; i < fillers.length; i++) {
        fillers[i] = new ArrayFiller(this);
        fillers[i].start();
      }

      for (int i = 0; i < fillers.length; i++) {
        try {
          fillers[i].join();
        }
        catch (InterruptedException exc) {
          // doesn't matter
        }
      }
    }

    protected synchronized Integer next() {
      if (index < length) {
        return index++;
      }

      return null;
    }
  }

  protected class ArrayFiller extends Thread {

    protected final ArrayFillJob job;

    public ArrayFiller(ArrayFillJob job) {
      this.job = job;
    }

    @Override
    public void run() {
      Integer index = null;
      while ((index = job.next()) != null) {
        try {
          Object value = propertyPopulatorInvoker.invokeNextPopulator(job.property, job.componentType);
          Array.set(job.array, index, value);
        }
        catch (PropertyPopulatorException exc) {
          break;
        }
      }
    }

  }
  */

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
