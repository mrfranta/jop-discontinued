package cz.zcu.kiv.jop.construction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import cz.zcu.kiv.jop.factory.AbstractFactory;
import cz.zcu.kiv.jop.ioc.NamedScopes;

/**
 * Implementation of {@link ConstructionStrategyFactory} which extends the abstract implementation
 * of binding factory ({@link AbstractFactory}).
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ConstructionStrategyFactoryImpl extends AbstractFactory<ConstructionStrategy> implements ConstructionStrategyFactory {

  /**
   * {@inheritDoc}
   */
  public ConstructionStrategy getDefaultConstructionStrategy() {
    return defaultConstructionStrategy;
  }

  //----- Injection part ------------------------------------------------------

  /** Default implementation of construction strategy. */
  protected ConstructionStrategy defaultConstructionStrategy;

  /**
   * Sets (injects) default implementation of construction strategy.
   *
   * @param defaultConstructionStrategy default strategy to set (inject).
   */
  @Inject
  public final void setDefaultConstructionStrategy(@Named(NamedScopes.DEFAULT_IMPL) ConstructionStrategy defaultConstructionStrategy) {
    this.defaultConstructionStrategy = defaultConstructionStrategy;
  }

}
