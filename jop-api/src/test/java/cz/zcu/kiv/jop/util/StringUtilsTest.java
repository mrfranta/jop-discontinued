package cz.zcu.kiv.jop.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests of helper static class {@link StringUtils}.
 *
 * @author Mr.FrAnTA
 */
public class StringUtilsTest {

  /**
   * Tests of method {@link StringUtils#isEmpty(String)}. This method should
   * return <code>true</code> in case of empty (<code>null</code> or without
   * length) string; <code>false</code> otherwise.
   */
  @Test
  public void testIsEmpty() {
    Assert.assertTrue(StringUtils.isEmpty((String)null));
    Assert.assertTrue(StringUtils.isEmpty(""));
    Assert.assertFalse(StringUtils.isEmpty(" "));
    Assert.assertFalse(StringUtils.isEmpty("text"));
  }

  /**
   * Tests of method {@link StringUtils#hasLength(String)}. This method should
   * return <code>true</code> in case of non-empty (not <code>null</code> and
   * with some length) string; <code>false</code> otherwise.
   */
  @Test
  public void testHasLength() {
    Assert.assertFalse(StringUtils.hasLength((String)null));
    Assert.assertFalse(StringUtils.hasLength(""));
    Assert.assertTrue(StringUtils.hasLength(" "));
    Assert.assertTrue(StringUtils.hasLength("text"));
  }

  /**
   * Tests of method {@link StringUtils#hasText(String)}. This method should
   * return <code>true</code> in case that given string contains at least one
   * non-whitespace character. It should returns <code>false</code> otherwise.
   */
  @Test
  public void testHasText() {
    Assert.assertFalse(StringUtils.hasText((String)null));
    Assert.assertFalse(StringUtils.hasText(""));
    Assert.assertFalse(StringUtils.hasText(" "));
    Assert.assertTrue(StringUtils.hasText("text"));
  }

}
