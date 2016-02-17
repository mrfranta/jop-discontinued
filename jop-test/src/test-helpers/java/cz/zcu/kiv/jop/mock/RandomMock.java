package cz.zcu.kiv.jop.mock;

import java.util.Random;

/**
 * Mocked random generator.
 *
 * @author Mr.FrAnTA
 */
public class RandomMock extends Random {
  /** Generated serial version UID. */
  private static final long serialVersionUID = 20160217L;

  /** The value of random generator. */
  private double value;

  /**
   * Constructs mocked random generator.
   *
   * @param value the value of random generator.
   */
  public RandomMock(double value) {
    this.value = value;
  }

  /**
   * Sets value returned by this generator in method {@link #nextDouble()}.
   *
   * @param value the value for this generator.
   */
  public void setValue(double value) {
    this.value = value;
  }

  /**
   * @return Returns mocked value of this random generator.
   */
  @Override
  public double nextDouble() {
    return value;
  }
}
