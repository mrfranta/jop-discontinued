package cz.zcu.kiv.jop.class_provider;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cz.zcu.kiv.jop.AbstractContextTest;
import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForName;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForNameImpl;
import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.guice.MockModule;
import cz.zcu.kiv.jop.session.ClassLoaderSession;

/**
 * Test of class {@link TargetClassForNameProvider}.
 *
 * @author Mr.FrAnTA
 */
public class TargetClassForNameProviderTest extends AbstractContextTest {

  /**
   * Preparations before test.
   */
  @Override
  @Before
  public void setUp() {
    prepareContext();
  }

  /**
   * Cleanup after test.
   */
  @Override
  @After
  public void tearDown() {
    mockery.assertIsSatisfied();
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given null value. Expected
   * {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNull() throws ClassProviderException {
    targetClassForNameProvider.get(null);
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with null value.
   * Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNullValue() throws ClassProviderException {
    targetClassForNameProvider.get(new TargetClassForNameImpl(null));
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with empty value.
   * Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForEmptyValue() throws ClassProviderException {
    targetClassForNameProvider.get(new TargetClassForNameImpl(""));
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with blank value.
   * Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForBlankValue() throws ClassProviderException {
    targetClassForNameProvider.get(new TargetClassForNameImpl(" "));
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with null symbolic
   * name of class loader. Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNullClassLoader() throws ClassProviderException {
    targetClassForNameProvider.get(new TargetClassForNameImpl(Integer.class.getName(), true, null));
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with empty symbolic
   * name of class loader. Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForEmptyClassLoader() throws ClassProviderException {
    targetClassForNameProvider.get(new TargetClassForNameImpl(Integer.class.getName(), true, ""));
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with blank symbolic
   * name of class loader. Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForBlankClassLoader() throws ClassProviderException {
    targetClassForNameProvider.get(new TargetClassForNameImpl(Integer.class.getName(), true, " "));
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test
  public void testGetForCallerClassLoader() throws ClassProviderException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.CALLER);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#CONTEXT} of class loader.
   */
  @Test
  public void testGetForContextClassLoader() throws ClassProviderException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.CONTEXT);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#SYSTEM} of class loader.
   */
  @Test
  public void testGetForSystemClassLoader() throws ClassProviderException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.SYSTEM);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with symbolic name
   * of class loader which is stored in session.
   */
  @Test
  public void testGetForStoredClassLoader() throws ClassProviderException {
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
    Class<?> clazz = targetClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for given annotation with symbolic name
   * of class loader which is not stored in session.
   */
  @Test
  public void testGetForNotStoredClassLoader() throws ClassProviderException {
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
    Class<?> clazz = targetClassForNameProvider.get(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameProvider#get} for non-existing class in given
   * annotation with symbolic name {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNonExistingClass() throws ClassProviderException {
    targetClassForNameProvider.get(new TargetClassForNameImpl("java.lang.Foo"));
  }

  // ------------------------------ context ------------------------------------

  /** Tested class generator. */
  private TargetClassForNameProvider targetClassForNameProvider;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void prepareInstances() {
    // prepare testing instance
    targetClassForNameProvider = injector.getInstance(TargetClassForNameProvider.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContextUnitSupport createUnitTestContext() {
    // @formatter:off
    return new ContextUnitSupport(
        new TargetClassForNameProviderModule(mockery)
    );
    // @formatter:on
  }

  /**
   * Google Guice module for context of this test.
   *
   * @author Mr.FrAnTA
   */
  private class TargetClassForNameProviderModule extends MockModule {
    /**
     * Constructs module with given mockery.
     *
     * @param mockery the mockery which will be used for mocking.
     */
    public TargetClassForNameProviderModule(Mockery mockery) {
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
