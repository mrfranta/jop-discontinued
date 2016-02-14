package cz.zcu.kiv.jop.ioc.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;

/**
 * Module which allows to override bindings from {@link CoreModule} or allows to add custom bindings
 * into IoC of this library. The explicit bindings are loaded from file {@value #IOC_FILE} in
 * classpath. The file can be present in root of classpath of in same package.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public class ExplicitBindingsModule extends AbstractModule {

  /** Logger used for logging. */
  private static final Log logger = LogFactory.getLog(ExplicitBindingsModule.class);

  /** Constant for file name with explicit bindings. */
  public static final String IOC_FILE = "ioc.properties";

  /**
   * Loads explicit bindings from properties file {@value #IOC_FILE} which may be placed into root
   * of classpath or into same package. The structure of file is very simple:
   *
   * <pre>
   * fully.qualified.class.name.Class = fully.qualified.class.name.Implementation
   * </pre>
   *
   * @return Loaded map of explicit bindings (key = class, value = implementation).
   */
  protected Map<String, String> loadExplicitBindings() {
    final Map<String, String> bindings = new HashMap<String, String>();

    InputStream is = getClass().getResourceAsStream("/" + IOC_FILE);
    if (is == null) { // try search in current package
      is = getClass().getResourceAsStream(IOC_FILE);
      if (is == null) {
        logger.debug("File '" + IOC_FILE + "' with explicit bindings was not found in classpath");
        return bindings;
      }
    }

    // load custom bindings from IOC file
    Properties bindingProps = new Properties();
    try {
      bindingProps.load(is);
    }
    catch (IOException exc) {
      logger.error("Cannot load explicit bindings from file: " + IOC_FILE, exc);
      addError(exc);
      return bindings;
    }
    finally {
      if (is != null) {
        try {
          is.close();
        }
        catch (IOException exc) {
          // ignored, not important now
        }
      }
    }

    // copy into map of String -> String
    for (Map.Entry<Object, Object> property : bindingProps.entrySet()) {
      bindings.put((String)property.getKey(), (String)property.getValue());
    }

    return bindings;
  }

  /**
   * Helper method which loads classes for given <code>className</code> and
   * <code>implementationName</code>. These names has to be fully qualified names for classes. If
   * the classes are loaded successfully, they are checked whatever the implementation can be
   * assigned to class and then is created the binding between of them.
   *
   * @param className the fully qualified name of class for binding.
   * @param implementationName the fully qualified name of implementation of class for binding.
   */
  @SuppressWarnings("unchecked")
  protected void createBinding(String className, String implementationName) {
    try {
      Class<?> clazz = Class.forName(className);
      Class<?> implementation = Class.forName(implementationName);

      if (!clazz.isAssignableFrom(implementation)) {
        String message = "Class " + implementation.getName() + " cannot be bound as implementation of " + clazz.getName();
        logger.error(message);
        addError(message);
        return;
      }

      bind((Class<Object>)clazz).to(implementation);
    }
    catch (ClassNotFoundException exc) {
      String message = "Class not found: " + exc.getMessage();
      logger.error(message);
      addError(message);
    }
  }

  /**
   * Configures module - binds loaded explicit bindings from file {@value #IOC_FILE}.
   */
  @Override
  public void configure() {
    Map<String, String> explicitBindings = loadExplicitBindings();
    for (Map.Entry<String, String> binding : explicitBindings.entrySet()) {
      createBinding(binding.getKey(), binding.getValue());
    }
  }

}
