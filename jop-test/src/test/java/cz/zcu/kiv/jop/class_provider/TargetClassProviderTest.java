package cz.zcu.kiv.jop.class_provider;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.zcu.kiv.jop.annotation.class_provider.TargetClassImpl;

/**
 * Test of class {@link TargetClassProvider}.
 *
 * @author Mr.FrAnTA
 */
public class TargetClassProviderTest {

  /** Tested class generator. */
  private static TargetClassProvider targetClassProvider;

  /**
   * Prepares tested class generator. It may be static because it doesn't have state.
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    targetClassProvider = new TargetClassProvider();
  }

  /**
   * Clears tested class generator.
   */
  @AfterClass
  public static void tearDownAfterClass() {
    targetClassProvider = null;
  }

  /**
   * Test of method {@link TargetClassProvider#get} for given null value. Expected
   * {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNull() throws ClassProviderException {
    targetClassProvider.get(null);
  }

  /**
   * Test of method {@link TargetClassProvider#get} for given annotation with null value.
   * Expected {@link ClassProviderException}.
   */
  @Test(expected = ClassProviderException.class)
  public void testGetForNullValue() throws ClassProviderException {
    targetClassProvider.get(new TargetClassImpl(null));
  }

  /**
   * Test of method {@link TargetClassProvider#get} for given annotation with class value
   * which will be returned.
   */
  @Test
  public void testGet() throws ClassProviderException {
    Assert.assertEquals(Integer.class, targetClassProvider.get(new TargetClassImpl(Integer.class)));
  }

}
