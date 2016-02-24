package cz.zcu.kiv.jop;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;

import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.Injector;

/**
 * Prepared skeleton of jUnit test with support of context.
 *
 * @author Mr.FrAnTA
 */
public abstract class AbstractContextTest {

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
    contextUnitSupport.dispose();
    mockery.assertIsSatisfied();
  }

  //------------------------------ context ------------------------------------

  /** Support of jUnit context. */
  private ContextUnitSupport contextUnitSupport;
  /** Mockery for mocking dependencies. */
  protected final Mockery mockery = new Mockery();
  /** Injector of dependencies. */
  protected Injector injector;

  /**
   * Prepare unit test instances
   */
  protected void prepareContext() {
    contextUnitSupport = createUnitTestContext();
    injector = contextUnitSupport.createInjector();
    prepareInstances(); // prepare instances for tests
  }

  /**
   * Prepares instances for tests.
   */
  protected abstract void prepareInstances();

  /**
   * Returns mocked instance of given class.
   *
   * @param mockedClass the mocked class type.
   * @return Instance of mocked class.
   */
  protected <T> T getInstance(Class<T> mockedClass) {
    return injector.getInstance(mockedClass);
  }

  /**
   * Create jUnit context.
   *
   * @return the provided jUnit context.
   */
  protected abstract ContextUnitSupport createUnitTestContext();

}
