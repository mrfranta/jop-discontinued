package cz.zcu.kiv.jop.example.validation;

import java.util.Date;

import cz.zcu.kiv.jop.example.domain.Person;

/**
 * Simple validator of persons.
 *
 * @author Mr.FrAnTA
 */
public class PersonValidator implements Validator {

  /** Constant for minimal age of person. */
  public static final int MIN_AGE = 0;
  /** Constant for maximal age of person. */
  public static final int MAX_AGE = 130;

  /** Constant for minimal accepted height of person. */
  public static final int MIN_HEIGHT = 40;
  /** Constant for maximal accepted height of person. */
  public static final int MAX_HEIGHT = 220;

  /** Constant for minimal accepted weight of person. */
  public static final double MIN_WEIGHT = 2.0;
  /** Constant for maximal accepted weight of person. */
  public static final double MAX_WEIGHT = 400.0;

  /**
   * Returns <code>true</code> only in case that the object can be assigned to {@link Person}.
   */
  public boolean supports(Class<?> clazz) {
    return Person.class.isAssignableFrom(clazz);
  }

  /**
   * Validates given object of person.
   */
  public void validate(Object obj) throws ValidationException {
    if (obj == null || !supports(obj.getClass())) {
      throw new ValidationException("Unsupported object given");
    }

    Person person = (Person)obj;
    if (person.getAge() < MIN_AGE) {
      throw new ValidationException("Person's age is too low");
    }

    if (person.getAge() > MAX_AGE) {
      throw new ValidationException("Person's age is too high");
    }

    if (person.getHeight() < MIN_HEIGHT) {
      throw new ValidationException("Person's height is too low");
    }

    if (person.getHeight() > MAX_HEIGHT) {
      throw new ValidationException("Person's height is too high");
    }

    if (person.getWeight() < MIN_WEIGHT) {
      throw new ValidationException("Person's weight is too low");
    }

    if (person.getWeight() > MAX_WEIGHT) {
      throw new ValidationException("Person's weight is too high");
    }

    if (person.getGender() == null) {
      throw new ValidationException("Person has to have gender");
    }

    if (person.getBirthDate() == null || new Date().before(person.getBirthDate())) {
      throw new ValidationException("Incorrect person's birth date");
    }

    if (person.getHairColor() == null) {
      throw new ValidationException("Person has to have some hair color");
    }
  }

}
