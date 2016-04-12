package cz.zcu.kiv.jop.context;

/**
 * Implementation of dependencies graph which contains relations between populated objects (between
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
public class DependencyGraphImpl implements DependencyGraph {

}
