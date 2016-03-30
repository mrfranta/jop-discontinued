package cz.zcu.kiv.jop.strategy;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.CustomAnnotation;
import cz.zcu.kiv.jop.annotation.strategy.PopulatingStrategyAnnotation;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.factory.FactoryException;
import cz.zcu.kiv.jop.ioc.NamedScopes;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.util.AnnotationUtils;

/**
 * Implementation of instance matcher invoker (interlayer between the instance matchers and another
 * parts of this library) which analyzes the annotations of given property and then should choose
 * some instance matcher. Then a chosen instance matcher is used for finding matching object from
 * all already populated (generated) objects.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class PopulatingStrategyInvokerImpl implements PopulatingStrategyInvoker {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(PopulatingStrategyInvokerImpl.class);

  /**
   * {@inheritDoc}
   */
  public boolean isAnnotationPresent(Property<?> property) {
    return AnnotationUtils.isAnnotatedAnnotationPresent(property, PopulatingStrategyAnnotation.class);
  }

  /**
   * {@inheritDoc}
   */
  public void applyStrategy(Property<?> property, PopulatingContext context) throws PopulatingStrategyException {
    if (property == null) {
      throw new PopulatingStrategyException("Property cannot be null");
    }

    Annotation[] annotations = AnnotationUtils.getAnnotatedAnnotations(property, PopulatingStrategyAnnotation.class);
    if (annotations == null || annotations.length == 0) {
      String strategyClassName = defaultPopulatingStrategy.getClass().getName();
      if (defaultPopulatingStrategy.supports(property)) {
        logger.debug("Invoking default populating strategy: " + strategyClassName + "; for property: " + property);
        defaultPopulatingStrategy.applyStrategy(property, context);
      }
      else {
        logger.warn("Default strategy " + strategyClassName + " doesn't support property (it will be skipped): " + property);
      }
      return; // default strategy applied
    }
    else if (annotations.length > 1) {
      throw new PopulatingStrategyException("Too many populating strategy annotations");
    }

    Annotation params = annotations[0];
    PopulatingStrategy populatingStrategy = null;
    if (AnnotationUtils.isAnnotatedAnnotation(params, CustomAnnotation.class)) {
      // in v1.0.0 is not supported, maybe later
      throw new PopulatingStrategyException("Custom populating strategy annotation is not supported");
    }

    populatingStrategy = getBoundPopulatingStrategy(params);

    // no populating strategy
    if (populatingStrategy == null) {
      throw new PopulatingStrategyException("No such populating strategy");
    }

    if (!populatingStrategy.supports(property)) {
      logger.debug("Populating strategy: " + populatingStrategy.getClass().getName() + " doesn't support property (it will be skipped): " + property);
      return;
    }

    logger.debug("Invoking populating strategy: " + populatingStrategy.getClass().getName() + "; for property: " + property);

    populatingStrategy.applyStrategy(property, context);
  }

  /**
   * Returns instance of populating strategy bound for given annotation in
   * {@link PopulatingStrategyFactory}.
   *
   * @param annotation the parameters annotation for which will be returned the populating strategy.
   * @return Bound populating strategy for given annotation.
   * @throws PopulatingStrategyException If some error occurs during obtaining of bound populating
   *           strategy.
   */
  protected PopulatingStrategy getBoundPopulatingStrategy(Annotation annotation) throws PopulatingStrategyException {
    try {
      return populatingStrategyFactory.createInstance(annotation);
    }
    catch (FactoryException exc) {
      throw new PopulatingStrategyException(exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Binding factory for creating/providing instances of populating strategies. */
  protected PopulatingStrategyFactory populatingStrategyFactory;
  /** Default implementation of populating strategy (default strategy). */
  protected PopulatingStrategy defaultPopulatingStrategy;

  /**
   * Sets (injects) binding factory for creating/providing instances of populating strategies.
   *
   * @param populatingStrategyFactory the populating strategy factory to set (inject).
   */
  @Inject
  public final void setPopulatingStrategyFactory(PopulatingStrategyFactory populatingStrategyFactory) {
    this.populatingStrategyFactory = populatingStrategyFactory;
  }

  /**
   * Sets (injects) default implementation of populating strategy.
   *
   * @param defaultPopulatingStrategy default strategy to set (inject).
   */
  @Inject
  public final void setDefaultPopulatingStrategy(@Named(NamedScopes.DEFAULT_IMPL) PopulatingStrategy defaultPopulatingStrategy) {
    this.defaultPopulatingStrategy = defaultPopulatingStrategy;
  }
}
