package cz.zcu.kiv.jop.generator.clazz;

import javax.inject.Inject;
import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForName;
import cz.zcu.kiv.jop.generator.ValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.session.ClassLoaderSession;
import cz.zcu.kiv.jop.util.StringUtils;

/**
 * Implementation of class generator for annotation {@link TargetClassForName}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class TargetClassForNameGenerator implements ValueGenerator<Class<?>, TargetClassForName> {

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Class<Class<?>> getValueType() {
    return (Class<Class<?>>)(Class<?>)Class.class;
  }

  /**
   * Returns loaded class type with fully qualified name given in parameter
   * {@link TargetClassForName#value() value()} of given annotation. For loading of class is used
   * class loaded specified in parameter {@link TargetClassForName#classLoader() classLoader()}.
   *
   * @param params the parameters of random generator.
   * @return Loaded class type with fully qualified name given in parameter
   *         {@link TargetClassForName#value() value()}.
   * @throws ValueGeneratorException If given parameters are not valid or if class with given name
   *           was not found.
   */
  public Class<?> getValue(TargetClassForName params) throws ValueGeneratorException {
    if (params == null) {
      throw new ValueGeneratorException("Annotation cannot be null");
    }

    // class name
    String className = params.value();
    if (!StringUtils.hasText(className)) {
      throw new ValueGeneratorException("Class name cannot be null, empty or blank");
    }

    // class loader symbolic name
    String classLoaderName = params.classLoader();
    if (!StringUtils.hasText(params.classLoader())) {
      throw new ValueGeneratorException("Symbolic name of class loader cannot be null, empty or blank");
    }

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
    }

    try {
      return Class.forName(params.value(), params.initialize(), classLoader);
    }
    catch (ClassNotFoundException exc) {
      throw new ValueGeneratorException("Class '" + params.value() + "' was not found", exc);
    }

  }

  // ----- Injection part ------------------------------------------------------

  /** Session which stores class class loaders by their symbolic names. */
  protected ClassLoaderSession classLoaderSession;

  /**
   * Sets (injects) session which stores class class loaders by their symbolic names.
   *
   * @param classLoaderSession the session to set (inject).
   */
  @Inject
  public final void setClassLoaderSession(ClassLoaderSession classLoaderSession) {
    this.classLoaderSession = classLoaderSession;
  }

}
