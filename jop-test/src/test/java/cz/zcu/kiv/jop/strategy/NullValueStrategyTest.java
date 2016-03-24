package cz.zcu.kiv.jop.strategy;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.AbstractContextTest;
import cz.zcu.kiv.jop.context.PopulatingContext;
import cz.zcu.kiv.jop.ioc.ContextUnitSupport;
import cz.zcu.kiv.jop.ioc.guice.MockModule;
import cz.zcu.kiv.jop.property.DirectAccessProperty;
import cz.zcu.kiv.jop.util.Defaults;

/**
 * Test of class {@link NullValueStrategy}.
 *
 * @author Mr.FrAnTA
 */
public class NullValueStrategyTest extends AbstractContextTest {

  /** Primitive field for testing. */
  protected boolean primitive = true;
  /** Non-primitive field for testing. */
  protected Integer nonPrimitive = new Integer(42);
  /** Final field which cannot be changed. */
  protected final double finalField = 42d;

  /**
   * Test of method {@link NullValueStrategy#applyStrategy} for primitive property.
   */
  @Test
  public void testApplyStrategyForPrimitiveType() throws Exception {
    /*----- Preparation -----*/

    DirectAccessProperty<?> property = new DirectAccessProperty<Object>(getClass(), "primitive");
    Assert.assertTrue(primitive);
    Assert.assertEquals(true, property.getGetter().get(this));

    /*----- Expectations -----*/

    mockery.checking(new Expectations() {
      {
        exactly(1).of(getInstance(PopulatingContext.class)).getCurrentObject();
        will(returnValue(NullValueStrategyTest.this));
      }
    });

    /*----- Execution -----*/
    nullValueStrategy.applyStrategy(property, getInstance(PopulatingContext.class));

    /*----- Verify -----*/
    Assert.assertEquals(Defaults.getDefaultValue(boolean.class), primitive);
    Assert.assertEquals(Defaults.getDefaultValue(boolean.class), property.getGetter().get(this));
  }

  /**
   * Test of method {@link NullValueStrategy#applyStrategy} for non-primitive property.
   */
  @Test
  public void testApplyStrategyForNonPrimitiveType() throws Exception {
    /*----- Preparation -----*/

    DirectAccessProperty<?> property = new DirectAccessProperty<Object>(getClass(), "nonPrimitive");
    Assert.assertEquals(new Integer(42), nonPrimitive);
    Assert.assertEquals(42, property.getGetter().get(this));

    /*----- Expectations -----*/

    mockery.checking(new Expectations() {
      {
        exactly(1).of(getInstance(PopulatingContext.class)).getCurrentObject();
        will(returnValue(NullValueStrategyTest.this));
      }
    });

    /*----- Execution -----*/
    nullValueStrategy.applyStrategy(property, getInstance(PopulatingContext.class));

    /*----- Verify -----*/
    Assert.assertEquals(null, nonPrimitive);
    Assert.assertEquals(null, property.getGetter().get(this));
  }

  /**
   * Test of method {@link NullValueStrategy#applyStrategy} for final property. Expected
   * {@link PopulatingStrategyException} because the final field cannot be changed.
   */
  @Test(expected = PopulatingStrategyException.class)
  public void testApplyStrategyWithError() throws Exception {
    /*----- Preparation -----*/

    DirectAccessProperty<?> property = new DirectAccessProperty<Object>(getClass(), "finalField");

    /*----- Execution & Verify -----*/
    nullValueStrategy.applyStrategy(property, getInstance(PopulatingContext.class));
  }

  // ------------------------------ context ------------------------------------

  /** Tested strategy. */
  private NullValueStrategy nullValueStrategy;

  @Override
  protected void prepareInstances() {
    // prepare testing instance
    nullValueStrategy = injector.getInstance(NullValueStrategy.class);
  }

  @Override
  protected ContextUnitSupport createUnitTestContext() {
    // @formatter:off
    return new ContextUnitSupport(
        new NullValueStrategyModule(mockery)
    );
    // @formatter:on
  }

  /**
   * Google Guice module for context of this test.
   *
   * @author Mr.FrAnTA
   */
  private class NullValueStrategyModule extends MockModule {

    /**
     * Constructs module with given mockery.
     *
     * @param mockery the mockery which will be used for mocking.
     */
    public NullValueStrategyModule(Mockery mockery) {
      super(mockery);
    }

    /**
     * Configures mocked bindings.
     */
    @Override
    protected void configure() {
      bindMock(PopulatingContext.class);
    }
  }

}
