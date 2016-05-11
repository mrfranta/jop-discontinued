package cz.zcu.kiv.jop.example.bmi;

import cz.zcu.kiv.jop.example.domain.Person;

/**
 * Implementation of BMI calculator using equation: <code>weight / height^2</code>
 *
 * @author Mr.FrAnTA
 */
public class BmiCalculatorImpl implements BmiCalculator {

  /**
   * Calculates BMI of given person using equation: <code>weight / height^2</code>
   */
  public double calculate(Person person) throws BmiException {
    if (person == null || person.getHeight() <= 0 || person.getWeight() <= 0) {
      throw new BmiException("Cannot calculate BMI of given person: wrong parameters");
    }

    return person.getWeight() / Math.pow(person.getHeight(), 2);
  }

}
