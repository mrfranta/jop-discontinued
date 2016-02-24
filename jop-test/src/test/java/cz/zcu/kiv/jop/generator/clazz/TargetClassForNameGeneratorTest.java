package cz.zcu.kiv.jop.generator.clazz;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.AbstractContextTest;
import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForName;
import cz.zcu.kiv.jop.annotation.class_provider.TargetClassForNameImpl;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;
import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.guice.MockModule;
import cz.zcu.kiv.jop.session.ClassLoaderSession;

/**
 * Test of class {@link TargetClassForNameGenerator}.
 *
 * @author Mr.FrAnTA
 */
public class TargetClassForNameGeneratorTest extends AbstractContextTest {

  /**
   * Test of method {@link TargetClassForNameGenerator#getValueType()} which has to return class of
   * class type.
   */
  @Test
  public void testGetValueType() {
    Assert.assertEquals(Class.class, targetClassForNameGenerator.getValueType());
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given null value. Expected
   * {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetForNull() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(null);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with null value.
   * Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetForNullValue() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(null));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with empty value.
   * Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetForEmptyValue() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(""));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with blank value.
   * Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetForBlankValue() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(" "));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with null symbolic
   * name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetForNullClassLoader() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(Integer.class.getName(), true, null));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with empty symbolic
   * name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetForEmptyClassLoader() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(Integer.class.getName(), true, ""));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with blank symbolic
   * name of class loader. Expected {@link ValueGeneratorException}.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetForBlankClassLoader() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl(Integer.class.getName(), true, " "));
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test
  public void testGetForCallerClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.CALLER);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameGenerator.getValue(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#CONTEXT} of class loader.
   */
  @Test
  public void testGetForContextClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.CONTEXT);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameGenerator.getValue(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with symbolic name
   * {@link ClassLoaderConst#SYSTEM} of class loader.
   */
  @Test
  public void testGetForSystemClassLoader() throws ValueGeneratorException {
    /*----- Preparation -----*/
    TargetClassForName targetClassForName = new TargetClassForNameImpl(Integer.class.getName(), true, ClassLoaderConst.SYSTEM);

    /*----- Execution -----*/
    Class<?> clazz = targetClassForNameGenerator.getValue(targetClassForName);

    /*----- Verify -----*/
    Assert.assertEquals(Integer.class, clazz);
  }

  /**
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with symbolic name
   * of class loader which is stored in session.
   */
  @Test
  public void testGetForStoredClassLoader() throws ValueGeneratorException {
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
   * Test of method {@link TargetClassForNameGenerator#get} for given annotation with symbolic name
   * of class loader which is not stored in session.
   */
  @Test
  public void testGetForNotStoredClassLoader() throws ValueGeneratorException {
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
   * Test of method {@link TargetClassForNameGenerator#get} for non-existing class in given
   * annotation with symbolic name {@link ClassLoaderConst#CALLER} of class loader.
   */
  @Test(expected = ValueGeneratorException.class)
  public void testGetForNonExistingClass() throws ValueGeneratorException {
    targetClassForNameGenerator.getValue(new TargetClassForNameImpl("java.lang.Foo"));
  }

  // ------------------------------ context ------------------------------------

  /** Tested class generator. */
  private TargetClassForNameGenerator targetClassForNameGenerator;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void prepareInstances() {
    // prepare testing instance
    targetClassForNameGenerator = injector.getInstance(TargetClassForNameGenerator.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContextUnitSupport createUnitTestContext() {
    // @formatter:off
    return new ContextUnitSupport(
        new TargetClassForNameGeneratorModule(mockery)
    );
    // @formatter:on
  }

  /**
   * Google Guice module for context of this test.
   *
   * @author Mr.FrAnTA
   */
  private class TargetClassForNameGeneratorModule extends MockModule {
    /**
     * Constructs module with given mockery.
     *
     * @param mockery the mockery which will be used for mocking.
     */
    public TargetClassForNameGeneratorModule(Mockery mockery) {
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
