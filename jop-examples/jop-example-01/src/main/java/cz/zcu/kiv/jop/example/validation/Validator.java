package cz.zcu.kiv.jop.example.validation;

/**
 * Simple validation API.
 *
 * @author Mr.FrAnTA
 */
public interface Validator {

  /**
   * Returns whatever instance of objects declared by given class can be given for validation.
   *
   * @param clazz the class type for check.
   * @return <code>true</code> if objects declared by given class can be validated;
   *         <code>false</code> otherwise.
   */
  public boolean supports(Class<?> clazz);

  /**
   * Validates given object and in case that given object is not valid, the exception may be thrown.
   *
   * @param obj the object for validation.
   * @throws ValidationException In case that the given object is not valid.
   */
  public void validate(Object obj) throws ValidationException;

}
