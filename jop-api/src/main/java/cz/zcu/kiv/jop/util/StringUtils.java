package cz.zcu.kiv.jop.util;

/**
 * Helper static class for various {@link String} operations.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public abstract class StringUtils {

  /**
   * Private constructor in combination with abstract modifier of this class
   * makes it static.
   */
  private StringUtils() {}

  /**
   * Checks whatever the given char sequence is <code>null</code> or empty
   * (length is equal to 0).
   *
   * @param cs the char sequence to check (may be <code>null</code>).
   * @return <code>true</code> if char sequence is <code>null</code> or empty;
   *         <code>false</code> otherwise.
   */
  public static boolean isEmpty(CharSequence cs) {
    return (cs == null || cs.length() == 0);
  }

  /**
   * Checks whatever the given string is <code>null</code> or empty (length is
   * equal to 0).
   *
   * @param str the String to check (may be <code>null</code>).
   * @return <code>true</code> if String is <code>null</code> or empty;
   *         <code>false</code> otherwise.
   */
  public static boolean isEmpty(String str) {
    return isEmpty((CharSequence)str);
  }

  /**
   * Check whatever the given char sequence is not <code>null</code> nor is not
   * empty (length is greater than 0). Notice that for char sequence made only
   * by whitespace will be returned <code>true</code>.
   *
   * @param cs the char sequence to check (may be <code>null</code>).
   * @return <code>true</code> if char sequence is not <code>null</code> and has
   *         length; <code>false</code> otherwise.
   */
  public static boolean hasLength(CharSequence cs) {
    return (cs != null && cs.length() > 0);
  }

  /**
   * Check whatever the given String is not <code>null</code> nor is not empty
   * (length is greater than 0). Notice that for String made only by whitespace
   * will be returned <code>true</code>.
   *
   * @param str the String to check (may be <code>null</code>).
   * @return <code>true</code> if String is not <code>null</code> and has
   *         length; <code>false</code> otherwise.
   */
  public static boolean hasLength(String str) {
    return hasLength((CharSequence)str);
  }

  /**
   * Check whether the given char sequence is not <code>null</code> and has some
   * non-whitespace text - has length greater than 0 and contains at least one
   * non-whitespace character.
   *
   * @param cs the char sequence to check (may be <code>null</code>).
   * @return <code>true</code> if char sequence is not <code>null</code> and
   *         contains a at least one non-whitespace character;
   *         <code>false</code> otherwise.
   */
  public static boolean hasText(CharSequence cs) {
    if (!hasLength(cs)) {
      return false;
    }

    int strLen = cs.length();
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return true;
      }
    }

    return false;
  }

  /**
   * Check whether the given String is not <code>null</code> and has some
   * non-whitespace text - has length greater than 0 and contains at least one
   * non-whitespace character.
   *
   * @param str the String to check (may be <code>null</code>).
   * @return <code>true</code> if String is not <code>null</code> and contains a
   *         at least one non-whitespace character; <code>false</code>
   *         otherwise.
   */
  public static boolean hasText(String str) {
    return hasText((CharSequence)str);
  }

}
