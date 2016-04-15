package cz.zcu.kiv.jop.populator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.defaults.DefaultNumber;
import cz.zcu.kiv.jop.annotation.populator.NumberValue;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.Setter;
import cz.zcu.kiv.jop.util.Bounds;
import cz.zcu.kiv.jop.util.Defaults;
import cz.zcu.kiv.jop.util.NumberComparator;
import cz.zcu.kiv.jop.util.PrimitiveUtils;

/**
 * Implementation of property populator for population of numeric properties. This property
 * populator uses {@link NumberValue} parameters and supports chaining of populators.
 * <p>
 * Number value populator invokes chained property populator or value generator and tries to adjust
 * and converts returned number into format specified by params and into target numeric type.
 * Adjusted value is populated into property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class NumberValuePopulator extends AbstractPropertyPopulator<NumberValue> {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(NumberValuePopulator.class);

  /** Constant for comparator of number values. */
  protected static final NumberComparator COMPARATOR = new NumberComparator();

  /**
   * Returns target numeric type of populated value. In case that given <code>targetType</code> is
   * {@link DefaultNumber} and <code>propertyType</code> is {@link Object} or {@link Number}, the
   * {@link DefaultNumber#getDefaultClass()} value is returned, otherwise is returned the declared
   * class type of property. The <code>targetType</code> returned in case that is not
   * {@link DefaultNumber}.
   *
   * @param propertyType the declared class type of property.
   * @param targetType the target numeric type.
   * @return Target numeric type of populated value.
   */
  @SuppressWarnings("unchecked")
  protected Class<? extends Number> getTargetClassType(Class<?> propertyType, Class<? extends Number> targetType) {
    if (targetType == DefaultNumber.class) {
      if (propertyType == Object.class || propertyType == Number.class) {
        return DefaultNumber.getDefaultClass();
      }
      else {
        return (Class<? extends Number>)propertyType;
      }
    }
    else {
      return targetType;
    }
  }

  /**
   * Returns information whether the declared type of given property is supported by this property
   * populator.
   * <p>
   * Supported class types:
   * <ul>
   * <li>{@link Object};</li>
   * <li>assignable to {@link Number};</li>
   * <li>primitive numeric types (byte, short, int...).</li>
   */
  public boolean supports(Property<?> property) {
    Class<?> propertyType = property.getType();
    if (propertyType.isPrimitive()) {
      propertyType = PrimitiveUtils.wrap(propertyType);
    }

    return (propertyType == Object.class || Number.class.isAssignableFrom(propertyType));
  }

  /**
   * Returns bounds for given numeric type. In case that cannot be obtained bounds for given numeric
   * type it returns bounds between {@link Double#POSITIVE_INFINITY} and
   * {@link Double#NEGATIVE_INFINITY} values.
   *
   * @param numberType the numeric type for which will be returned bounds.
   * @return Bounds for given numeric type.
   */
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

  /**
   * Adjusts and converts given number into format specified by params and into target numeric type.
   *
   * @param value the number value which will be adjusted and converted into target numeric type.
   * @param params the parameters of number property populator which are used for adjustment of
   *          number value.
   * @param targetType the target numeric type.
   * @return Adjusted and converted number value.
   * @throws PropertyPopulatorException If some error occurs during processing of parameters.
   * @throws NumberFormatException If some error occurs during numerical conversion.
   */
  protected Number prepareNumberValue(Number value, NumberValue params, Class<? extends Number> targetType) throws PropertyPopulatorException, NumberFormatException {
    // check whatever the min <= max
    if (Double.compare(params.min(), params.max()) > 0) {
      throw new PropertyPopulatorException("Minimum has to be lesser than or equals to maximum");
    }

    Bounds<Number> bounds = getNumberBounds(targetType);
    Number minValue = COMPARATOR.min(bounds.getMin(), params.min());
    Number maxValue = COMPARATOR.min(bounds.getMax(), params.max());
    Number val = COMPARATOR.min(COMPARATOR.max(value, minValue), maxValue);

    // primitives or wrappers which will be autoboxed
    if (targetType.isPrimitive() || PrimitiveUtils.isWrapper(targetType)) {
      return toPrimitiveOrWrapperValue(val, targetType);
    }

    // special number classes

    if (targetType == BigInteger.class) {
      return new BigInteger(String.valueOf(val.longValue()));
    }

    if (targetType == BigDecimal.class) {
      return NumberComparator.toBigDecimal(val);
    }

    if (targetType == AtomicInteger.class) {
      return new AtomicInteger(val.intValue());
    }

    if (targetType == AtomicLong.class) {
      return new AtomicLong(val.longValue());
    }

    logger.warn("Unsupported numerical type: " + targetType.getName());

    return val;
  }

  /**
   * Converts given number into target primitive or numeric type.
   *
   * @param value the number value to convert.
   * @param targetType the target numeric type.
   * @return Value of converted number.
   */
  protected Number toPrimitiveOrWrapperValue(Number value, Class<? extends Number> targetType) {
    if (targetType == Integer.class || targetType == int.class) {
      return value.intValue();
    }

    String targetName = targetType.getSimpleName().toLowerCase();
    try {
      Method targetMethod = Number.class.getMethod(targetName + "Value");

      return (Number)targetMethod.invoke(value);
    }
    catch (Exception exc) {
      // should not happen
      logger.warn("Cannot transform number value: " + value + " into numerical type: " + targetType);
    }

    return value;
  }

  /**
   * Invokes chained property populator or value generator and tries to adjust and populate returned
   * number value into given property.
   */
  @SuppressWarnings("unchecked")
  public void populate(Property<?> property, Object owner, NumberValue params) throws PropertyPopulatorException {
    checkPropertyNotNull(property); // check property
    checkParamsNotNull(params); // check parameters

    // invoke chained populator or value generator
    Number value = propertyPopulatorInvoker.invokeNextPopulator(property, Number.class);

    // prepare target number class type
    Class<? extends Number> target = getTargetClassType(property.getType(), params.target());

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

}
