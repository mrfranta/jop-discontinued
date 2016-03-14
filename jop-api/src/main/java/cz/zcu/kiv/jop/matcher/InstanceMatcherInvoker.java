package cz.zcu.kiv.jop.matcher;

import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;

/**
 * Interface which serves as interlayer between the instance matchers and another parts of this
 * library. Implementation of this interface should analyze the annotations of given property and
 * then should choose some instance matcher. Then a chosen instance matcher is used for finding
 * matching object from all already populated (generated) objects.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface InstanceMatcherInvoker {

  /**
   * Analyzes annotations for given property and then choose the proper instance matcher. Then a
   * chosen instance matcher is used for finding matching object from all already populated
   * (generated) objects stored in given <code>context</code>. If no matching object is found, the
   * <code>null</code> value is expected.
   *
   * @param property the property for which will be found and used proper instance matcher.
   * @return The matching object for given property or <code>null</code>
   * @throws InstanceMatcherException If some error occurs during finding of matching object using a
   *           proper instance matcher.
   */
  public Object match(Property<?> property, PopulatingContext context) throws InstanceMatcherException;

}
