package cz.zcu.kiv.jop.session;

import java.lang.annotation.Annotation;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of session class {@link ExtendedRandomGeneratorSessionImpl}.
 *
 * @author Mr.FrAnTA
 */
public class ExtendedRandomGeneratorSessionImplTest {

  /**
   * Test of method {@link ExtendedRandomGeneratorSessionImpl#setRandomGenerator} for null
   * annotation which expects {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetGeneratorForNullAnnotation() {
    /*----- Preparation -----*/
    ExtendedRandomGeneratorSessionImpl session = new ExtendedRandomGeneratorSessionImpl();

    /*----- Execution & Verify -----*/
    session.setRandomGenerator(null, new Random());
  }

  /**
   * Test of method {@link ExtendedRandomGeneratorSessionImpl#setRandomGenerator} for null generator
   * which "resets" the generator in session.
   */
  @Test
  public void testSetGeneratorForNullGenerator() {
    /*----- Preparation -----*/
    ExtendedRandomGeneratorSessionImpl session = new ExtendedRandomGeneratorSessionImpl();

    Annotation foo = new FooAnnotation(0);
    Random fooRand = new Random();

    /*----- Execution -----*/
    session.setRandomGenerator(foo, fooRand);
    Random oldRand = session.setRandomGenerator(foo, null);

    /*----- Verify -----*/
    Assert.assertEquals(fooRand, oldRand);
    Assert.assertNotEquals(fooRand, session.getRandomGenerator(foo));
  }

  /**
   * Test of method {@link ExtendedRandomGeneratorSessionImpl#setRandomGenerator} for given random
   * generator which sets the generator in session.
   */
  @Test
  public void testSetGenerator() {
    /*----- Preparation -----*/
    ExtendedRandomGeneratorSessionImpl session = new ExtendedRandomGeneratorSessionImpl();

    Annotation foo = new FooAnnotation(0);
    Random fooRand = new Random();

    /*----- Execution -----*/
    Random oldRand = session.setRandomGenerator(foo, fooRand);

    /*----- Verify -----*/
    Assert.assertNull(oldRand); // no previous generator
    Assert.assertEquals(fooRand, session.getRandomGenerator(foo));
  }

  /**
   * Test of method {@link ExtendedRandomGeneratorSessionImpl#setRandomGenerator} which re-sets the
   * generator in session.
   */
  @Test
  public void testReSetGenerator() {
    /*----- Preparation -----*/
    ExtendedRandomGeneratorSessionImpl session = new ExtendedRandomGeneratorSessionImpl();

    Annotation foo = new FooAnnotation(0);
    Random newRand = new Random();

    /*----- Execution -----*/
    Random fooRand = session.getRandomGenerator(foo);
    Random oldRand = session.setRandomGenerator(foo, newRand);

    /*----- Verify -----*/
    Assert.assertEquals(fooRand, oldRand);
    Assert.assertEquals(newRand, session.getRandomGenerator(foo));
  }

  /**
   * Test of method {@link ExtendedRandomGeneratorSessionImpl#setRandomGenerator} which sets
   * generator for different instances of annotations.
   */
  @Test
  public void testSetGeneratorForDifferentAnnotations() {
    /*----- Preparation -----*/
    ExtendedRandomGeneratorSessionImpl session = new ExtendedRandomGeneratorSessionImpl();

    Annotation foo0 = new FooAnnotation(0);
    Random fooRand0 = new Random();

    Annotation foo1 = new FooAnnotation(1);
    Random fooRand1 = new Random();

    /*----- Execution -----*/
    session.setRandomGenerator(foo0, fooRand0);
    session.setRandomGenerator(foo1, fooRand1);

    /*----- Verify -----*/
    Assert.assertNotEquals(session.getRandomGenerator(foo0), session.getRandomGenerator(foo1));
  }

  /**
   * Test of method {@link ExtendedRandomGeneratorSessionImpl#getRandomGenerator} which for first
   * call returns new instance of random generator.
   */
  @Test
  public void testGetGeneratorForFirstGet() {
    /*----- Preparation -----*/
    ExtendedRandomGeneratorSessionImpl session = new ExtendedRandomGeneratorSessionImpl();

    Annotation foo = new FooAnnotation(0);

    /*----- Execution -----*/
    Assert.assertNotNull(session.getRandomGenerator(foo));
  }

  /**
   * Test of method {@link ExtendedRandomGeneratorSessionImpl#getRandomGenerator} which returns same
   * generator for different instances of annotations.
   */
  @Test
  public void testGetGeneratorForDifferentAnnotations() {
    /*----- Preparation -----*/
    ExtendedRandomGeneratorSessionImpl session = new ExtendedRandomGeneratorSessionImpl();

    Annotation foo0 = new FooAnnotation(0);
    Annotation foo1 = new FooAnnotation(1);

    /*----- Execution -----*/
    Assert.assertNotEquals(session.getRandomGenerator(foo0), session.getRandomGenerator(foo1));
  }

  /**
   * Test of method {@link ExtendedRandomGeneratorSessionImpl#clear()} which removes all stored
   * random generators in cache.
   */
  @Test
  public void testClear() {
    /*----- Preparation -----*/
    ExtendedRandomGeneratorSessionImpl session = new ExtendedRandomGeneratorSessionImpl();

    Annotation foo = new FooAnnotation(0);
    Random fooRand = new Random();
    session.setRandomGenerator(foo, fooRand);

    /*----- Execution -----*/
    session.clear();

    /*----- Verify -----*/
    Assert.assertNotEquals(fooRand, session.getRandomGenerator(foo));
  }

  /**
   * Helper annotation which may be used in tests.
   */
  private static @interface Foo {
    /** Attribute for value. */
    public int value();
  }

  /**
   * Helper "implementation" of annotation {@link Foo} which may be used for manual creation of
   * annotation instance.
   */
  private class FooAnnotation implements Foo {
    /** Annotation value. */
    private final int value;

    /**
     * Constructs annotation.
     *
     * @param value value of annotation.
     */
    public FooAnnotation(int value) {
      this.value = value;
    }

    /**
     * Returns type of annotation (this class).
     *
     * @return type of annotation.
     */
    public Class<? extends Annotation> annotationType() {
      return Foo.class;
    }

    /**
     * @return Value of annotation.
     */
    public int value() {
      return value;
    }
  }
}
