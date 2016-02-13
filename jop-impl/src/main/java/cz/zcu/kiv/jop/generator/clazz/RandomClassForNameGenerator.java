package cz.zcu.kiv.jop.generator.clazz;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClassForName;
import cz.zcu.kiv.jop.generator.CategoricalGenerator;
import cz.zcu.kiv.jop.generator.ValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.session.ClassLoaderSession;
import cz.zcu.kiv.jop.session.RandomGeneratorSession;
import cz.zcu.kiv.jop.util.StringUtils;

/**
 * Implementation of categorical class generator for annotation {@link RandomClassForName}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class RandomClassForNameGenerator implements ValueGenerator<Class<?>, RandomClassForName> {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(RandomClassForNameGenerator.class);

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Class<Class<?>> getValueType() {
    return (Class<Class<?>>)(Class<?>)Class.class;
  }

  /**
   * Returns loaded class type with random fully qualified name from parameter
   * {@link RandomClassForName#value() value()} of given annotation. The name of class is generated
   * by {@link CategoricalGenerator}. For loading of class is used class loaded specified in
   * parameter {@link RandomClassForName#classLoader() classLoader()}.
   *
   * @param params the parameters of random generator.
   * @return Loaded class type with random fully qualified name from parameter
   *         {@link RandomClassForName#value() value()}.
   * @throws ValueGeneratorException If given parameters are not valid or if class with given name
   *           was not found.
   */
  public Class<?> getValue(RandomClassForName params) throws ValueGeneratorException {
    if (params == null) {
      throw new ValueGeneratorException("Params cannot be null");
    }

    if (params.value() == null || params.value().length == 0) {
      throw new ValueGeneratorException("Params cannot be null");
    }

    // class loader symbolic name
    String classLoaderName = params.classLoader();
    if (!StringUtils.hasText(classLoaderName)) {
      throw new ValueGeneratorException("Symbolic name of class loader cannot be null, empty or blank");
    }

    Random rand = randomGeneratorSession.getRandomGenerator(params);
    String className = CategoricalGenerator.getValue(rand, params.value(), params.probabilities());

    // class loader
    ClassLoader classLoader = null;
    if (ClassLoaderConst.CALLER.equalsIgnoreCase(classLoaderName)) {
      classLoader = null; // default for caller
    }
    else if (ClassLoaderConst.CONTEXT.equalsIgnoreCase(classLoaderName)) {
      classLoader = Thread.currentThread().getContextClassLoader();
    }
    else if (ClassLoaderConst.SYSTEM.equalsIgnoreCase(classLoaderName)) {
      classLoader = ClassLoader.getSystemClassLoader();
    }
    else {
      classLoader = classLoaderSession.getClassLoader(classLoaderName);
      if (classLoader == null) {
        logger.warn("No class loader found for name '" + classLoaderName + "', it will be used called class loader.");
      }
    }

    try {
      return Class.forName(className, params.initialize(), classLoader);
    }
    catch (ClassNotFoundException exc) {
      throw new ValueGeneratorException("Class '" + className + "' was not found", exc);
    }
  }

  // ----- Injection part ------------------------------------------------------

  /** Session which stores class class loaders by their symbolic names. */
  protected ClassLoaderSession classLoaderSession;
  /** Session which stores random generators. */
  protected RandomGeneratorSession randomGeneratorSession;

  /**
   * Sets (injects) session which stores class class loaders by their symbolic names.
   *
   * @param classLoaderSession the session to set (inject).
   */
  @Inject
  public final void setClassLoaderSession(ClassLoaderSession classLoaderSession) {
    this.classLoaderSession = classLoaderSession;
  }

  /**
   * Sets (injects) session which stores random generators.
   *
   * @param randomGeneratorSession the session to set (inject).
   */
  @Inject
  public final void setRandomGeneratorSession(RandomGeneratorSession randomGeneratorSession) {
    this.randomGeneratorSession = randomGeneratorSession;
  }

}
