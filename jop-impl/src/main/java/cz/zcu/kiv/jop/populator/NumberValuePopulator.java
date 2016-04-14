package cz.zcu.kiv.jop.populator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.defaults.DefaultNumber;
import cz.zcu.kiv.jop.annotation.generator.number.UniformGenerator;
import cz.zcu.kiv.jop.annotation.populator.ArrayValue;
import cz.zcu.kiv.jop.annotation.populator.NumberValue;
import cz.zcu.kiv.jop.annotation.populator.StringValue;
import cz.zcu.kiv.jop.ioc.Injector;
import cz.zcu.kiv.jop.ioc.InjectorManager;
import cz.zcu.kiv.jop.property.DirectAccessProperty;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.Setter;
import cz.zcu.kiv.jop.util.Bounds;
import cz.zcu.kiv.jop.util.Defaults;
import cz.zcu.kiv.jop.util.NumberComparator;
import cz.zcu.kiv.jop.util.PrimitiveUtils;

@Singleton
public class NumberValuePopulator extends AbstractPropertyPopulator<NumberValue> {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(NumberValuePopulator.class);

  protected static final NumberComparator COMPARATOR = new NumberComparator();

  public boolean supports(Property<?> property) {
    Class<?> propertyType = property.getType();
    if (propertyType.isPrimitive()) {
      propertyType = PrimitiveUtils.wrap(propertyType);
    }

    return (propertyType == Object.class || Number.class.isAssignableFrom(propertyType));
  }

  protected Bounds<Number> getNumberBounds(Class<? extends Number> numberType) {
    if (numberType == BigDecimal.class || numberType == BigInteger.class) {
      return new Bounds<Number>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    if (numberType == AtomicInteger.class) {
      return new Bounds<Number>(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    if (numberType == AtomicLong.class) {
      return new Bounds<Number>(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    try {
      Class<?> targetType = numberType.isPrimitive() ? PrimitiveUtils.wrap(numberType) : numberType;

      Field minField = targetType.getField("MIN_VALUE");
      Field maxField = targetType.getField("MAX_VALUE");

      return new Bounds<Number>((Number)minField.get(null), (Number)maxField.get(null));
    }
    catch (Exception exc) {
      logger.warn("Cannot get bounds of numberic type: " + numberType + "; returning bounds with infinities");
      return new Bounds<Number>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
  }

  protected Number prepareNumberValue(Number value, NumberValue params, Class<? extends Number> numberType) throws PropertyPopulatorException, NumberFormatException {
    // check whatever the min <= max
    if (Double.compare(params.min(), params.max()) > 0) {
      throw new PropertyPopulatorException("Minimum has to be lesser than or equals to maximum");
    }

    Bounds<Number> bounds = getNumberBounds(numberType);
    Number minValue = COMPARATOR.min(bounds.getMin(), params.min());
    Number maxValue = COMPARATOR.min(bounds.getMax(), params.max());
    Number val = COMPARATOR.min(COMPARATOR.max(value, minValue), maxValue);

    // integer value
    if (numberType == Integer.class || numberType == Integer.TYPE) {
      return val.intValue();
    }

    // primitives or wrappers which will be autoboxed
    if (numberType.isPrimitive() || PrimitiveUtils.isWrapper(numberType)) {
      return toPrimitiveOrWrapperValue(val, numberType);
    }

    // special number classes

    if (numberType == BigInteger.class) {
      return new BigInteger(String.valueOf(val.longValue()));
    }

    if (numberType == BigDecimal.class) {
      return NumberComparator.toBigDecimal(val);
    }

    if (numberType == AtomicInteger.class) {
      return new AtomicInteger(val.intValue());
    }

    if (numberType == AtomicLong.class) {
      return new AtomicLong(val.longValue());
    }

    logger.warn("Unsupported numerical type: " + numberType.getName());

    return val;
  }

  protected Number toPrimitiveOrWrapperValue(Number value, Class<? extends Number> numberType) {
    String targetName = numberType.getSimpleName().toLowerCase();

    try {
      Method targetMethod = Number.class.getMethod(targetName + "Value");

      return (Number)targetMethod.invoke(value);
    }
    catch (Exception exc) {
      // should not happen
      logger.warn("Cannot transform number value: " + value + " into numerical type: " + numberType);
    }

    return value;
  }

  @SuppressWarnings("unchecked")
  protected Class<? extends Number> getTargetClassType(Class<?> propertyType, NumberValue params) {
    if (params.target() == DefaultNumber.class) {
      if (propertyType == Object.class || propertyType == Number.class) {
        return DefaultNumber.getDefaultClass();
      }
      else {
        return (Class<? extends Number>)propertyType;
      }
    }
    else {
      return params.target();
    }
  }

  @SuppressWarnings("unchecked")
  public void populate(Property<?> property, Object owner, NumberValue params) throws PropertyPopulatorException {
    checkPropertyNotNull(property); // check property
    checkParamsNotNull(params); // check parameters

    // invoke chained populator or value generator
    Number value = propertyPopulatorInvoker.invokeNextPopulator(property, Number.class);

    // prepare target number class type
    Class<? extends Number> target = getTargetClassType(property.getType(), params);

    if (value == null && target.isPrimitive()) {
      value = (Number)Defaults.getDefaultValue(target);
    }

    if (value != null) {
      // transform the value into number value according to parameters and target
      try {
        value = prepareNumberValue(value, params, target);
      }
      catch (NumberFormatException exc) {
        throw new PropertyPopulatorException("Cannot adjust number value: " + value, exc);
      }
    }

    try {
      Setter<Object> setter = (Setter<Object>)property.getSetter();
      setter.set(owner, value);
    }
    catch (Exception exc) {
      throw new PropertyPopulatorException("Cannot populate value '" + value + "' into property: " + property, exc);
    }
  }

  @ArrayValue(length = 10001, target = String.class)
  @StringValue(fill = ' ')
  @NumberValue(min = 10, target = Short.class)
  @UniformGenerator(min = 0, max = 1000)
  public static CharSequence[] field;

  public static void main(String[] args) throws Exception {
    Injector injector = InjectorManager.getInstance().get();
    PropertyPopulatorInvoker invoker = injector.getInstance(PropertyPopulatorInvoker.class);

    Property<?> property = new DirectAccessProperty<Object>(NumberValuePopulator.class, "field");

    long start = System.currentTimeMillis();
    for (int i = 0; i < 10; i++) {
      if (i % 50 == 0) {
        System.err.println();
      }
      System.err.print(i + ",");
      invoker.populate(property, null);
    }
    long end = System.currentTimeMillis();

    System.err.println();
    Object[] array = (Object[])property.getGetter().get(null);
    System.err.println("VALUE: " + array.length + " : " + Arrays.toString(array));

    System.err.println("time = " + ((end - start) / 10) + "ms");
  }

}
