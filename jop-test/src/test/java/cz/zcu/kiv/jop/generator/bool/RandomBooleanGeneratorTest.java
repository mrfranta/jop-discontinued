package cz.zcu.kiv.jop.generator.bool;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Invocation;
import org.jmock.lib.action.ReturnValueAction;
import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.AbstractContextTest;
import cz.zcu.kiv.jop.annotation.generator.bool.RandomBoolean;
import cz.zcu.kiv.jop.annotation.generator.bool.RandomBooleanImpl;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.guice.MockModule;
import cz.zcu.kiv.jop.mock.RandomMock;
import cz.zcu.kiv.jop.session.RandomGeneratorSession;

/**
 * Test of class {@link RandomBooleanGenerator}.
 *
 * @author Mr.FrAnTA
 */
public class RandomBooleanGeneratorTest extends AbstractContextTest {

  /**
   * Test of method {@link RandomBooleanGenerator#getValueType()} which has to return class of class
   * type.
   */
  @Test
  public void testGetValueType() {
    Assert.assertEquals(Boolean.class, randomBooleanGenerator.getValueType());
  }

  /**
   * Test of method {@link RandomBooleanGenerator#getValue} for given null value. Expected
   * {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNull() throws ValueGeneratorException {
    randomBooleanGenerator.getValue(null);
  }

  /**
   * Test of method {@link RandomBooleanGenerator#getValue} for given annotation with negative
   * probability. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNegativeProbability() throws ValueGeneratorException {
    randomBooleanGenerator.getValue(new RandomBooleanImpl(-1.0));
  }

  /**
   * Test of method {@link RandomBooleanGenerator#getValue} for given annotation with too high
   * probability (p > 1.0). Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForHighProbability() throws ValueGeneratorException {
    randomBooleanGenerator.getValue(new RandomBooleanImpl(1.1));
  }

  /**
   * Test of method {@link RandomBooleanGenerator#getValue} for zero probability.
   */
  @Test
  public void testGetValueForZeroProbability() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final int numberOfCalls = 10;
    final RandomBoolean randomBoolean = new RandomBooleanImpl(0.0);

    /*----- Expectations -----*/
    // @formatter:off
    Boolean[] expected = new Boolean[] {
        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
    };
    // @formatter:on

    mockery.checking(new Expectations() {
      {
        exactly(numberOfCalls).of(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomBoolean)));
        will(new ReturnRandomAction());
      }
    });

    /*----- Execution -----*/
    Boolean[] values = new Boolean[numberOfCalls];
    for (int i = 0; i < numberOfCalls; i++) {
      values[i] = randomBooleanGenerator.getValue(randomBoolean);
    }

    /*----- Verify -----*/
    Assert.assertArrayEquals(expected, values);
  }

  /**
   * Test of method {@link RandomBooleanGenerator#getValue} for probability equals 0.50.
   */
  @Test
  public void testGetValueForSomeProbability() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final int numberOfCalls = 10;
    final RandomBoolean randomBoolean = new RandomBooleanImpl(0.5);

    /*----- Expectations -----*/
    // @formatter:off
    Boolean[] expected = new Boolean[] {
        Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
        Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE
    };
    // @formatter:on

    mockery.checking(new Expectations() {
      {
        exactly(numberOfCalls).of(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomBoolean)));
        will(new ReturnRandomAction());
      }
    });

    /*----- Execution -----*/
    Boolean[] values = new Boolean[numberOfCalls];
    for (int i = 0; i < numberOfCalls; i++) {
      values[i] = randomBooleanGenerator.getValue(randomBoolean);
    }

    /*----- Verify -----*/
    Assert.assertArrayEquals(expected, values);
  }

  /**
   * Test of method {@link RandomBooleanGenerator#getValue} for probability equals 1.0.
   */
  @Test
  public void testGetValueForOneProbability() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final int numberOfCalls = 10;
    final RandomBoolean randomBoolean = new RandomBooleanImpl(1.0);

    /*----- Expectations -----*/
    // @formatter:off
    Boolean[] expected = new Boolean[] {
        Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
        Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE
    };
    // @formatter:on

    mockery.checking(new Expectations() {
      {
        exactly(numberOfCalls).of(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomBoolean)));
        will(new ReturnRandomAction());
      }
    });

    /*----- Execution -----*/
    Boolean[] values = new Boolean[numberOfCalls];
    for (int i = 0; i < numberOfCalls; i++) {
      values[i] = randomBooleanGenerator.getValue(randomBoolean);
    }

    /*----- Verify -----*/
    Assert.assertArrayEquals(expected, values);
  }

  // ------------------------------ helpers ------------------------------------

  /**
   * Mock of return action which returns the mocked random generators. The mocked random generators
   * provides pseudo-random value <code>((n - 1) * 0.1)</code> where <code>n</code> is number of
   * call. The maximal number of <code>n</code> is 10.
   *
   * @author Mr.FrAnTA
   */
  private static class ReturnRandomAction extends ReturnValueAction {

    /** The number of call. */
    private int n = 1;

    /**
     * Constructs the return action.
     */
    public ReturnRandomAction() {
      super(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(Invocation invocation) throws Throwable {
      if (n > 10) {
        throw new IllegalStateException("n > 10");
      }

      return new RandomMock(((n++) - 1) / 10.0);
    }
  }

  // ------------------------------ context ------------------------------------

  /** Tested class generator. */
  private RandomBooleanGenerator randomBooleanGenerator;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void prepareInstances() {
    // prepare testing instance
    randomBooleanGenerator = injector.getInstance(RandomBooleanGenerator.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContextUnitSupport createUnitTestContext() {
    // @formatter:off
    return new ContextUnitSupport(
        new RandomBooleanGeneratorModule(mockery)
    );
    // @formatter:on
  }

  /**
   * Google Guice module for context of this test.
   *
   * @author Mr.FrAnTA
   */
  private class RandomBooleanGeneratorModule extends MockModule {
    /**
     * Constructs module with given mockery.
     *
     * @param mockery the mockery which will be used for mocking.
     */
    public RandomBooleanGeneratorModule(Mockery mockery) {
      super(mockery);
    }

    /**
     * Configures mocked bindings.
     */
    @Override
    protected void configure() {
      bindMock(RandomGeneratorSession.class);
    }
  }

}
