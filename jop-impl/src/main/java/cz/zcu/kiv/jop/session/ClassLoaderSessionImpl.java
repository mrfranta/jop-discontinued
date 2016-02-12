package cz.zcu.kiv.jop.session;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.util.Preconditions;
import cz.zcu.kiv.jop.util.StringUtils;

/**
 * Implementation of session which stores mapped class loaders to symbolic unique names. This
 * session primary servers for possibility of using own class loaders for loading of classes by
 * their fully qualified names.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ClassLoaderSessionImpl implements ClassLoaderSession {

  /**
   * Map (session) of stored class loaders.
   */
  protected final Map<String, ClassLoader> session = new HashMap<String, ClassLoader>();

  /**
   * {@inheritDoc}
   */
  public ClassLoader setClassLoader(String name, ClassLoader classLoader) {
    Preconditions.checkArgument(StringUtils.hasText(name), "Symbolic name cannot be null, empty or blank");
    Preconditions.checkArgument(checkReservedNames(name), "Symbolic name cannot be '%s'", name);

    return session.put(name, classLoader);
  }

  /**
   * {@inheritDoc}
   */
  public ClassLoader getClassLoader(String name) {
    Preconditions.checkArgument(StringUtils.hasText(name), "Symbolic name cannot be null, empty or blank");
    Preconditions.checkArgument(checkReservedNames(name), "Symbolic name cannot be '%s'", name);

    return session.get(name);
  }

  /**
   * Checks given name whatever is one of reserved symbolic name for class loaders.
   *
   * @param name the symbolic name of class loader.
   * @return <code>true</code> if given name is reserved; <code>false</code> otherwise.
   */
  protected boolean checkReservedNames(String name) {
    // @formatter:off
    return !(ClassLoaderConst.CALLER.equalsIgnoreCase(name)
            || ClassLoaderConst.CONTEXT.equalsIgnoreCase(name)
            || ClassLoaderConst.SYSTEM.equalsIgnoreCase(name));
    // @formatter:on
  }

  /**
   * {@inheritDoc}
   */
  public void clear() {
    session.clear();
  }

}
