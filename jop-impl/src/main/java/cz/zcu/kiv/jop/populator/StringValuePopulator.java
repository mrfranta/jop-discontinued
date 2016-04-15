package cz.zcu.kiv.jop.populator;

import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.defaults.DefaultCharSequence;
import cz.zcu.kiv.jop.annotation.populator.StringValue;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.Setter;
import cz.zcu.kiv.jop.util.ReflectionException;
import cz.zcu.kiv.jop.util.ReflectionUtils;
import cz.zcu.kiv.jop.util.StringUtils;

/**
 * Implementation of property populator for population of string ({@link CharSequence}) properties.
 * This property populator uses {@link StringValue} parameters and supports chaining of populators.
 * <p>
 * String value populator invokes chained property populator or value generator and tries to adjust
 * and converts returned value into format specified by params and into target {@link CharSequence}
 * type. Adjusted value is populated into property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class StringValuePopulator extends AbstractPropertyPopulator<StringValue> {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(StringValuePopulator.class);

  /**
   * Returns information whether the declared type of given property is supported by this property
   * populator.
   * <p>
   * Supported class types:
   * <ul>
   * <li>{@link Object};</li>
   * <li>assignable to {@link CharSequence}.</li>
   */
  public boolean supports(Property<?> property) {
    Class<?> propertyType = property.getType();

    return (propertyType == Object.class || CharSequence.class.isAssignableFrom(propertyType));
  }

  /**
   * Returns target string type of populated value. In case that given <code>targetType</code> is
   * {@link DefaultCharSequence} and <code>propertyType</code> is {@link Object} or
   * {@link CharSequence}, the {@link DefaultCharSequence#getDefaultClass()} value is returned,
   * otherwise is returned the declared class type of property. The <code>targetType</code> returned
   * in case that is not {@link DefaultCharSequence}.
   *
   * @param propertyType the declared class type of property.
   * @param targetType the target string type.
   * @return Target string type of populated value.
   */
  @SuppressWarnings("unchecked")
  protected Class<? extends CharSequence> getTargetClassType(Class<?> propertyType, Class<? extends CharSequence> targetType) {
    if (targetType == DefaultCharSequence.class) {
      if (propertyType == Object.class || propertyType == CharSequence.class) {
        return DefaultCharSequence.getDefaultClass();
      }
      else {
        return (Class<? extends CharSequence>)propertyType;
      }
    }
    else {
      return targetType;
    }
  }

  /**
   * Converts given value into string value which is adjusted into format specified by params.
   *
   * @param value the number value which will converted into string value and adjusted.
   * @param params the parameters of string value populator which are used for adjustment of given
   *          value.
   * @return Converted and adjusted string value of given value.
   * @throws PropertyPopulatorException If some error occurs during processing of parameters.
   */
  protected String prepareStringValue(Object value, StringValue params) throws PropertyPopulatorException {
    String str = String.valueOf(value);
    int strlen = str.length();

    if (params.length() >= 0) {
      logger.debug("The exact length set. Minimal length " + params.minLength() + " and maximal length " + params.maxLength() + " will be ignored");
      str = StringUtils.expandOrCrop(str, params.length(), params.fill());
    }
    else {
      int minLength = params.minLength();
      if (minLength < 0) {
        logger.warn("Minimal length of char sequence is lesser than 0. Changing to minLength = 0");
        minLength = 0;
      }

      int maxLength = params.maxLength();
      if (maxLength >= 0 && maxLength < minLength) {
        throw new PropertyPopulatorException("Maximal length has to be greater than or equals to minimal length");
      }

      if (strlen < minLength) {
        str = StringUtils.expandOrCrop(str, minLength, params.fill());
      }
      else if (maxLength > 0 && strlen > maxLength) {
        str = StringUtils.expandOrCrop(str, maxLength, params.fill());
      }
    }

    return str;
  }

  /**
   * Transforms given string value into target implementation of {@link CharSequence}.
   *
   * @param str the string value to transform.
   * @param target the class type of target implementation of {@link CharSequence}.
   * @return Transformed string value.
   * @throws PropertyPopulatorException If some error occurs during transformation.
   */
  protected CharSequence toCharSequence(String str, Class<? extends CharSequence> target) throws PropertyPopulatorException {
    if (target == String.class) {
      return str;
    }

    try {
      return ReflectionUtils.createInstance(target, str);
    }
    catch (ReflectionException exc) {
      throw new PropertyPopulatorException("Cannot create instance of " + target, exc);
    }
  }

  /**
   * Invokes chained property populator or value generator and transforms returned value into string
   * value. Then the string value is adjusted and populate value into given property.
   */
  @SuppressWarnings("unchecked")
  public void populate(Property<?> property, Object owner, StringValue params) throws PropertyPopulatorException {
    checkPropertyNotNull(property); // check property
    checkParamsNotNull(params); // check parameters

    // invoke chained populator or value generator
    Object value = propertyPopulatorInvoker.invokeNextPopulator(property, Object.class);

    // transform the value into string value of required length
    String strValue = prepareStringValue(value, params);

    // prepare target character sequence class type
    Class<? extends CharSequence> target = getTargetClassType(property.getType(), params.target());

    // transform into target char sequence
    value = toCharSequence(strValue, target);

    try {
      Setter<Object> setter = (Setter<Object>)property.getSetter();
      setter.set(owner, value);
    }
    catch (Exception exc) {
      throw new PropertyPopulatorException("Cannot populate value '" + value + "' into property: " + property, exc);
    }
  }
}
