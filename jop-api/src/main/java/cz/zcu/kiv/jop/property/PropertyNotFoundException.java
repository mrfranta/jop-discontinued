package cz.zcu.kiv.jop.property;

/**
 * This exception may occur in case that <em>property</em> was not found.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public class PropertyNotFoundException extends PropertyException {

  /**
   * Determines if a de-serialized file is compatible with this class.
   * <p>
   * Maintainers must change this value if and only if the new version of this
   * class is not compatible with old versions. See Oracle docs for <a
   * href="http://docs.oracle.com/javase/1.5.0/docs/guide/
   * serialization/">details</a>.
   * <p>
   * Not necessary to include in first version of the class, but included here
   * as a reminder of its importance.
   */
  private static final long serialVersionUID = 20151026L;

  /**
   * Constructs an exception.
   *
   * @param objectClass class type of a property owner.
   * @param propertyName name of property.
   */
  public PropertyNotFoundException(Class<?> objectClass, String propertyName) {
    super(objectClass, propertyName);
  }

  /**
   * Returns default message in case that the detail message given in exception
   * constructor is <code>null</code>.
   *
   * @return Default message of exception.
   */
  @Override
  protected String getDefaultMessage() {
    return "Could not find a property " + propertyName + " in class " + objectClass.getName();
  }
}
