package cz.zcu.kiv.jop.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for helper class {@link Preconditions}.
 *
 * @author Mr.FrAnTA
 */
public class PreconditionsTest {

  /** Constant for valid state condition. */
  protected static final boolean validState = true;
  /** Constant for invalid state condition. */
  protected static final boolean invalidState = false;

  /** Constant for legal argument condition. */
  protected static final boolean legalArgument = true;
  /** Constant for illegal argument condition. */
  protected static final boolean illegalArgument = false;

  /**
   * Test of {@link Preconditions#checkNotNull(Object)} method which should
   * return given reference and in case of <code>null</code> throws the
   * {@link NullPointerException}.
   */
  @Test
  public void testCheckNotNull() {
    String ref = "Test";
    Assert.assertEquals(ref, Preconditions.checkNotNull(ref));
  }

  /**
   * Test of {@link Preconditions#checkNotNull(Object)} method which should
   * return given reference and in case of <code>null</code> throws the
   * {@link NullPointerException}.
   */
  @Test(expected = NullPointerException.class)
  public void testCheckNotNullForNull() {
    Preconditions.checkNotNull(null);
  }

  /**
   * Test of {@link Preconditions#checkNotNull(Object, String)} method which
   * should return given reference and in case of <code>null</code> throws the
   * {@link NullPointerException} with given message.
   */
  @Test
  public void testCheckNotNullWithMessage() {
    String ref = "Test";
    Assert.assertEquals(ref, Preconditions.checkNotNull(ref, "Reference should not be null"));
  }

  /**
   * Test of {@link Preconditions#checkNotNull(Object, String)} method which
   * should return given reference and in case of <code>null</code> throws the
   * {@link NullPointerException} with given message.
   */
  @Test(expected = NullPointerException.class)
  public void testCheckNotNullWithMessageForNull() {
    String message = "Reference should not be null";
    try {
      Preconditions.checkNotNull(null, message);
    }
    catch (NullPointerException exc) {
      // only check message and re-throw exception to be expected
      Assert.assertEquals(message, exc.getMessage());
      throw exc;
    }
  }

  /**
   * Test of {@link Preconditions#checkNotNull(Object, String, Object...)}
   * method which should return given reference and in case of <code>null</code>
   * throws the {@link NullPointerException} with given formatted message.
   */
  @Test
  public void testCheckNotNullWithFormattedMessage() {
    String ref = "Test";
    Assert.assertEquals(ref, Preconditions.checkNotNull(ref, "%s should not be null", "Reference"));
  }

  /**
   * Test of {@link Preconditions#checkNotNull(Object, String, Object...)}
   * method which should return given reference and in case of <code>null</code>
   * throws the {@link NullPointerException} with given formatted message.
   */
  @Test(expected = NullPointerException.class)
  public void testCheckNotNullWithFormattedMessageForNull() {
    final String message = "Reference should not be null";
    try {
      Preconditions.checkNotNull(null, "%s should not be null", "Reference");
    }
    catch (NullPointerException exc) {
      // only check message and re-throw exception to be expected
      Assert.assertEquals(message, exc.getMessage());
      throw exc;
    }
  }

  /**
   * Test of {@link Preconditions#checkArgumentNotNull(Object)} method which
   * should return given reference and in case of <code>null</code> throws the
   * {@link IllegalArgumentException}.
   */
  @Test
  public void testCheckArgumentNotNull() {
    String ref = "Test";
    Assert.assertEquals(ref, Preconditions.checkArgumentNotNull(ref));
  }

  /**
   * Test of {@link Preconditions#checkArgumentNotNull(Object)} method which
   * should return given reference and in case of <code>null</code> throws the
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckArgumentNotNullForNull() {
    Preconditions.checkArgumentNotNull(null);
  }

  /**
   * Test of {@link Preconditions#checkArgumentNotNull(Object, String)} method
   * which should return given reference and in case of <code>null</code> throws
   * the {@link IllegalArgumentException} with given message.
   */
  @Test
  public void testCheckArgumentNotNullWithMessage() {
    String ref = "Test";
    Assert.assertEquals(ref, Preconditions.checkArgumentNotNull(ref, "Reference should not be null"));
  }

  /**
   * Test of {@link Preconditions#checkArgumentNotNull(Object, String)} method
   * which should return given reference and in case of <code>null</code> throws
   * the {@link IllegalArgumentException} with given message.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckArgumentNotNullWithMessageForNull() {
    String message = "Reference should not be null";
    try {
      Preconditions.checkArgumentNotNull(null, message);
    }
    catch (IllegalArgumentException exc) {
      // only check message and re-throw exception to be expected
      Assert.assertEquals(message, exc.getMessage());
      throw exc;
    }
  }

  /**
   * Test of
   * {@link Preconditions#checkArgumentNotNull(Object, String, Object...)}
   * method which should return given reference and in case of <code>null</code>
   * throws the {@link IllegalArgumentException} with given formatted message.
   */
  @Test
  public void testCheckArgumentNotNullWithFormattedMessage() {
    String ref = "Test";
    Assert.assertEquals(ref, Preconditions.checkArgumentNotNull(ref, "%s should not be null", "Reference"));
  }

  /**
   * Test of
   * {@link Preconditions#checkArgumentNotNull(Object, String, Object...)}
   * method which should return given reference and in case of <code>null</code>
   * throws the {@link IllegalArgumentException} with given formatted message.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckArgumentNotNullWithFormattedMessageForNull() {
    final String message = "Reference should not be null";
    try {
      Preconditions.checkArgumentNotNull(null, "%s should not be null", "Reference");
    }
    catch (IllegalArgumentException exc) {
      // only check message and re-throw exception to be expected
      Assert.assertEquals(message, exc.getMessage());
      throw exc;
    }
  }

  /**
   * Test of {@link Preconditions#checkState(boolean)} method which throws the
   * {@link IllegalStateException} in case that given condition is not met.
   */
  @Test
  public void testCheckStateForValidState() {
    Preconditions.checkState(validState);
  }

  /**
   * Test of {@link Preconditions#checkState(boolean)} method which throws the
   * {@link IllegalStateException} in case that given condition is not met.
   */
  @Test(expected = IllegalStateException.class)
  public void testCheckStateForInvalidState() {
    Preconditions.checkState(invalidState);
  }

  /**
   * Test of {@link Preconditions#checkState(boolean, String)} method which
   * throws the {@link IllegalStateException} with given message in case that
   * given condition is not met.
   */
  @Test
  public void testCheckStateWithMessageForInvalidState() {
    Preconditions.checkState(validState, "State is not valid");
  }

  /**
   * Test of {@link Preconditions#checkState(boolean, String)} method which
   * throws the {@link IllegalStateException} with given message in case that
   * given condition is not met.
   */
  @Test(expected = IllegalStateException.class)
  public void testCheckStateWithMessageForValidState() {
    String message = "State is not valid";
    try {
      Preconditions.checkState(invalidState, message);
    }
    catch (IllegalStateException exc) {
      // only check message and re-throw exception to be expected
      Assert.assertEquals(message, exc.getMessage());
      throw exc;
    }
  }

  /**
   * Test of {@link Preconditions#checkState(boolean, String, Object...)} method
   * which throws the {@link IllegalStateException} in case that given condition
   * is not met.
   */
  @Test
  public void testCheckStateWithFormattedMessageForValidState() {
    Preconditions.checkState(validState, "%s is not valid", "State");
  }

  /**
   * Test of {@link Preconditions#checkState(boolean, String, Object...)} method
   * which throws the {@link IllegalStateException} in case that given condition
   * is not met.
   */
  @Test(expected = IllegalStateException.class)
  public void testCheckStateWithFormattedMessageForInvalidState() {
    String message = "State is not valid";
    try {
      Preconditions.checkState(invalidState, "%s is not valid", "State");
    }
    catch (IllegalStateException exc) {
      // only check message and re-throw exception to be expected
      Assert.assertEquals(message, exc.getMessage());
      throw exc;
    }
  }

  /**
   * Test of {@link Preconditions#checkArgument(boolean)} method which throws
   * the {@link IllegalArgumentException} in case that given condition is not
   * met.
   */
  @Test
  public void testCheckArgumentForValidArgument() {
    Preconditions.checkArgument(legalArgument);
  }

  /**
   * Test of {@link Preconditions#checkArgument(boolean)} method which throws
   * the {@link IllegalArgumentException} in case that given condition is not
   * met.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckArgumentForInlegalArgument() {
    Preconditions.checkArgument(illegalArgument);
  }

  /**
   * Test of {@link Preconditions#checkArgument(boolean, String)} method which
   * throws the {@link IllegalArgumentException} with given message in case that
   * given condition is not met.
   */
  @Test
  public void testCheckArgumentWithMessageForInlegalArgument() {
    Preconditions.checkArgument(legalArgument, "Argument is illegal");
  }

  /**
   * Test of {@link Preconditions#checkArgument(boolean, String)} method which
   * throws the {@link IllegalArgumentException} with given message in case that
   * given condition is not met.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckArgumentWithMessageForValidArgument() {
    String message = "Argument is illegal";
    try {
      Preconditions.checkArgument(illegalArgument, message);
    }
    catch (IllegalArgumentException exc) {
      // only check message and re-throw exception to be expected
      Assert.assertEquals(message, exc.getMessage());
      throw exc;
    }
  }

  /**
   * Test of {@link Preconditions#checkArgument(boolean, String, Object...)}
   * method which throws the {@link IllegalArgumentException} in case that given
   * condition is not met.
   */
  @Test
  public void testCheckArgumentWithFormattedMessageForValidArgument() {
    Preconditions.checkArgument(legalArgument, "%s is illegal", "Argument");
  }

  /**
   * Test of {@link Preconditions#checkArgument(boolean, String, Object...)}
   * method which throws the {@link IllegalArgumentException} in case that given
   * condition is not met.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckArgumentWithFormattedMessageForInlegalArgument() {
    String message = "Argument is illegal";
    try {
      Preconditions.checkArgument(illegalArgument, "%s is illegal", "Argument");
    }
    catch (IllegalArgumentException exc) {
      // only check message and re-throw exception to be expected
      Assert.assertEquals(message, exc.getMessage());
      throw exc;
    }
  }

}
