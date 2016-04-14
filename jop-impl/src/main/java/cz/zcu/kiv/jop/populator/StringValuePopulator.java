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

@Singleton
public class StringValuePopulator extends AbstractPropertyPopulator<StringValue> {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(StringValuePopulator.class);

  public boolean supports(Property<?> property) {
    Class<?> propertyType = property.getType();

    return (propertyType == Object.class || CharSequence.class.isAssignableFrom(propertyType));
  }

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

  @SuppressWarnings("unchecked")
  protected Class<? extends CharSequence> getTargetClassType(Class<?> propertyType, StringValue params) {
    if (params.target() == DefaultCharSequence.class) {
      if (propertyType == Object.class || propertyType == CharSequence.class) {
        return DefaultCharSequence.getDefaultClass();
      }
      else {
        return (Class<? extends CharSequence>)propertyType;
      }
    }
    else {
      return params.target();
    }
  }

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

  @SuppressWarnings("unchecked")
  public void populate(Property<?> property, Object owner, StringValue params) throws PropertyPopulatorException {
    checkPropertyNotNull(property); // check property
    checkParamsNotNull(params); // check parameters

    // invoke chained populator or value generator
    Object value = propertyPopulatorInvoker.invokeNextPopulator(property, Object.class);

    // transform the value into string value of required length
    String strValue = prepareStringValue(value, params);

    // prepare target character sequence class type
    Class<? extends CharSequence> target = getTargetClassType(property.getType(), params);

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
