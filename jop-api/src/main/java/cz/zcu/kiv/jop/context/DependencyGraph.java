package cz.zcu.kiv.jop.context;

/**
 * This interface serves as abstraction of graph of dependencies between populated objects (between
 * each other). Each object can be related (dependent) on any number of dependencies but it cannot
 * create cycle (example: A -&gt; B -&gt; A). Because of that this graph can be also called as tree.
 * <p>
 * This dependency graph may be part of {@link PopulatingContext} for which stores relations between
 * classes (dependencies) and prevents loops between classes. The populated objects has to create
 * tree (graph without loops).
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface DependencyGraph {

  /*
   * Note that this interface has no methods. It's caused by insufficient
   * idea of final form of dependency graph. The methods of this interface will
   * be added during implementation of this API.
   */

  // TODO Fill this interface by methods according the needs from implementation

}
