package cz.zcu.kiv.jop.context;

import java.util.List;

import cz.zcu.kiv.jop.bean.Bean;

/**
 * The interface for populating context which stores all required information about current ongoing
 * population of some object. Context contains currently populated (generated) object and list of
 * all already populated (generated) objects.
 * <p>
 * The context should contain the {@link DependencyGraph} for preventing cyclic dependencies, the
 * {@link PopulatingQueue} for enqueuing dependencies to generate and populate and the
 * {@link PopulatingSession} which stores the already populated (generated) objects.
 * <p>
 * This context should be created for each call of method <code>populate</code> of
 * {@link cz.zcu.kiv.jop.ObjectPopulator ObjectPopulator}. context should not be shared with other
 * calls of <code>populate</code> methods (except the session which may share instances between
 * calls) and it should not be in IoC context because it should be handled exclusively by populator.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface PopulatingContext {

  /**
   * Returns instance of currently populated object (bean).
   *
   * @return Instance of currently populated object.
   */
  public Object getCurrentObject();

  /**
   * Returns currently populated bean descriptor for currently populated object returned by method
   * {@link #getCurrentObject()}.
   *
   * @return Currently populated bean.
   */
  public Bean getCurrentBean();

  /**
   * Checks whatever the given dependency (of currently populated bean which is returned by method
   * {@link #getCurrentBean()}) can be enqueued into populating queue for population. It will be
   * checked whatever the given dependency don't create cycle in graph (tree) of dependencies.
   *
   * @param dependency the dependency of current bean (object) to check.
   * @return <code>true</code> if given dependency can be populated; <code>false</code> otherwise.
   */
  public boolean canPopulate(Class<?> dependency);

  /**
   * Tries to enqueue the given dependency (of currently populated bean which is returned by method
   * {@link #getCurrentBean()}) to populate (given dependency is not populated immediately). This
   * method uses method {@link #canPopulate} for deciding whatever the given dependency can be
   * enqueued.
   *
   * @param dependency the dependency of current bean (object) to populate.
   * @return <code>true</code> if given dependency was enqueued; <code>false</code> otherwise.
   */
  public boolean populate(Class<?> dependency);

  /**
   * Returns list of all populated (generated) instances by populator.
   *
   * @return List of all populated instances.
   */
  public List<Object> getPopulatedInstances();

}
