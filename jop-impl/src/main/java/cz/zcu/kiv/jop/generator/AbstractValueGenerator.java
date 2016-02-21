package cz.zcu.kiv.jop.generator;

import java.lang.annotation.Annotation;
import java.util.Random;

import javax.inject.Inject;

import cz.zcu.kiv.jop.session.RandomGeneratorSession;

/**
 * The abstract ancestor for value generators. This class contains helpers methods for checking
 * parameters of value generator. It also contains injected session for random generators and helper
 * methods for generating random numbers.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <T> Type of generated values.
 * @param <P> Annotation type of generator parameters.
 */
public abstract class AbstractValueGenerator<T, P extends Annotation> implements ValueGenerator<T, P> {

  /**
   * Returns stored random generator for given annotation.
   *
   * @param params the annotation for parameters of value generator.
   * @return Random generator stored in session for given annotation.
   * @throws ValueGeneratorException If session of random generators doesn't contains (or provides)
   *           the random generator for given annotation.
   */
  protected Random getRandomGenerator(P params) throws ValueGeneratorException {
    Random random = randomGeneratorSession.getRandomGenerator(params);
    if (random == null) {
      throw new ValueGeneratorException("No available random generator for parameters: " + params.getClass().getSimpleName());
    }

    return random;
  }

  /**
   * Generates pseudo-random double value between 0.0 (inclusive) and 1.0 (exclusive). This method
   * uses random generator stored in session of random generators for given annotation for
   * parameters of value generator.
   *
   * @param params the annotation for parameters of value generator.
   * @return Pseudo-random double value between 0.0 (inclusive) and 1.0 (exclusive).
   * @throws ValueGeneratorException If session of random generators doesn't contains (or provides)
   *           the random generator for given annotation.
   */
  protected double nextDouble(P params) throws ValueGeneratorException {
    return getRandomGenerator(params).nextDouble(); // null-safe
  }

  /**
   * Generates pseudo-random double value between given <code>min</code> (inclusive) and
   * <code>max</code> (inclusive). This method uses random generator stored in session of random
   * generators for given annotation for parameters of value generator.
   *
   * @param params the annotation for parameters of value generator.
   * @param min the minimal value for random value.
   * @param max the maximal value for random value.
   * @return Pseudo-random double value between <code>min</code> (inclusive) and <code>max</code>
   *         (inclusive).
   * @throws ValueGeneratorException If session of random generators doesn't contains (or provides)
   *           the random generator for given annotation.
   */
  protected double nextDouble(P params, double min, double max) throws ValueGeneratorException {
    Random rand = getRandomGenerator(params); // null-safe
    if (rand.nextDouble() >= 0.5) { // 1.0 inclusive
      return ((1 - Math.random()) * (max - min) + min);
    }

    return (Math.random() * (max - min) + min);
  }

  /**
   * Checks whatever parameters of value generator (<code>params</code>) are not <code>null</code>.
   * In case that given parameters are <code>null</code> the {@link ValueGeneratorException} is
   * thrown. This method also returns given parameters of value generator so checking of
   * <code>null</code> value can be used in this way:
   *
   * <pre>
   * public Boolean getValue(RandomBoolean params) throws ValueGeneratorException {
   *   double probability = checkParamsNotNull(params).probability();
   *   Random rand = getRandomGenerator(params);
   *
   *   return (rand.nextDouble() &lt; probability) ? Boolean.TRUE : Boolean.FALSE;
   * }
   * </pre>
   *
   * @param params the parameters of random generator which will be checked.
   * @return Given parameters of random generator.
   * @throws ValueGeneratorException If given parameters of random generator are <code>null</code>.
   */
  public static <P extends Annotation> P checkParamsNotNull(P params) throws ValueGeneratorException {
    if (params == null) {
      throw new ValueGeneratorException("Parameters of generator cannot be null");
    }

    return params;
  }

  /**
   * Check whatever the parameters of random generators meet given <code>condition</code> and if
   * not, the {@link ValueGeneratorException} is thrown. This method is useful for single-line
   * checking of random generator parameters. This method can be used in this way:
   *
   * <pre>
   * public Integer getValue(RandomAge params) throws ValueGeneratorException {
   *   checkParams(params.minAge() &gt; 0); // minimal possible age
   *   checkParams(params.maxAge() &lt;= 130); // maximal possible age
   *
   *   Random rand = getRandomGenerator(params);
   *   return params.minAge() + rand.nextInt(params.maxAge() - params.minAge() + 1);
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @throws ValueGeneratorException If given condition is not met.
   */
  public static void checkParams(boolean condition) throws ValueGeneratorException {
    if (!condition) {
      throw new ValueGeneratorException();
    }
  }

  /**
   * Check whatever the parameters of random generators meet given <code>condition</code> and if
   * not, the {@link ValueGeneratorException} with given error message is thrown. This method is
   * useful for single-line checking of random generator parameters. This method can be used in this
   * way:
   *
   * <pre>
   * public void setAge(int age) {
   *   checkParams(params.minAge() &gt; 0, &quot;Minimal age is too low&quot;); // minimal possible age
   *   checkParams(params.maxAge() &lt;= 130, &quot;Maximal age is too high&quot;); // maximal possible age
   *
   *   Random rand = getRandomGenerator(params);
   *   return params.minAge() + rand.nextInt(params.maxAge() - params.minAge() + 1);
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @param errorMessage the error message for thrown exception in case that given
   *          <code>condition</code> is not met.
   * @throws ValueGeneratorException If given condition is not met.
   */
  public static void checkParams(boolean condition, String errorMessage) throws ValueGeneratorException {
    if (!condition) {
      throw new ValueGeneratorException(errorMessage);
    }
  }

  /**
   * Check whatever the parameters of random generators meet given <code>condition</code> and if
   * not, the {@link ValueGeneratorException} with given formatted error message is thrown. This
   * method is useful for single-line checking of random generator parameters. This method can be
   * used in this way:
   *
   * <pre>
   * public void setAge(int age) {
   *   checkParams(params.minAge() &gt; 0, &quot;Minimal age %d is too low&quot;, params.minAge()); // minimal possible age
   *   checkParams(params.maxAge() &lt;= 130, &quot;Maximal age %d is too high&quot;, params.maxAge()); // maximal possible age
   *
   *   Random rand = getRandomGenerator(params);
   *   return params.minAge() + rand.nextInt(params.maxAge() - params.minAge() + 1);
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @param errorMessageFormat the formatted error message for thrown exception in case that given
   *          <code>condition</code> is not met.
   * @param errorMessageArgs the arguments for given formatted error message.
   * @throws ValueGeneratorException If given condition is not met.
   */
  public static void checkParams(boolean condition, String errorMessageFormat, Object... errorMessageArgs) throws ValueGeneratorException {
    if (!condition) {
      throw new ValueGeneratorException(String.format(errorMessageFormat, errorMessageArgs));
    }
  }

  // ----- Injection part ------------------------------------------------------

  /** Session which stores random generators. */
  protected RandomGeneratorSession randomGeneratorSession;

  /**
   * Sets (injects) session which stores random generators.
   *
   * @param randomGeneratorSession the session to set (inject).
   */
  @Inject
  public final void setRandomGeneratorSession(RandomGeneratorSession randomGeneratorSession) {
    this.randomGeneratorSession = randomGeneratorSession;
  }

}
