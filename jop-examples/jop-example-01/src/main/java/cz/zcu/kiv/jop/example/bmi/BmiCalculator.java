package cz.zcu.kiv.jop.example.bmi;

import cz.zcu.kiv.jop.example.domain.Person;

/**
 * Interface for calculator of body mass index (BMI).
 *
 * @author Mr.FrAnTA
 *
 */
public interface BmiCalculator {

  /**
   * Calculates body mass index of given person.
   *
   * @param person the person for which will be calculated the BMI.
   * @return Calculated BMI of given person.
   * @throws BmiException If some error occurs during BMI calculation.
   */
  public double calculate(Person person) throws BmiException;

}
