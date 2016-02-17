package cz.zcu.kiv.jop.generator.clazz;

import java.lang.annotation.Annotation;
import java.util.Random;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClassForName;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.Injector;
import cz.zcu.kiv.jop.ioc.guice.MockModule;
import cz.zcu.kiv.jop.session.ClassLoaderSession;
import cz.zcu.kiv.jop.session.RandomGeneratorSession;

/**
 * Test of class {@link RandomClassForNameGenerator}.
 *
 * @author Mr.FrAnTA
 */
public class RandomClassForNameGeneratorTest {

  /** Tested class generator. */
  private RandomClassForNameGenerator randomClassForNameGenerator;

  /**
   * Preparations before test.
   */
  @Before
  public void setUp() {
    prepareContext();
  }

  /**
   * Cleanup after test.
   */
  @After
  public void tearDown() {
    mockery.assertIsSatisfied();
  }

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
    randomClassForNameGenerator.getValue(new RandomClassForNameImpl());
  }

  /**
   * Test of method {@link RandomClassForNameGenerator#getValue} for given annotation with empty
   * value (array). Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForEmptyValue() throws ValueGeneratorException {
    randomClassForNameGenerator.getValue(new RandomClassForNameImpl(new String[0]));
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
    final RandomClassForName targetClassForName = new RandomClassForNameImpl(values, probabilities, true, ClassLoaderConst.CALLER);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(targetClassForName)));
        will(returnValue(new RandomMock(0.3))); // it will return first value
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(targetClassForName);

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
    final RandomClassForName targetClassForName = new RandomClassForNameImpl(values, probabilities, true, ClassLoaderConst.CONTEXT);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(targetClassForName)));
        will(returnValue(new RandomMock(0.7))); // it will return second value
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(targetClassForName);

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
    final RandomClassForName targetClassForName = new RandomClassForNameImpl(values, probabilities, true, ClassLoaderConst.SYSTEM);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(targetClassForName)));
        will(returnValue(new RandomMock(0.5))); // it will return first value
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(targetClassForName);

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
    final RandomClassForName targetClassForName = new RandomClassForNameImpl(values, probabilities, true, name);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(targetClassForName)));
        will(returnValue(new RandomMock(0.45))); // it will return first value

        oneOf(getInstance(ClassLoaderSession.class)).getClassLoader(with(equal(name)));
        will(returnValue(Thread.currentThread().getContextClassLoader()));
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(targetClassForName);

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
    final RandomClassForName targetClassForName = new RandomClassForNameImpl(values, probabilities, true, name);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(targetClassForName)));
        will(returnValue(new RandomMock(0.51))); // it will return second value

        oneOf(getInstance(ClassLoaderSession.class)).getClassLoader(with(equal(name)));
        will(returnValue(null));
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = randomClassForNameGenerator.getValue(targetClassForName);

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
    final RandomClassForName targetClassForName = new RandomClassForNameImpl(values);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(RandomGeneratorSession.class)).getRandomGenerator(with(equal(targetClassForName)));
        will(returnValue(new RandomMock(0.51))); // it will return second value
      }
    });

    /*----- Execution & Verify -----*/
    randomClassForNameGenerator.getValue(targetClassForName);
  }

  /**
   * Mocked random generator.
   *
   * @author Mr.FrAnTA
   */
  private static class RandomMock extends Random {
    /** Generated serial version UID. */
    private static final long serialVersionUID = 4365645541423931887L;

    /** The value of random generator. */
    private final double value;

    /**
     * Constructs mocked random generator.
     *
     * @param value the value of random generator.
     */
    public RandomMock(double value) {
      this.value = value;
    }

    /**
     * @return Returns value with which was created random generator.
     */
    @Override
    public double nextDouble() {
      return value;
    }
  }

  /**
   * Implementation of annotation {@link RandomClassForName}.
   *
   * @author Mr.FrAnTA
   */
  private static class RandomClassForNameImpl implements RandomClassForName {

    /** Value of annotation. */
    private final String[] value;
    /** Probabilities of annotation. */
    private final double[] probabilities;
    /** Information whether the class will be initialized. */
    private final boolean initialize;
    /** Symbolic name of class loader. */
    private final String classLoader;

    /**
     * Constructs the annotation.
     */
    public RandomClassForNameImpl() {
      this(null, new double[0], true, ClassLoaderConst.CALLER);
    }

    /**
     * Constructs the annotation.
     *
     * @param value the value of annotation.
     */
    public RandomClassForNameImpl(String... value) {
      this(value, new double[0], true, ClassLoaderConst.CALLER);
    }

    /**
     * Constructs the annotation.
     *
     * @param value the value of annotation.
     * @param probabilities probabilities of annotation.
     * @param initialize information whether the class will be initialized.
     * @param classLoader symbolic name of class loader.
     */
    public RandomClassForNameImpl(String[] value, boolean initialize, String classLoader) {
      this(value, new double[0], initialize, classLoader);
    }

    /**
     * Constructs the annotation.
     *
     * @param value the value of annotation.
     * @param probabilities probabilities of annotation.
     * @param initialize information whether the class will be initialized.
     * @param classLoader symbolic name of class loader.
     */
    public RandomClassForNameImpl(String[] value, double[] probabilities, boolean initialize, String classLoader) {
      this.value = value;
      this.probabilities = probabilities;
      this.initialize = initialize;
      this.classLoader = classLoader;
    }

    /**
     * Returns type of annotation (this class).
     *
     * @return type of annotation.
     */
    @Override
    public Class<? extends Annotation> annotationType() {
      return RandomClassForName.class;
    }

    /**
     * @return Value of annotation.
     */
    @Override
    public String[] value() {
      return value;
    }

    /**
     * @return Probabilities of annotation.
     */
    @Override
    public double[] probabilities() {
      return probabilities;
    }

    /**
     * @return Information whether the class will be initialized.
     */
    @Override
    public boolean initialize() {
      return initialize;
    }

    /**
     * @return Symbolic name of class loader.
     */
    @Override
    public String classLoader() {
      return classLoader;
    }
  }

  // ------------------------------ context ------------------------------------

  /** Mockery for mocking dependencies. */
  private final Mockery mockery = new Mockery();
  /** Injector of dependencies. */
  private Injector injector;

  /**
   * Prepare unit test instances
   */
  private void prepareContext() {
    ContextUnitSupport contextUnitSupport = createUnitTestContext();
    injector = contextUnitSupport.createInjector();

    // prepare testing instance
    randomClassForNameGenerator = injector.getInstance(RandomClassForNameGenerator.class);
  }

  /**
   * Returns mocked instance of given class.
   *
   * @param mockedClass the mocked class type.
   * @return Instance of mocked class.
   */
  private <T> T getInstance(Class<T> mockedClass) {
    return injector.getInstance(mockedClass);
  }

  /**
   * Create jUnit context.
   *
   * @return the provided jUnit context.
   */
  private ContextUnitSupport createUnitTestContext() {
    // @formatter:off
    return new ContextUnitSupport(
        new RandomClassForNameModule(mockery)
    );
    // @formatter:on
  }

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
