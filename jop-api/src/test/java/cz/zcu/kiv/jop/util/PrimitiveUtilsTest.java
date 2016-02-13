package cz.zcu.kiv.jop.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests of helper static class {@link PrimitiveUtils}.
 *
 * @author Mr.FrAnTA
 */
public class PrimitiveUtilsTest {

  /** Mapping of primitive types to object types. */
  private static final Map<Class<?>, Class<?>> primitivesMap = new HashMap<Class<?>, Class<?>>();
  /** Mapping of primitive types to their abbreviations. */
  private static final Map<Class<?>, String> abbreviationMap = new HashMap<Class<?>, String>();

  /**
   * Prepares map of primitives before test.
   */
  @BeforeClass
  public static void setUpBeforeClass() {
    primitivesMap.put(void.class, Void.class);
    primitivesMap.put(boolean.class, Boolean.class);
    primitivesMap.put(byte.class, Byte.class);
    primitivesMap.put(char.class, Character.class);
    primitivesMap.put(short.class, Short.class);
    primitivesMap.put(int.class, Integer.class);
    primitivesMap.put(long.class, Long.class);
    primitivesMap.put(float.class, Float.class);
    primitivesMap.put(double.class, Double.class);

    abbreviationMap.put(void.class, "V");
    abbreviationMap.put(boolean.class, "Z");
    abbreviationMap.put(byte.class, "B");
    abbreviationMap.put(char.class, "C");
    abbreviationMap.put(short.class, "S");
    abbreviationMap.put(int.class, "I");
    abbreviationMap.put(long.class, "J");
    abbreviationMap.put(float.class, "F");
    abbreviationMap.put(double.class, "D");
  }

  /**
   * Clears map of primitives after test.
   */
  @AfterClass
  public static void tearDownAfterClass() {
    primitivesMap.clear();
    abbreviationMap.clear();
  }

  /**
   * Test of method {@link PrimitiveUtils#wrap(Class)} for <code>null</code> value.
   */
  @Test
  public void testWrapForNull() {
    Assert.assertNull(PrimitiveUtils.wrap(null));
  }

  /**
   * Test of method {@link PrimitiveUtils#wrap(Class)} for object class types.
   */
  @Test
  public void testWrapForObjectTypes() {
    for (Class<?> objectClass : primitivesMap.values()) {
      Assert.assertEquals(objectClass, PrimitiveUtils.wrap(objectClass));
    }
  }

  /**
   * Test of method {@link PrimitiveUtils#wrap(Class)} for primitive class types.
   */
  @Test
  public void testWrapForPrimitiveTypes() {
    for (Map.Entry<Class<?>, Class<?>> entry : primitivesMap.entrySet()) {
      Assert.assertEquals(entry.getValue(), PrimitiveUtils.wrap(entry.getKey()));
    }
  }

  /**
   * Test of method {@link PrimitiveUtils#unwrap(Class)} for <code>null</code> value.
   */
  @Test
  public void testUnwrapForNull() {
    Assert.assertNull(PrimitiveUtils.unwrap(null));
  }

  /**
   * Test of method {@link PrimitiveUtils#unwrap(Class)} for primitive class types.
   */
  @Test
  public void testUnwrapForPrimitiveTypes() {
    for (Class<?> primitiveClass : primitivesMap.keySet()) {
      Assert.assertEquals(primitiveClass, PrimitiveUtils.unwrap(primitiveClass));
    }
  }

  /**
   * Test of method {@link PrimitiveUtils#unwrap(Class)} for object class types.
   */
  @Test
  public void testUnwrapForObjectTypes() {
    for (Map.Entry<Class<?>, Class<?>> entry : primitivesMap.entrySet()) {
      Assert.assertEquals(entry.getKey(), PrimitiveUtils.unwrap(entry.getValue()));
    }
  }

  /**
   * Test of method {@link PrimitiveUtils#abbreviate(Class)} for given null.
   */
  @Test
  public void testAbbreviateForNull() {
    Assert.assertNull(PrimitiveUtils.abbreviate(null));
  }

  /**
   * Test of method {@link PrimitiveUtils#abbreviate(Class)} for given object type
   */
  @Test
  public void testAbbreviateForObjectType() {
    Assert.assertEquals(Object.class.getName(), PrimitiveUtils.abbreviate(Object.class));
  }

  /**
   * Test of method {@link PrimitiveUtils#abbreviate(Class)} for primitive class types.
   */
  @Test
  public void testAbbreviateForPrimitiveTypes() {
    for (Map.Entry<Class<?>, String> entry : abbreviationMap.entrySet()) {
      Assert.assertEquals(entry.getValue(), PrimitiveUtils.abbreviate(entry.getKey()));
    }
  }
}
