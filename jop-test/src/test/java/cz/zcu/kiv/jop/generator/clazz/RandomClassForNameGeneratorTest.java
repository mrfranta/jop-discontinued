package cz.zcu.kiv.jop.generator.clazz;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.AbstractContextTest;
import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClassForName;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClassForNameImpl;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.guice.MockModule;
import cz.zcu.kiv.jop.mock.RandomMock;
import cz.zcu.kiv.jop.session.ClassLoaderSession;
import cz.zcu.kiv.jop.session.RandomGeneratorSession;

/**
 * Test of class {@link RandomClassForNameGenerator}.
 *
 * @author Mr.FrAnTA
 */
public class RandomClassForNameGeneratorTest extends AbstractContextTest {

  /**
   * Test of method {@link RandomClassForNameGenerator#getValueType()} which has to return class of
   * class type.
   */
  @Test
  public void testGetValueType() {
    Assert.assertEquals(Class.class, randomClassForNameGenerator.getValueType());
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given null value. Expected
   * {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNull() throws ValueGeneratorException {
    randomClassForNameGenerator.getValue(null);
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with null
   * value. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNullValue() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final RandomClassForName randomClassForName = new RandomClassForNameImpl();

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomClassForName)));
        will(returnValue(new RandomMock(0.3))); // it will return first value
      }
    });

    /*----- Execution & Verify -----*/
    randomClassForNameGenerator.getValue(randomClassForName);
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with empty
   * value (array). Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForEmptyValue() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final RandomClassForName randomClassForName = new RandomClassForNameImpl(new String[0]);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomClassForName)));
        will(returnValue(new RandomMock(0.3))); // it will return first value
      }
    });

    /*----- Execution & Verify -----*/
    randomClassForNameGenerator.getValue(randomClassForName);
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with null
   * symbolic name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNullClassLoader() throws ValueGeneratorException {
    randomClassForNameGenerator.getValue(new RandomClassForNameImpl(new String[] {Integer.class.getName()}, true, null));
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with empty
   * symbolic name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForEmptyClassLoader() throws ValueGeneratorException {
    randomClassForNameGenerator.getValue(new RandomClassForNameImpl(new String[] {Integer.class.getName()}, true, ""));
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with blank
   * symbolic name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForBlankClassLoader() throws ValueGeneratorException {
    randomClassForNameGenerator.getValue(new RandomClassForNameImpl(new String[] {Integer.class.getName()}, true, " "));
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with symbolic
   * name {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test
  public void testGetValueForCallerClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    String[] values = new String[] {Integer.class.getName(), Long.class.getName()};
    double[] probabilities = new double[] {0.5, 0.5};
    final RandomClassForName randomClassForName = new RandomClassForNameImpl(values, probabilities, true, ClassLoaderConst.CALLER);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomClassForName)));
        will(returnValue(new RandomMock(0.3))); // it will return first value
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(randomClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with symbolic
   * name {@link ClassLoaderConst#CONTEXT} of class loader.
   */
  @Test
  public void testGetValueForContextClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    String[] values = new String[] {Integer.class.getName(), Long.class.getName()};
    double[] probabilities = new double[] {0.5, 0.5};
    final RandomClassForName randomClassForName = new RandomClassForNameImpl(values, probabilities, true, ClassLoaderConst.CONTEXT);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomClassForName)));
        will(returnValue(new RandomMock(0.7))); // it will return second value
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(randomClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Long.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with symbolic
   * name {@link ClassLoaderConst#SYSTEM} of class loader.
   */
  @Test
  public void testGetValueForSystemClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    String[] values = new String[] {Integer.class.getName(), Long.class.getName()};
    double[] probabilities = new double[] {0.5, 0.5};
    final RandomClassForName randomClassForName = new RandomClassForNameImpl(values, probabilities, true, ClassLoaderConst.SYSTEM);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomClassForName)));
        will(returnValue(new RandomMock(0.5))); // it will return first value
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(randomClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with symbolic
   * name of class loader which is stored in session.
   */
  @Test
  public void testGetValueForStoredClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final String name = "NAME";
    String[] values = new String[] {Integer.class.getName(), Long.class.getName()};
    double[] probabilities = new double[] {0.5, 0.5};
    final RandomClassForName randomClassForName = new RandomClassForNameImpl(values, probabilities, true, name);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomClassForName)));
        will(returnValue(new RandomMock(0.45))); // it will return first value

        oneOf(getInstance(ClassLoaderSession.class)).getClassLoader(with(equal(name)));
        will(returnValue(Thread.currentThread().getContextClassLoader()));
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(randomClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with symbolic
   * name of class loader which is not stored in session.
   */
  @Test
  public void testGetValueForNotStoredClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final String name = "NAME";
    String[] values = new String[] {Integer.class.getName(), Long.class.getName()};
    double[] probabilities = new double[] {0.5, 0.5};
    final RandomClassForName randomClassForName = new RandomClassForNameImpl(values, probabilities, true, name);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomClassForName)));
        will(returnValue(new RandomMock(0.51))); // it will return second value

        oneOf(getInstance(ClassLoaderSession.class)).getClassLoader(with(equal(name)));
        will(returnValue(null));
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(randomClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Long.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for non-existing class in given
   * annotation with symbolic name {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNonExistingClass() throws ValueGeneratorException {
    /*----- Preparation -----*/
    String[] values = new String[] {"java.lang.Foo", "java.lang.Bar"};
    final RandomClassForName randomClassForName = new RandomClassForNameImpl(values);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(randomClassForName)));
        will(returnValue(new RandomMock(0.51))); // it will return second value
      }
    });

    /*----- Execution & Verify -----*/
    randomClassForNameGenerator.getValue(randomClassForName);
  }

  // ------------------------------ context ------------------------------------

  /** Tested class generator. */
  private RandomClassForNameGenerator randomClassForNameGenerator;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void prepareInstances() {
    // prepare testing instance
    randomClassForNameGenerator = injector.getInstance(RandomClassForNameGenerator.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContextUnitSupport createUnitTestContext() {
    // @formatter:off
    return new ContextUnitSupport(
        new RandomClassForNameModule(mockery)
    );
    // @formatter:on
  }

  /**
   * Google Guice module for context of this test.
   *
   * @author Mr.FrAnTA
   */
  private class RandomClassForNameModule extends MockModule {
    /**
     * Constructs module with given mockery.
     *
     * @param mockery the mockery which will be used for mocking.
     */
    public RandomClassForNameModule(Mockery mockery) {
      super(mockery);
    }

    /**
     * Configures mocked bindings.
     */
    @Override
    protected void configure() {
      bindMock(ClassLoaderSession.class);
      bindMock(RandomGeneratorSession.class);
    }
  }

}
