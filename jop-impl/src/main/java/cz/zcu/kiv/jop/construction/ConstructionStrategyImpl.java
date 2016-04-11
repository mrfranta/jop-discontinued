package cz.zcu.kiv.jop.construction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.inject.Singleton;

import cz.zcu.kiv.jop.bean.JopBean;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.property.Property;
import cz.zcu.kiv.jop.property.VirtualProperty;
import cz.zcu.kiv.jop.strategy.PopulatingStrategyInvoker;

/**
 * Default implementation of {@link ConstructionStrategy} which searches for usable constructor or
 * static construction method which is used for construction of object. Used constructor or
 * construction method may contains parameters (with populating annotations) which will be generated
 * according their annotations.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ConstructionStrategyImpl implements ConstructionStrategy {

  /**
   * Searches for usable constructor of given class. If given class contains only one constructor,
   * this constructor is returned. If given class contains more constructions, it searches for
   * constructor annotated by {@link cz.zcu.kiv.jop.annotation.Constructor Constructor} annotation.
   * In case that given class contains no (annotated) constructor it returns <code>null</code>.
   *
   * @param clazz the class which constructor will be returned.
   * @return Found constructor or <code>null</code>.
   * @throws ConstructionStrategyException If given class contains too many annotated constructors.
   */
  protected Constructor<?> findConstructor(Class<?> clazz) throws ConstructionStrategyException {
    Constructor<?>[] constructs = clazz.getDeclaredConstructors();

    // no constructors present
    if (constructs.length == 0) {
      return null;
    }

    // one constructor present
    if (constructs.length == 1) {
      return constructs[0];
    }

    // try to find marked constructor
    Constructor<?> markedConstructor = null;
    for (Constructor<?> construct : constructs) {
      if (construct.getAnnotation(cz.zcu.kiv.jop.annotation.Constructor.class) != null) {
        if (markedConstructor != null) {
          throw new ConstructionStrategyException("Too many annotated constructors in class: " + clazz.getName());
        }

        markedConstructor = construct;
      }
    }

    return markedConstructor;
  }

  /**
   * Searches for construction method annotated by {@link cz.zcu.kiv.jop.annotation.Constructor
   * Constructor} annotation in given class. Construction method has to be static and it may have
   * any type of modifier for access. In case that given class contains no construction method it
   * returns <code>null</code>.
   *
   * @param clazz the class which construction method will be returned.
   * @return Found static construction method or <code>null</code>.
   * @throws ConstructionStrategyException If given class contains too many annotated construction
   *           methods.
   */
  protected Method findConstructingMethod(Class<?> clazz) throws ConstructionStrategyException {
    Method[] methods = clazz.getDeclaredMethods();

    // find marked static method
    Method markedMethod = null;
    for (Method method : methods) {
      if (method.getAnnotation(cz.zcu.kiv.jop.annotation.Constructor.class) != null) {
        if (markedMethod != null) {
          throw new ConstructionStrategyException("Too many annotated construction methods in class: " + clazz.getName());
        }

        markedMethod = method;
      }
    }

    return markedMethod;
  }

  /**
   * Prepares parameters for invocation of construction or construction method.
   *
   * @param paramsTypes the array of class types of parameters.
   * @param paramsAnnotations the array of array of annotations annotating parameters.
   * @param context the populating context for preparation of parameters.
   * @return Prepared parameters for constructor or construction method invocation.
   * @throws Exception If some error occurs during parameters preparation.
   */
  protected Object[] prepareParameters(Class<?>[] paramsTypes, Annotation[][] paramsAnnotations, PopulatingContext context) throws Exception {
    Object[] params = new Object[paramsTypes.length];
    for (int i = 0; i < paramsTypes.length; i++) {
      // prepare virtual property
      Property<?> property = prepareParameterProperty(i, paramsTypes[i], paramsAnnotations[i]);

      // apply strategies to property
      populatingStrategyInvoker.applyStrategy(property, context);

      // store parameter value
      params[i] = property.getGetter().get(null);
    }

    return params;
  }

  /**
   * Prepares property for parameter of constructor or construction method.
   *
   * @param index the index of parameter.
   * @param parameterType the class type of parameter.
   * @param annotations the array of annotations annotating parameter.
   * @return Prepared property for parameter.
   */
  protected <T> Property<T> prepareParameterProperty(int index, Class<T> parameterType, Annotation[] annotations) {
    return new VirtualProperty<T>("arg" + index, parameterType, annotations);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <T> T constructObject(Class<T> objClass, PopulatingContext context) throws ConstructionStrategyException {
    if (!JopBean.isSupported(objClass)) {
      throw new ConstructionStrategyException("Unsupported class type: " + objClass.getName());
    }

    String className = objClass.getName();

    Constructor<?> construct = findConstructor(objClass);
    Method constructMethod = null;

    if (construct == null) {
      constructMethod = findConstructingMethod(objClass);
    }
    else if (construct.getAnnotation(cz.zcu.kiv.jop.annotation.Constructor.class) == null) {
      constructMethod = findConstructingMethod(objClass);

      // found annotated method, constructor is not relevant
      if (constructMethod != null) {
        construct = null;
      }
    }
    else {
      if (findConstructingMethod(objClass) != null) {
        throw new ConstructionStrategyException("Too many annotated constructors and construction methods in class: " + className);
      }
    }

    if (constructMethod == null && construct == null) {
      throw new ConstructionStrategyException("No (annotated) constructor or construction method found for class: " + className);
    }

    Object[] params = null;

    if (construct != null) {
      try {
        params = prepareParameters(construct.getParameterTypes(), construct.getParameterAnnotations(), context);
      }
      catch (Exception exc) {
        throw new ConstructionStrategyException("Cannot parameters for constructor of class: " + className, exc);
      }

      if (!construct.isAccessible()) {
        construct.setAccessible(true);
      }

      try {
        return (T)construct.newInstance(params);
      }
      catch (Exception exc) {
        throw new ConstructionStrategyException("Cannot create instance of class: " + className, exc);
      }
    }

    try {
      params = prepareParameters(constructMethod.getParameterTypes(), constructMethod.getParameterAnnotations(), context);
    }
    catch (Exception exc) {
      throw new ConstructionStrategyException("Cannot parameters for constructor method of class: " + className, exc);
    }

    if (!constructMethod.isAccessible()) {
      constructMethod.setAccessible(true);
    }

    try {
      return (T)constructMethod.invoke(null, params);
    }
    catch (Exception exc) {
      throw new ConstructionStrategyException("Cannot create instance of class: " + className, exc);
    }
  }

  //----- Injection part ------------------------------------------------------

  /** Interlayer (invoker) for populating strategies. */
  protected PopulatingStrategyInvoker populatingStrategyInvoker;

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
