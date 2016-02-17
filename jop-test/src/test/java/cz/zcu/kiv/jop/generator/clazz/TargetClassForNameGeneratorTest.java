package cz.zcu.kiv.jop.generator.clazz;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForName;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForNameImpl;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.Injector;
import cz.zcu.kiv.jop.ioc.guice.MockModule;
import cz.zcu.kiv.jop.session.ClassLoaderSession;

/**
 * Test of class {@link TargetClassForNameGenerator}.
 *
 * @author Mr.FrAnTA
 */
public class TargetClassForNameGeneratorTest {

  /** Tested class generator. */
  private TargetClassForNameGenerator targetClassForNameGenerator;

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
   * Test of method {@link TargetClassForNameGenerator#getValueType()} which has to return class of
   * class type.
   */
  @Test
  public void testGetValueType() {
    Assert.assertEquals(Class.class, targetClassForNameGenerator.getValueType());
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given null value. Expected
   * {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNull() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(null);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with null
   * value. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNullValue() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(null));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with empty
   * value. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForEmptyValue() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(""));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with blank
   * value. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForBlankValue() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(" "));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with null
   * symbolic name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNullClassLoader() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(Integer.class.getName(), true, null));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with empty
   * symbolic name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForEmptyClassLoader() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(Integer.class.getName(), true, ""));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with blank
   * symbolic name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForBlankClassLoader() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(Integer.class.getName(), true, " "));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with symbolic
   * name {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test
  public void testGetValueForCallerClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.CALLER);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameGenerator.getValue(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with symbolic
   * name {@link ClassLoaderConst#CONTEXT} of class loader.
   */
  @Test
  public void testGetValueForContextClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.CONTEXT);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameGenerator.getValue(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with symbolic
   * name {@link ClassLoaderConst#SYSTEM} of class loader.
   */
  @Test
  public void testGetValueForSystemClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.SYSTEM);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameGenerator.getValue(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with symbolic
   * name of class loader which is stored in session.
   */
  @Test
  public void testGetValueForStoredClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final String name = "NAME";
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, name);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(ClassLoaderSession.class)).getClassLoader(with(equal(name)));
        will(returnValue(Thread.currentThread().getContextClassLoader()));
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameGenerator.getValue(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for given annotation with symbolic
   * name of class loader which is not stored in session.
   */
  @Test
  public void testGetValueForNotStoredClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    final String name = "NAME";
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, name);

    /*----- Expectations -----*/
    mockery.checking(new Expectations() {
      {
        oneOf(getInstance(ClassLoaderSession.class)).getClassLoader(with(equal(name)));
        will(returnValue(null));
      }
    });

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameGenerator.getValue(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#getValue} for non-existing class in given
   * annotation with symbolic name {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetValueForNonExistingClass() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl("java.lang.Foo"));
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
    targetClassForNameGenerator = injector.getInstance(TargetClassForNameGenerator.class);
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
        new TargetClassForNameModule(mockery)
    );
    // @formatter:on
  }

  private class TargetClassForNameModule extends MockModule {
    /**
     * Constructs module with given mockery.
     *
     * @param mockery the mockery which will be used for mocking.
     */
    public TargetClassForNameModule(Mockery mockery) {
      super(mockery);
    }

    /**
     * Configures mocked bindings.
     */
    @Override
    protected void configure() {
      bindMock(ClassLoaderSession.class);
    }
  }

}
