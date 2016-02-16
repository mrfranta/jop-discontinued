package cz.zcu.kiv.jop.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for helper class {@link Defaults}.
 *
 * @author Mr.FrAnTA
 */
public class DefaultsTest {

  /**
   * Test of {@link Defaults#getDefaultValue} method which should return default values for
   * primitive types and for object types returns <code>null</code> value (also default value).
   */
  @Test
  public void testGetDefaultValue() {
    Assert.assertEquals(false, Defaults.getDefaultValue(boolean.class));
    Assert.assertEquals(null, Defaults.getDefaultValue(Boolean.class));

    Assert.assertEquals((byte)0, Defaults.getDefaultValue(byte.class));
    Assert.assertEquals(null, Defaults.getDefaultValue(Byte.class));

    Assert.assertEquals((char)0, Defaults.getDefaultValue(char.class));
    Assert.assertEquals(null, Defaults.getDefaultValue(Character.class));

    Assert.assertEquals((short)0, Defaults.getDefaultValue(short.class));
    Assert.assertEquals(null, Defaults.getDefaultValue(Short.class));

    Assert.assertEquals(0, Defaults.getDefaultValue(int.class));
    Assert.assertEquals(null, Defaults.getDefaultValue(Integer.class));

    Assert.assertEquals(0L, Defaults.getDefaultValue(long.class));
    Assert.assertEquals(null, Defaults.getDefaultValue(Long.class));

    Assert.assertEquals(0.f, Defaults.getDefaultValue(float.class));
    Assert.assertEquals(null, Defaults.getDefaultValue(Float.class));

    Assert.assertEquals(0.d, Defaults.getDefaultValue(double.class));
    Assert.assertEquals(null, Defaults.getDefaultValue(Double.class));

    Assert.assertEquals(null, Defaults.getDefaultValue(Object.class));
  }

}
