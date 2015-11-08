package cz.zcu.kiv.jop.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for helper class {@link ReflectionUtils}.
 * 
 * @author Mr.FrAnTA
 */
public class ReflectionUtilsTest {

  /**
   * Tests of method
   * {@link ReflectionUtils#isAccessible(java.lang.reflect.Member)} which should
   * returns <code>true</code> in case that given member (field) is accessible.
   */
  @Test
  public void testIsAccessibleField() throws Exception {
    Assert.assertFalse(ReflectionUtils.isAccessible(null));
    Assert.assertTrue(ReflectionUtils.isAccessible(getDeclaredField("Public")));
    Assert.assertFalse(ReflectionUtils.isAccessible(getDeclaredField("Protected")));
    Assert.assertFalse(ReflectionUtils.isAccessible(getDeclaredField("Package")));
    Assert.assertFalse(ReflectionUtils.isAccessible(getDeclaredField("Private")));
  }

  /**
   * Tests of method
   * {@link ReflectionUtils#isAccessible(java.lang.reflect.Member)} which should
   * returns <code>true</code> in case that given member (method) is accessible.
   */
  @Test
  public void testIsAccessibleMethod() throws Exception {
    Assert.assertFalse(ReflectionUtils.isAccessible(null));
    Assert.assertTrue(ReflectionUtils.isAccessible(getDeclaredGetter("Public")));
    Assert.assertFalse(ReflectionUtils.isAccessible(getDeclaredGetter("Protected")));
    Assert.assertFalse(ReflectionUtils.isAccessible(getDeclaredGetter("Package")));
    Assert.assertFalse(ReflectionUtils.isAccessible(getDeclaredGetter("Private")));
  }

  /**
   * Tests of method {@link ReflectionUtils#getDeclaredField(Class, String)}
   * which should returns declared field in given class with given name. In case
   * that field is not accessible, the method should make it accessible.
   */
  @Test
  public void testGetDeclaredField() {
    // Assert.assertNull(ReflectionUtils.getDeclaredField(Mock.class, null));
    Assert.assertNotNull(ReflectionUtils.getDeclaredField(Mock.class, "Public"));
    Assert.assertNotNull(ReflectionUtils.getDeclaredField(Mock.class, "Protected"));
    Assert.assertNotNull(ReflectionUtils.getDeclaredField(Mock.class, "Package"));
    Assert.assertNotNull(ReflectionUtils.getDeclaredField(Mock.class, "Private"));
    Assert.assertNull(ReflectionUtils.getDeclaredField(Mock.class, "Undeclared"));
  }

  /**
   * Tests of method
   * {@link ReflectionUtils#getDeclaredField(Class, String, boolean)} which
   * should returns declared field in given class with given name. In case that
   * field is not accessible, the method should return <code>null</code>.
   */
  @Test
  public void testGetDeclaredAccessibleField() {
    // Assert.assertNull(ReflectionUtils.getDeclaredField(Mock.class, null));
    Assert.assertNotNull(ReflectionUtils.getDeclaredField(Mock.class, "Public", true));
    Assert.assertNull(ReflectionUtils.getDeclaredField(Mock.class, "Protected", true));
    Assert.assertNull(ReflectionUtils.getDeclaredField(Mock.class, "Package", true));
    Assert.assertNull(ReflectionUtils.getDeclaredField(Mock.class, "Private", true));
    Assert.assertNull(ReflectionUtils.getDeclaredField(Mock.class, "Undeclared", true));
  }

  /**
   * Tests of method
   * {@link ReflectionUtils#getDeclaredMethod(Class, String, Class...)} which
   * should returns declared method in given class with given name and class
   * types of parameters. In case that method is not accessible, the method
   * <code>{@link ReflectionUtils#getDeclaredMethod getDeclaredMethod}</code>
   * should make it accessible.
   */
  @Test
  public void testGetDeclaredMethod() {
    // Assert.assertNull(ReflectionUtils.getDeclaredField(Mock.class, null));
    Assert.assertNotNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getPublic"));
    Assert.assertNotNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getProtected"));
    Assert.assertNotNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getPackage"));
    Assert.assertNotNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getPrivate"));

    Assert.assertNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getPublic", Void.class));
    Assert.assertNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getProtected", Void.class));
    Assert.assertNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getPackage", Void.class));
    Assert.assertNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getPrivate", Void.class));

    Assert.assertNull(ReflectionUtils.getDeclaredMethod(Mock.class, "getUndeclared"));
  }

  /**
   * Helper method which returns field with entered name (from {@link Mock}
   * class).
   */
  protected Field getDeclaredField(String name) throws Exception {
    return Mock.class.getDeclaredField(name);
  }

  /**
   * Helper method which returns getter for property with entered name (from
   * {@link Mock} class).
   */
  protected Method getDeclaredGetter(String name) throws Exception {
    return Mock.class.getDeclaredMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
  }

  /**
   * Mock object which contains fields and method with various visibility
   * modifiers.
   * 
   * @author Mr.FrAnTA
   */
  @SuppressWarnings("unused")
  protected static class Mock {

    /** Field with public visibility. */
    public int Public;
    /** Field with protected visibility. */
    protected int Protected;
    /** Field with package visibility. */
    int Package;
    /** Field with private visibility. */
    private int Private;

    /** Method with public visibility. */
    public int getPublic() {
      return Public;
    }

    /** Method with protected visibility. */
    protected int getProtected() {
      return Protected;
    }

    /** Method with package visibility. */
    int getPackage() {
      return Package;
    }

    /** Method with private visibility. */
    private int getPrivate() {
      return Private;
    }

  }

}
