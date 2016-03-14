package cz.zcu.kiv.jop;

import java.util.List;

/**
 * A main interface which allows easily populates objects which populates with random data according
 * to used annotations for object properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ObjectPopulator {

  /**
   * Generate instance of the given <code>clazz</code> and populates only properties which are not
   * dependencies. The references to other (dependent) properties will be set on <code>null</code>.
   *
   * @param clazz the class type for which will be populated and populated.
   * @return Generated and populated instance of given class.
   * @throws ObjectPopulatorException If some error occurs during populating of instance.
   */
  public <T> T populate(Class<T> clazz) throws ObjectPopulatorException;

  /**
   *
   * @param clazz the class type for which will be populated and populated.
   * @param instancesCount the number of populated and populated instances.
   * @return List of populated and populated instances of given class.
   * @throws ObjectPopulatorException If some error occurs during populating of instances.
   */
  public <T> List<T> populate(Class<T> clazz, int instancesCount) throws ObjectPopulatorException;

  /**
   * Generate instance of the given <code>clazz</code> and populates only properties which are not
   * dependencies. The references to other (dependent) properties will be ignored (they will have
   * default value or will be <code>null</code>).
   *
   * @param clazz the class type for which will be populated and populated.
   * @return Generated and populated instance of given class.
   * @throws ObjectPopulatorException If some error occurs during populating of instance.
   */
  public <T> T populateSingle(Class<T> clazz) throws ObjectPopulatorException;

  /**
   * Generates list of instances of the given <code>clazz</code> which will have populated only
   * properties which are not dependencies. The references to other (dependent) properties will be
   * ignored (they will have default value or will be <code>null</code>).
   *
   * @param clazz the class type for which will be populated and populated.
   * @param instancesCount the number of populated and populated instances.
   * @return List of populated and populated instances of given class.
   * @throws ObjectPopulatorException If some error occurs during populating of instances.
   */
  public <T> List<T> populateSingle(Class<T> clazz, int instancesCount) throws ObjectPopulatorException;

}
