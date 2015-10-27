package cz.zcu.kiv.jop;

import java.util.List;

/**
 * A main interface which allows easily generates objects which populates with
 * random data according to used annotations for object properties.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface ObjectPopulator {

  /**
   * Generate instance of the given <code>clazz</code> and populates only
   * properties which are not dependencies. The references to other (dependent)
   * properties will be set on <code>null</code>.
   *
   * @param clazz the class type for which will be generated and populated.
   * @return Generated and populated instance of given class.
   * @throws ObjectPopulatorException If some error occurs during generating or
   *           populating of instance.
   */
  public <T> T generate(Class<T> clazz) throws ObjectPopulatorException;

  /**
   *
   * @param clazz the class type for which will be generated and populated.
   * @param instancesCount the number of generated and populated instances.
   * @return List of generated and populated instances of given class.
   * @throws ObjectPopulatorException If some error occurs during generating or
   *           populating of instances.
   */
  public <T> List<T> generate(Class<T> clazz, int instancesCount) throws ObjectPopulatorException;

  /**
   * Generate instance of the given <code>clazz</code> and populates only
   * properties which are not dependencies. The references to other (dependent)
   * properties will be ignored (they will have default value or will be
   * <code>null</code>).
   *
   * @param clazz the class type for which will be generated and populated.
   * @return Generated and populated instance of given class.
   * @throws ObjectPopulatorException If some error occurs during generating or
   *           populating of instance.
   */
  public <T> T generateSingle(Class<T> clazz) throws ObjectPopulatorException;

  /**
   * Generates list of instances of the given <code>clazz</code> which will have
   * populated only properties which are not dependencies. The references to
   * other (dependent) properties will be ignored (they will have default value
   * or will be <code>null</code>).
   *
   * @param clazz the class type for which will be generated and populated.
   * @param instancesCount the number of generated and populated instances.
   * @return List of generated and populated instances of given class.
   * @throws ObjectPopulatorException If some error occurs during generating or
   *           populating of instances.
   */
  public <T> List<T> generateSingle(Class<T> clazz, int instancesCount) throws ObjectPopulatorException;

}
