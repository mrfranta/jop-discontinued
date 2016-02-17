package cz.zcu.kiv.jop.class_provider;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.AbstractContextTest;
import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClassForName;
import cz.zcu.kiv.jop.annotation.class_provider.RandomClassForNameImpl;
import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.guice.MockModule;
import cz.zcu.kiv.jop.mock.RandomMock;
import cz.zcu.kiv.jop.session.ClassLoaderSession;
import cz.zcu.kiv.jop.session.RandomGeneratorSession;

/**
 * Test of class {@link RandomClassForNameProvider}.
 *
 * @author Mr.FrAnTA
 */
public class RandomClassForNameProviderTest extends AbstractContextTest {

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given null value. Expected
   * {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNull() throws ClassProviderException {
    randomClassForNameProvider.get(null);
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with null value.
   * Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNullValue() throws ClassProviderException {
    randomClassForNameProvider.get(new RandomClassForNameImpl());
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with empty value
   * (array). Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForEmptyValue() throws ClassProviderException {
    randomClassForNameProvider.get(new RandomClassForNameImpl(new String[0]));
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with null symbolic
   * name of class loader. Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNullClassLoader() throws ClassProviderException {
    randomClassForNameProvider.get(new RandomClassForNameImpl(new String[] {Integer.class.getName()}, true, null));
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with empty symbolic
   * name of class loader. Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForEmptyClassLoader() throws ClassProviderException {
    randomClassForNameProvider.get(new RandomClassForNameImpl(new String[] {Integer.class.getName()}, true, ""));
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with blank symbolic
   * name of class loader. Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForBlankClassLoader() throws ClassProviderException {
    randomClassForNameProvider.get(new RandomClassForNameImpl(new String[] {Integer.class.getName()}, true, " "));
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test
  public void testGetForCallerClassLoader() throws ClassProviderException {
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
    Class<?> clazz = randomClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#CONTEXT} of class loader.
   */
  @Test
  public void testGetForContextClassLoader() throws ClassProviderException {
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
    Class<?> clazz = randomClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Long.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#SYSTEM} of class loader.
   */
  @Test
  public void testGetForSystemClassLoader() throws ClassProviderException {
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
    Class<?> clazz = randomClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with symbolic name
   * of class loader which is stored in session.
   */
  @Test
  public void testGetForStoredClassLoader() throws ClassProviderException {
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
    Class<?> clazz = randomClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for given annotation with symbolic name
   * of class loader which is not stored in session.
   */
  @Test
  public void testGetForNotStoredClassLoader() throws ClassProviderException {
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
    Class<?> clazz = randomClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Long.class, clazz);
  }

  /**
   * Test of method {@link RandomClassForNameProvider#get} for non-existing class in given
   * annotation with symbolic name {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNonExistingClass() throws ClassProviderException {
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
    randomClassForNameProvider.get(targetClassForName);
  }

  // ------------------------------ context ------------------------------------

  /** Tested class generator. */
  private RandomClassForNameProvider randomClassForNameProvider;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void prepareInstances() {
    // prepare testing instance
    randomClassForNameProvider = injector.getInstance(RandomClassForNameProvider.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContextUnitSupport createUnitTestContext() {
    // @formatter:off
    return new ContextUnitSupport(
        new RandomClassForNameProviderModule(mockery)
    );
    // @formatter:on
  }

  /**
   * Google Guice module for context of this test.
   *
   * @author Mr.FrAnTA
   */
  private class RandomClassForNameProviderModule extends MockModule {
    /**
     * Constructs module with given mockery.
     *
     * @param mockery the mockery which will be used for mocking.
     */
    public RandomClassForNameProviderModule(Mockery mockery) {
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
