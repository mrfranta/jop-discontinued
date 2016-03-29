package cz.zcu.kiv.jop.strategy;

import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;

/**
 * Interface which serves as interlayer between the populating strategies and another parts of this
 * library. Implementation of this interface should analyze the annotations of given property and
 * then should choose and invoke some populating strategy - during invocation is the strategy is
 * applied to given property.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingStrategyInvoker {

  /**
   * Returns information whether the populating strategy annotation is present on given property.
   *
   * @param property the property which annotations will be checked.
   * @return <code>true</code> if given property is annotated by populating strategy annotation;
   *         <code>false</code> otherwise.
   */
  public boolean isAnnotationPresent(Property<?> property);

  /**
   * Analyzes annotations of given property and then choose the proper populating strategy for
   * invocation. If the property has no populating strategy annotation, the default strategy is
   * chosen. The chosen strategy is applied to given property.
   *
   * @param property the property for which will be invoked the proper populating strategy.
   * @param context the populating context which contains already populated (generated) objects
   *          which may be obtained; also supports enqueuing classes for population.
   * @throws PopulatingStrategyException If some error occurs during populating strategy invocation.
   */
  public void applyStrategy(Property<?> property, PopulatingContext context) throws PopulatingStrategyException;

}
