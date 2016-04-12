package cz.zcu.kiv.jop.context;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.session.PopulatingSession;

/**
 * Extension of default populating context which doesn't support population of dependencies of
 * beans. Because of that the method {@link #canPopulate(Class)} returns always <code>false</code>
 * and methods {@link #populate(Bean)} and {@link #populate(Object)} has no effect and returns
 * always the <code>false</code>.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class SinglePopulatingContext extends DefaultPopulatingContext {

  /**
   * Constructs new populating context which doesn't support population of dependencies.
   */
  public SinglePopulatingContext() {
    super(null, new PopulatingQueueImpl(), (PopulatingSession)null, null);
  }

  /**
   * Constructs new populating context which doesn't support population of dependencies.
   *
   * @param queue the populating queue which contains the bean descriptors of dependencies which
   *          will be populated.
   */
  protected SinglePopulatingContext(PopulatingQueue queue) {
    super(null, queue, (PopulatingSession)null, null);
  }

  /**
   * This method returns always empty list because this context doesn't support population of
   * dependencies.
   *
   * @param includeGlobalInstances information whether will be included instances from previous
   *          calls of ObjectPopulator.
   * @return Empty list.
   */
  @Override
  public List<Object> getPopulatedInstances(boolean includeGlobalInstances) {
    return new ArrayList<Object>();
  }

  /**
   * Returns always <code>false</code> because this context doesn't support population of
   * dependencies.
   *
   * @param dependency the dependency of current bean (object) to check.
   * @return Always <code>false</code>.
   */
  @Override
  public boolean canPopulate(Class<?> dependency) {
    return false;
  }

  /**
   * This method has no effect and returns always <code>false</code> because this context doesn't
   * support population of dependencies.
   *
   * @param dependency the dependency of current bean (object) to populate.
   * @return Always <code>false</code>.
   */
  @Override
  public boolean populate(Object dependency) {
    return false;
  }

  /**
   * This method has no effect and returns always <code>false</code> because this context doesn't
   * support population of dependencies.
   *
   * @param dependency the bean descriptor of dependency of current bean (object) to populate.
   * @return Always <code>false</code>.
   */
  @Override
  public boolean populate(Bean dependency) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PopulatingContext createConstructionContext(Bean bean) {
    return new SingleConstructionPopulatingContext(bean);
  }
}
