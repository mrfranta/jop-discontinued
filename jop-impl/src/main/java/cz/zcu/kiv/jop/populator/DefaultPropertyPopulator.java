package cz.zcu.kiv.jop.populator;

import javax.inject.Inject;
import javax.inject.Singleton;

import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.generator.ValueGeneratorInvoker;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.PropertyException;
import cz.zcu.kiv.jop.property.Setter;

/**
 * Implementation of default property populator which lookups for value generator annotation of
 * given property. If there is value generator annotation present invokes the proper property
 * generator and result of invocation populates into given property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class DefaultPropertyPopulator extends SimplePropertyPopulator {

  /**
   * {@inheritDoc}
   * <p>
   * This property populator can populate only properties which are annotated by value generator
   * annotation.
   */
  public boolean supports(Property<?> property) {
    // this populator supports only propeties annotated by value generator annotation
    return valueGeneratorInvoker.isAnnotationPresent(property);
  }

  /**
   * If property is annotated by value generator annotation invokes the proper value generator and
   * result of invocation populates into given property.
   *
   * @param property the property for which will be generated value and populated.
   * @param owner the instance of property owner.
   * @throws PropertyPopulatorException If some error occurs during value generator invocation or
   *           population of generated value into given property.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void populate(Property<?> property, Object owner) throws PropertyPopulatorException {
    if (property == null) {
      throw new PropertyPopulatorException("Property cannot be null");
    }

    if (valueGeneratorInvoker.isAnnotationPresent(property)) {
      try {
        // generate value
        Object value = valueGeneratorInvoker.getValue(property);

        // populate value
        Setter<Object> setter = (Setter<Object>)property.getSetter();
        setter.set(owner, value);
      }
      catch (ValueGeneratorException exc) {
        throw new PropertyPopulatorException("Cannot generate value for population of property: " + property, exc);
      }
      catch (PropertyException exc) {
        throw new PropertyPopulatorException("Cannot populate generated value into property: " + property, exc);
      }
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Interlayer (invoker) for value generators. */
  protected ValueGeneratorInvoker valueGeneratorInvoker;

  /**
   * Sets (injects) interlayer (invoker) for value generators.
   *
   * @param valueGeneratorInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setValueGeneratorInvoker(ValueGeneratorInvoker valueGeneratorInvoker) {
    this.valueGeneratorInvoker = valueGeneratorInvoker;
  }
}
