package cz.zcu.kiv.jop.context;

import java.util.List;

import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.session.PopulatingSession;
import cz.zcu.kiv.jop.strategy.PopulatingStrategy;
import cz.zcu.kiv.jop.strategy.PopulatingStrategyException;

/**
 * The interface for populating context which stores all required information about current ongoing
 * population of some object. Context contains currently populated (generated) object and list of
 * all already populated (generated) objects. It also contains the queue of lazy populating
 * strategies invocation.
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
   * Returns instance of currently populated object (bean). It may returns <code>null</code> in case
   * that there is no object to populate.
   *
   * @return Instance of currently populated object or <code>null</code>.
   */
  public Object getCurrentObject();

  /**
   * Returns currently populated bean descriptor for currently populated object returned by method
   * {@link #getCurrentObject()}. It may returns <code>null</code> in case that there is no object
   * to populate.
   *
   * @return Currently populated bean or <code>null</code>.
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
  public boolean populate(Object dependency);

  /**
   * Tries to enqueue the given bean descriptor of dependency (of currently populated bean which is
   * returned by method {@link #getCurrentBean()}) to populate (given dependency is not populated
   * immediately). This method uses method {@link #canPopulate} for deciding whatever the given
   * dependency can be enqueued.
   *
   * @param dependency the bean descriptor of dependency of current bean (object) to populate.
   * @return <code>true</code> if given dependency was enqueued; <code>false</code> otherwise.
   */
  public boolean populate(Bean dependency);

  /**
   * Adds invocation of given lazy populating strategy which will be performed in the end of
   * population process.
   *
   * @param property the property for which will be performed invocation of lazy populating
   *          strategy.
   * @param strategy the lazy populating strategy to add.
   * @throws PopulatingStrategyException If some error occurs during adding or invoking of lazy
   *           strategies.
   */
  public void addLazyPopulatingStrategyInvocation(Property<?> property, PopulatingStrategy strategy) throws PopulatingStrategyException;

  /**
   * Returns list of populated (generated) instances. According to parameter
   * <code>includeGlobalInstances</code> returns list of all instances populated only in scope of
   * this populating context or list of all populated instances including already populated
   * instances in previous calls of {@link cz.zcu.kiv.jop.ObjectPopulator ObjectPopulator}.
   *
   * @param includeGlobalInstances information whether will be included instances from previous
   *          calls of ObjectPopulator.
   * @return List of populated instances.
   */
  public List<Object> getPopulatedInstances(boolean includeGlobalInstances);

  /**
   * Creates the construction context for given bean. The construction context should be created as
   * sub-context of this context (it should have shared graph, queue, session etc.) with given bean
   * as current bean (and bean instance as object). Construction context should not support the lazy
   * populating strategies invocation - they have to be invoked immediately.
   * <p>
   * Construction context is important for construction of new instances of objects which requires
   * populating context for population of their (constructor) parameters.
   *
   * @param bean the bean for which will be created construction context.
   * @return Construction context for given bean.
   */
  public PopulatingContext createConstructionContext(Bean bean);

}
