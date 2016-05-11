package cz.zcu.kiv.jop.example.bmi;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.jop.ObjectPopulator;
import cz.zcu.kiv.jop.ObjectPopulatorProvider;
import cz.zcu.kiv.jop.example.domain.Child;
import cz.zcu.kiv.jop.example.domain.Parent;
import cz.zcu.kiv.jop.example.domain.Person;

/**
 * Test of class {@link BmiCalculatorImpl}
 *
 * @author Mr.FrAnTA
 */
public class BmiCalculatorImplTest {

  /** Calculator of BMI. */
  private static BmiCalculatorImpl calculator;
  /** Data set of persons. */
  private static List<Person> persons;

  /** Number of generated children. */
  private static final int CHILDREN_COUNT = 150;
  /** Number of generated parents. */
  private static final int PARENTS_COUNT = 25;

  /**
   * Prepares test data.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    calculator = new BmiCalculatorImpl();
    persons = generatePersons(CHILDREN_COUNT, PARENTS_COUNT);
  }

  /**
   * Release test data.
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    calculator = null;

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
   * Tests method {@link BmiCalculatorImpl#calculate(Person)} for invalid persons.
   */
  @Test
  public void testCalculateBmiForIncorrectPerson() throws Exception {
    List<Person> wrongPersons = generatePersons(4, 0);
    wrongPersons.get(0).setHeight(0);
    wrongPersons.get(1).setHeight(-42);
    wrongPersons.get(2).setWeight(0);
    wrongPersons.get(3).setWeight(-42);
    wrongPersons.add(null);

    for (Person person : wrongPersons) {
      try {
        calculator.calculate(person);
        Assert.fail("Person should be invalid and exception should be thrown");
      }
      catch (BmiException exc) {
        // this is valid state
      }
    }
  }

  /**
   * Tests method {@link BmiCalculatorImpl#calculate(Person)} for generated persons.
   */
  @Test
  public void testCalculate() throws Exception {
    for (Person person : persons) {
      double weight = person.getWeight();
      double heightSq = person.getHeight() * person.getHeight();
      double expected = weight / heightSq;
      Assert.assertEquals(expected, calculator.calculate(person), 0.01);
    }

  }
}
