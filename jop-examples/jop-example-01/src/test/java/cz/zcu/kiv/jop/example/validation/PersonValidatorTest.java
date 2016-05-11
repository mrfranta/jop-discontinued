package cz.zcu.kiv.jop.example.validation;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.jop.ObjectPopulator;
import cz.zcu.kiv.jop.ObjectPopulatorProvider;
import cz.zcu.kiv.jop.example.domain.Child;
import cz.zcu.kiv.jop.example.domain.Gender;
import cz.zcu.kiv.jop.example.domain.Parent;
import cz.zcu.kiv.jop.example.domain.Person;

/**
 * Test of class {@link PersonValidator}
 *
 * @author Mr.FrAnTA
 */
public class PersonValidatorTest {

  /** Validator for persons validation. */
  private static PersonValidator validator;
  /** Data set of persons. */
  private static List<Person> persons;

  /** Number of generated children. */
  private static final int CHILDREN_COUNT = 400;
  /** Number of generated parents. */
  private static final int PARENTS_COUNT = 50;

  /**
   * Prepares test data.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    validator = new PersonValidator();
    persons = generatePersons(CHILDREN_COUNT, PARENTS_COUNT);
  }

  /**
   * Release test data.
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    validator = null;

    if (persons != null) {
      persons.clear();
    }

    persons = null;
  }

  /**
   * Prepares persons for test.
   *
   * @param childrenCount the number of generated children.
   * @param parentsCount the number of generated parents.
   * @return List of generated persons.
   * @throws Exception if some error occurs.
   */
  private static List<Person> generatePersons(int childrenCount, int parentsCount) throws Exception {
    // prepares list of persons
    List<Person> persons = new ArrayList<Person>();

    // prepares populator
    ObjectPopulator populator = ObjectPopulatorProvider.getObjectPopulator();
    // generate children
    if (childrenCount > 0) {
      persons.addAll(populator.populate(Child.class, childrenCount));
    }
    if (parentsCount > 0) {
      persons.addAll(populator.populate(Parent.class, parentsCount));
    }

    return persons;
  }

  /**
   * Test of method {@link PersonValidator#supports(Class)} for generated persons.
   */
  @Test
  public void testSupports() {
    for (Person person : persons) {
      Assert.assertTrue(validator.supports(person.getClass()));
    }
  }

  /**
   * Test of method {@link PersonValidator#supports(Class)} for different classes.
   */
  @Test
  public void testSupportsForDifferentClasses() {
    Assert.assertFalse(validator.supports(Object.class));
    Assert.assertFalse(validator.supports(Gender.class));
    Assert.assertTrue(validator.supports(Person.class));
    Assert.assertTrue(validator.supports(Child.class));
    Assert.assertTrue(validator.supports(Parent.class));
  }

  /**
   * Test of method {@link PersonValidator#validate(Object)} for generated persons.
   */
  @Test
  public void testValidate() throws ValidationException {
    for (Person person : persons) {
      validator.validate(person);
    }
  }

  /**
   * Test of method {@link PersonValidator#validate(Object)} for invalid person. Expected
   * {@link ValidationException}.
   */
  @Test(expected = ValidationException.class)
  public void testValidateForInvalidPerson() throws ValidationException {
    validator.validate(new Parent("Some name"));
  }

  /**
   * Test of method {@link PersonValidator#validate(Object)} for invalid object. Expected
   * {@link ValidationException}.
   */
  @Test(expected = ValidationException.class)
  public void testValidateForInvalidObject() throws ValidationException {
    validator.validate(new Object());
  }
}
