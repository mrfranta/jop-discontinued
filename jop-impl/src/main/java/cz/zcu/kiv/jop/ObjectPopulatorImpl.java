package cz.zcu.kiv.jop;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.bean.Bean;
import cz.zcu.kiv.jop.construction.ConstructionStrategyInvoker;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.context.PopulatingContextHandler;
import cz.zcu.kiv.jop.context.PopulatingContextHandlerImpl;
import cz.zcu.kiv.jop.context.PopulatingQueueIterator;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.session.PopulatingSession;
import cz.zcu.kiv.jop.strategy.PopulatingStrategyInvoker;

/**
 * Implementation of {@link ObjectPopulator} interface which allows easily populates objects which
 * populates with random data according to used annotations for object properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ObjectPopulatorImpl implements ObjectPopulator {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(ObjectPopulatorImpl.class);

  /**
   * Constructs object populator.
   */
  ObjectPopulatorImpl() {}

  /**
   * {@inheritDoc}
   */
  public <T> T populate(Class<T> clazz) throws ObjectPopulatorException {
    return populate(clazz, true);
  }

  /**
   * {@inheritDoc}
   */
  public <T> List<T> populate(Class<T> clazz, int instancesCount) throws ObjectPopulatorException {
    if (instancesCount <= 0) {
      throw new ObjectPopulatorException("Number of instances has to be greater than 0");
    }

    List<T> instances = new ArrayList<T>();
    for (int i = 0; i < instancesCount; i++) {
      instances.add(populate(clazz, true));
    }

    return instances;
  }

  /**
   * {@inheritDoc}
   */
  public <T> T populateSingle(Class<T> clazz) throws ObjectPopulatorException {
    return populate(clazz, false);
  }

  /**
   * {@inheritDoc}
   */
  public <T> List<T> populateSingle(Class<T> clazz, int instancesCount) throws ObjectPopulatorException {
    if (instancesCount <= 0) {
      throw new ObjectPopulatorException("Number of instances has to be greater than 0");
    }

    List<T> instances = new ArrayList<T>();
    for (int i = 0; i < instancesCount; i++) {
      instances.add(populate(clazz, false));
    }

    return instances;
  }

  /**
   * The generic populating method which generates instance of the given <code>clazz</code> and then
   * perform population action according to given parameter <code>populateDependencies</code>.
   * <p>
   * If the parameter is <code>true</code> then populates all properties of <code>clazz</code>
   * instance.
   * <p>
   * If the parameter is <code>false</code> then populates only properties which are not
   * dependencies. The references to other (dependent) properties will be ignored (they will have
   * default value or will be <code>null</code>).
   *
   * @param clazz the class type for which will be generated and populated.
   * @param populateDependencies information whether may be populated dependencies of populated
   *          object(s).
   * @return Generated and populated instance of given class.
   * @throws ObjectPopulatorException If some error occurs during generating or populating of
   *           object.
   */
  @SuppressWarnings("unchecked")
  protected <T> T populate(Class<T> clazz, boolean populateDependencies) throws ObjectPopulatorException {
    if (clazz == null) {
      throw new ObjectPopulatorException("Class type cannot be null");
    }

    // create populating context handler
    PopulatingContextHandler contextHandler = createPopulatingContextHandler(populateDependencies);
    // get populating context
    PopulatingContext context = contextHandler.getPopulatingContext();

    try {
      logger.debug("Creating new instance of: " + clazz.getName());

      // constructs new instance of object for population
      Bean bean = constructionStrategyInvoker.constructObject(clazz, contextHandler.getPopulatingContext());

      // add created bean into population context
      contextHandler.setRootBean(bean);

      // iterate through all beans waiting for population
      PopulatingQueueIterator beansToPopulate = contextHandler.getPopulatingQueueIterator();
      while (beansToPopulate.hasFront()) {
        // get actual bean to populate
        Bean beanToPopulate = beansToPopulate.front();
        logger.debug("Populating bean: " + beanToPopulate);

        // populate properties
        for (Property<?> property : beanToPopulate.getProperties()) {
          populatingStrategyInvoker.applyStrategy(property, context);
        }

        // add populated object into sessions
        contextHandler.addPopulatedInstance(beanToPopulate.getInstance());
        populatingSession.addPopulatedInstance(beanToPopulate.getInstance());

        // remove object from iterator
        beansToPopulate.next();
      }

      contextHandler.invokeLazyPopulatingStrategies();

      return (T)bean.getInstance();
    }
    catch (Exception exc) {
      throw new ObjectPopulatorException("Population of class " + clazz.getName() + " failed", exc);
    }
  }

  /**
   * Creates new instance of populating context handler.
   *
   * @param populateDependencies information whether may be populated dependencies of populated
   *          object(s).
   * @return New instance of handler of populating context.
   */
  protected PopulatingContextHandler createPopulatingContextHandler(boolean populateDependencies) {
    return new PopulatingContextHandlerImpl(populateDependencies, populatingSession);
  }

  /**
   * {@inheritDoc}
   */
  public void clearPopulatingSession() {
    populatingSession.clear();
  }

  //----- Injection part ------------------------------------------------------

  /** Session for already populated (generated) objects. */
  protected PopulatingSession populatingSession;
  /** Interlayer (invoker) for construction strategies. */
  protected ConstructionStrategyInvoker constructionStrategyInvoker;
  /** Interlayer (invoker) for populating strategies. */
  protected PopulatingStrategyInvoker populatingStrategyInvoker;

  /**
   * Sets (injects) session of already populated (generated) objects.
   *
   * @param populatingSession the session to set (inject).
   */
  @Inject
  public final void setPopulatingSession(PopulatingSession populatingSession) {
    this.populatingSession = populatingSession;
  }

  /**
   * Sets (injects) interlayer (invoker) for construction strategies.
   *
   * @param constructionStrategyInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setConstructionStrategyInvoker(ConstructionStrategyInvoker constructionStrategyInvoker) {
    this.constructionStrategyInvoker = constructionStrategyInvoker;
  }

  /**
   * Sets (injects) interlayer (invoker) for populating strategies.
   *
   * @param populatingStrategyInvoker the interlayer (invoker) to set (inject).
   */
  @Inject
  public final void setPopulatingStrategyInvoker(PopulatingStrategyInvoker populatingStrategyInvoker) {
    this.populatingStrategyInvoker = populatingStrategyInvoker;
  }
}
