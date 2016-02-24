package cz.zcu.kiv.jop.util;

/**
 * Helper static class which provides static methods for unified single-line checking of some
 * preconditions of method arguments, state of object, etc.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public abstract class Preconditions {

  /**
   * Private constructor in combination with abstract modifier of this class makes it static.
   */
  private Preconditions() {}

  /**
   * Checks whatever given object (<code>reference</code>) is not <code>null</code>. In case that
   * given object is <code>null</code> the {@link NullPointerException} is thrown. This method also
   * returns given reference so checking of <code>null</code> value can be used in this way:
   *
   * <pre>
   * public static String getFullName(Person person) {
   *   String firstName = checkNotNull(person.getFirstName());
   *   String lastName = checkNotNull(person.getLastName());
   *
   *   return firstName + &quot; &quot; + lastName;
   * }
   * </pre>
   *
   * @param reference the object (reference) which will be checked.
   * @return Given object (<code>reference</code>).
   * @throws NullPointerException if given object is <code>null</code>.
   */
  public static <T> T checkNotNull(T reference) throws NullPointerException {
    if (reference == null) {
      throw new NullPointerException();
    }

    return reference;
  }

  /**
   * Checks whatever given object (<code>reference</code>) is not <code>null</code>. In case that
   * given object is <code>null</code> the {@link NullPointerException} with given error message is
   * thrown. This method also returns given reference so checking of <code>null</code> value can be
   * used in this way:
   *
   * <pre>
   * public static String getFullName(Person person) {
   *   String firstName = checkNotNull(person.getFirstName(), &quot;First name of person is null&quot;);
   *   String lastName = checkNotNull(person.getLastName(), &quot;Last name of person is null&quot;);
   *
   *   return firstName + &quot; &quot; + lastName;
   * }
   * </pre>
   *
   * @param reference the object (reference) which will be checked.
   * @param errorMessage the error message for thrown exception in case that given object is
   *          <code>null</code>.
   * @return Given object (<code>reference</code>).
   * @throws NullPointerException if given object is <code>null</code>.
   */
  public static <T> T checkNotNull(T reference, String errorMessage) throws NullPointerException {
    if (reference == null) {
      throw new NullPointerException(errorMessage);
    }

    return reference;
  }

  /**
   * Checks whatever given object (<code>reference</code>) is not <code>null</code>. In case that
   * given object is <code>null</code> the {@link NullPointerException} with given formatted error
   * message is thrown. This method also returns given reference so checking of <code>null</code>
   * value can be used in this way:
   *
   * <pre>
   * private static final String FULL_NAME_ERR_MSG = &quot;%s name of person is null&quot;;
   *
   * public static String getFullName(Person person) {
   *   String firstName = checkNotNull(person.getFirstName(), FULL_NAME_ERR_MSG, &quot;First&quot;);
   *   String lastName = checkNotNull(person.getLastName(), FULL_NAME_ERR_MSG, &quot;Last&quot;);
   *
   *   return firstName + &quot; &quot; + lastName;
   * }
   * </pre>
   *
   * @param reference the object (reference) which will be checked.
   * @param errorMessageFormat the formatted error message for thrown exception in case that given
   *          object is <code>null</code>.
   * @param errorMessageArgs the arguments for given formatted error message.
   * @return Given object (<code>reference</code>).
   * @throws NullPointerException if given object is <code>null</code>.
   */
  public static <T> T checkNotNull(T reference, String errorMessageFormat, Object... errorMessageArgs) throws NullPointerException {
    if (reference == null) {
      throw new NullPointerException(String.format(errorMessageFormat, errorMessageArgs));
    }

    return reference;
  }

  /**
   * Checks whatever given object (<code>reference</code>), given as argument of some method, is not
   * <code>null</code>. In case that given object is <code>null</code> the
   * {@link IllegalArgumentException} is thrown. The main difference between this method and method
   * {@link #checkNotNull(Object)} is that the given argument with invalid value (in this case with
   * <code>null</code>) should cause {@link IllegalArgumentException} not the
   * {@link NullPointerException}. This method also returns given reference so checking of
   * <code>null</code> value can be used in this way:
   *
   * <pre>
   * public static &lt;T&gt; Collection merge(Collection&lt;? extends T&gt; col1, Collection&lt;? extends T&gt; col2) {
   *   Collection&lt;T&gt; collection = new ArrayList&lt;T&gt;(Preconditions.checkArgumentNotNull(col1));
   *   collection.addAll(Preconditions.checkArgumentNotNull(col2));
   *
   *   return collection;
   * }
   * </pre>
   *
   * @param reference the object (reference) which will be checked.
   * @return Given object (<code>reference</code>).
   * @throws IllegalArgumentException if given object is <code>null</code>.
   */
  public static <T> T checkArgumentNotNull(T reference) throws IllegalArgumentException {
    if (reference == null) {
      throw new IllegalArgumentException();
    }

    return reference;
  }

  /**
   * Checks whatever given object (<code>reference</code>), given as argument of some method, is not
   * <code>null</code>. In case that given object is <code>null</code> the
   * {@link IllegalArgumentException} with given error message is thrown. The main difference
   * between this method and method {@link #checkNotNull(Object, String)} is that the given argument
   * with invalid value (in this case with <code>null</code>) should cause
   * {@link IllegalArgumentException} not the {@link NullPointerException}. This method also returns
   * given reference so checking of <code>null</code> value can be used in this way:
   *
   * <pre>
   * public static &lt;T&gt; Collection merge(Collection&lt;? extends T&gt; col1, Collection&lt;? extends T&gt; col2) {
   *   Collection&lt;T&gt; collection = new ArrayList&lt;T&gt;(Preconditions.checkArgumentNotNull(col1, &quot;col1 is null&quot;));
   *   collection.addAll(Preconditions.checkArgumentNotNull(col2, &quot;col2 is null&quot;));
   *
   *   return collection;
   * }
   * </pre>
   *
   * @param reference the object (reference) which will be checked.
   * @return Given object (<code>reference</code>).
   * @throws IllegalArgumentException if given object is <code>null</code>.
   */
  public static <T> T checkArgumentNotNull(T reference, String errorMessage) throws IllegalArgumentException {
    if (reference == null) {
      throw new IllegalArgumentException(errorMessage);
    }

    return reference;
  }

  /**
   * Checks whatever given object (<code>reference</code>), given as argument of some method, is not
   * <code>null</code>. In case that given object is <code>null</code> the
   * {@link IllegalArgumentException} with given formatted error message is thrown. The main
   * difference between this method and method {@link #checkNotNull(Object, String, Object...)} is
   * that the given argument with invalid value (in this case with <code>null</code>) should cause
   * {@link IllegalArgumentException} not the {@link NullPointerException}. This method also returns
   * given reference so checking of <code>null</code> value can be used in this way:
   *
   * <pre>
   * private static final String MERGE_ERR_MSG = &quot;%s is null&quot;;
   *
   * public static &lt;T&gt; Collection merge(Collection&lt;? extends T&gt; col1, Collection&lt;? extends T&gt; col2) {
   *   Collection&lt;T&gt; collection = new ArrayList&lt;T&gt;(Preconditions.checkArgumentNotNull(col1, MERGE_ERR_MSG, &quot;col1&quot;));
   *   collection.addAll(Preconditions.checkArgumentNotNull(col2, MERGE_ERR_MSG, &quot;col2&quot;));
   *
   *   return collection;
   * }
   * </pre>
   *
   * @param reference the object (reference) which will be checked.
   * @param errorMessageFormat the formatted error message for thrown exception in case that given
   *          object is <code>null</code>.
   * @param errorMessageArgs the arguments for given formatted error message.
   * @return Given object (<code>reference</code>).
   * @throws IllegalArgumentException if given object is <code>null</code>.
   */
  public static <T> T checkArgumentNotNull(T reference, String errorMessageFormat, Object... errorMessageArgs) throws IllegalArgumentException {
    if (reference == null) {
      throw new IllegalArgumentException(String.format(errorMessageFormat, errorMessageArgs));
    }

    return reference;
  }

  /**
   * Checks whatever the given state <code>condition</code> is met and if not, the
   * {@link IllegalStateException} is thrown. This method is useful for single-line checking of some
   * state. This method can be used in this way:
   *
   * <pre>
   * public void start() {
   *   Preconditions.checkState(isReady);
   *   Preconditions.checkState(!isStarted);
   *
   *   _doSomething();
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @throws IllegalStateException If given condition is not met.
   */
  public static void checkState(boolean condition) throws IllegalStateException {
    if (!condition) {
      throw new IllegalStateException();
    }
  }

  /**
   * Checks whatever the given state <code>condition</code> is met and if not, the
   * {@link IllegalStateException} with given error message is thrown. This method is useful for
   * single-line checking of some state. This method can be used in this way:
   *
   * <pre>
   * public void start() {
   *   Preconditions.checkState(isReady, &quot;I'm not ready&quot;);
   *   Preconditions.checkState(!isStarted, &quot;I'm already working&quot;);
   *
   *   _doSomething();
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @param errorMessage the error message for thrown exception in case that given
   *          <code>condition</code> is not met.
   * @throws IllegalStateException If given condition is not met.
   */
  public static void checkState(boolean condition, String errorMessage) throws IllegalStateException {
    if (!condition) {
      throw new IllegalStateException(errorMessage);
    }
  }

  /**
   * Checks whatever the given state <code>condition</code> is met and if not, the
   * {@link IllegalStateException} with given formatted error message is thrown. This method is
   * useful for single-line checking of some state. This method can be used in this way:
   *
   * <pre>
   * public void start() {
   *   Preconditions.checkState(isReady, &quot;I'm not ready... My state is %s&quot;, getState());
   *   Preconditions.checkState(!isStarted, &quot;I'm already working&quot;);
   *
   *   _doSomething();
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @param errorMessageFormat the formatted error message for thrown exception in case that given
   *          <code>condition</code> is not met.
   * @param errorMessageArgs the arguments for given formatted error message.
   * @throws IllegalStateException If given condition is not met.
   */
  public static void checkState(boolean condition, String errorMessageFormat, Object... errorMessageArgs) throws IllegalStateException {
    if (!condition) {
      throw new IllegalStateException(String.format(errorMessageFormat, errorMessageArgs));
    }
  }

  /**
   * Check whatever the some method argument meets given <code>condition</code> and if not, the
   * {@link IllegalArgumentException} is thrown. This method is useful for single-line checking of
   * method arguments. This method can be used in this way:
   *
   * <pre>
   * public void setAge(int age) {
   *   Preconditions.checkArgument(age &gt; 0); // minimal age
   *   Preconditions.checkArgument(age &lt; 130); // maximal age
   *
   *   this.age = age;
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @throws IllegalArgumentException If given condition is not met.
   */
  public static void checkArgument(boolean condition) throws IllegalArgumentException {
    if (!condition) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Check whatever the some method argument meets given <code>condition</code> and if not, the
   * {@link IllegalArgumentException} with given error message is thrown. This method is useful for
   * single-line checking of method arguments. This method can be used in this way:
   *
   * <pre>
   * public void setAge(int age) {
   *   Preconditions.checkArgument(age &gt; 0, &quot;Age is too low&quot;); // minimal age
   *   Preconditions.checkArgument(age &lt; 130, &quot;Age is too high&quot;); // maximal age
   *
   *   this.age = age;
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @param errorMessage the error message for thrown exception in case that given
   *          <code>condition</code> is not met.
   * @throws IllegalArgumentException If given condition is not met.
   */
  public static void checkArgument(boolean condition, String errorMessage) throws IllegalArgumentException {
    if (!condition) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  /**
   * Check whatever the some method argument meets given <code>condition</code> and if not, the
   * {@link IllegalArgumentException} with given formatted error message is thrown. This method is
   * useful for single-line checking of method arguments. This method can be used in this way:
   *
   * <pre>
   * public void setAge(int age) {
   *   Preconditions.checkArgument(age &gt; 0, &quot;Age %d is too low&quot;, age); // minimal age
   *   Preconditions.checkArgument(age &lt; 130, &quot;Age %d is too high&quot;, age); // maximal age
   *
   *   this.age = age;
   * }
   * </pre>
   *
   * @param condition the condition which will be checked whatever is met.
   * @param errorMessageFormat the formatted error message for thrown exception in case that given
   *          <code>condition</code> is not met.
   * @param errorMessageArgs the arguments for given formatted error message.
   * @throws IllegalArgumentException If given condition is not met.
   */
  public static void checkArgument(boolean condition, String errorMessageFormat, Object... errorMessageArgs) throws IllegalArgumentException {
    if (!condition) {
      throw new IllegalArgumentException(String.format(errorMessageFormat, errorMessageArgs));
    }
  }

}
