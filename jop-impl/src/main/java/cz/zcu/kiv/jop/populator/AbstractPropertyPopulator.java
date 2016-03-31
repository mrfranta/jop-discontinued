package cz.zcu.kiv.jop.populator;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import cz.zcu.kiv.jop.property.Property;

/**
 * The abstract ancestor for property propulators. This class contains helpers methods for checking
 * parameters of property populator. It also contains injected invoker of property populators for
 * possibility to invoke next (chained) property populator (or default one).
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <P> Annotation type of parameters for populator of property.
 */
public abstract class AbstractPropertyPopulator<P extends Annotation> implements PropertyPopulator<P> {

  /**
   * Checks whatever given property is not <code>null</code>. In case that given property is
   * <code>null</code> the {@link PropertyPopulatorException} is thrown. This method also returns
   * given property so it can be used in this way:
   *
   * <pre>
   * Setter&lt;?&gt; setter = checkPropertyNotNull(property).getSetter();
   * setter.set(owner, value);
   * </pre>
   *
   * @param property the property to check.
   * @return Given property.
   * @throws PropertyPopulatorException If given property is <code>null</code>.
   */
  public static Property<?> checkPropertyNotNull(Property<?> property) throws PropertyPopulatorException {
    if (property == null) {
      throw new PropertyPopulatorException("Property cannot be null");
    }

    return property;
  }

  /**
   * Checks whatever parameters of property populator (<code>params</code>) are not
   * <code>null</code>. In case that given parameters are <code>null</code> the
   * {@link PropertyPopulatorException} is thrown. This method also returns given parameters of
   * property populator so checking of <code>null</code> value can be used in this way:
   *
   * <pre>
   * int length = checkParamsNotNull(params).length();
   * </pre>
   *
   * @param params the parameters of value generator which will be checked.
   * @return Given parameters of value generator.
   * @throws PropertyPopulatorException If given parameters of value generator are <code>null</code>
   *           .
   */
  public static <P extends Annotation> P checkParamsNotNull(P params) throws PropertyPopulatorException {
    if (params == null) {
      throw new PropertyPopulatorException("Parameters of property populator cannot be null");
    }

    return params;
  }

  /**
   * Check whatever the parameters of value generators meet given <code>condition</code> and if not,
   * the {@link PropertyPopulatorException} is thrown. This method is useful for single-line
   * checking of value generator parameters. This method can be used in this way:
   *
   * <pre>
   * checkParams(params.length() >= 0) // minimal possible value
   * int length = params.length();
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @throws PropertyPopulatorException If given condition is not met.
   */
  public static void checkParams(boolean condition) throws PropertyPopulatorException {
    if (!condition) {
      throw new PropertyPopulatorException();
    }
  }

  /**
   * Check whatever the parameters of value generators meet given <code>condition</code> and if not,
   * the {@link PropertyPopulatorException} with given error message is thrown. This method is
   * useful for single-line checking of value generator parameters. This method can be used in this
   * way:
   *
   * <pre>
   * checkParams(params.length() >= 0, "Length has to be greater or equals to 0") // minimal possible value
   * int length = params.length();
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @param errorMessage the error message for thrown exception in case that given
   *          <code>condition</code> is not met.
   * @throws PropertyPopulatorException If given condition is not met.
   */
  public static void checkParams(boolean condition, String errorMessage) throws PropertyPopulatorException {
    if (!condition) {
      throw new PropertyPopulatorException(errorMessage);
    }
  }

  /**
   * Check whatever the parameters of value generators meet given <code>condition</code> and if not,
   * the {@link PropertyPopulatorException} with given formatted error message is thrown. This
   * method is useful for single-line checking of value generator parameters. This method can be
   * used in this way:
   *
   * <pre>
   * checkParams(params.length() >= 0, "Length %d is not valid")
   * int length = params.length();
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @param errorMessageFormat the formatted error message for thrown exception in case that given
   *          <code>condition</code> is not met.
   * @param errorMessageArgs the arguments for given formatted error message.
   * @throws PropertyPopulatorException If given condition is not met.
   */
  public static void checkParams(boolean condition, String errorMessageFormat, Object... errorMessageArgs) throws PropertyPopulatorException {
    if (!condition) {
      throw new PropertyPopulatorException(String.format(errorMessageFormat, errorMessageArgs));
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Interlayer (invoker) for property populators. */
  protected PropertyPopulatorInvoker propertyPopulatorInvoker;

  /**
   * Sets (injects) interlayer (invoker) for property populators.
   *
   * @param propertyPopulatorInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setPropertyPopulatorInvoker(PropertyPopulatorInvoker propertyPopulatorInvoker) {
    this.propertyPopulatorInvoker = propertyPopulatorInvoker;
  }

}
