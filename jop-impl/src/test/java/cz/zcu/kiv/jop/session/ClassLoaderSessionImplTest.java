package cz.zcu.kiv.jop.session;

import org.junit.Assert;
import org.junit.Test;

import cz.zcu.kiv.jop.annotation.class_provider.ClassLoaderConst;

/**
 * Test of session class {@link ClassLoaderSessionImpl}.
 *
 * @author Mr.FrAnTA
 */
public class ClassLoaderSessionImplTest {

  /** Constant for first symbolic name. */
  private static final String NAME1 = "NAME1";
  /** Constant for second symbolic name. */
  private static final String NAME2 = "NAME2";

  /**
   * Test of method {@link ClassLoaderSession#setClassLoader} for null name. Expected exception
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetClassLoaderForNullName() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    session.setClassLoader(null, ClassLoader.getSystemClassLoader());
  }

  /**
   * Test of method {@link ClassLoaderSession#setClassLoader} for empty name. Expected exception
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetClassLoaderForEmptyName() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    session.setClassLoader("", ClassLoader.getSystemClassLoader());
  }

  /**
   * Test of method {@link ClassLoaderSession#setClassLoader} for blank name. Expected exception
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetClassLoaderForBlankName() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    session.setClassLoader(" ", ClassLoader.getSystemClassLoader());
  }

  /**
   * Test of method {@link ClassLoaderSession#setClassLoader} for reserved name. Expected exception
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetClassLoaderForReservedName() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    session.setClassLoader(ClassLoaderConst.SYSTEM, ClassLoader.getSystemClassLoader());
  }

  /**
   * Test of method {@link ClassLoaderSession#setClassLoader} for not set class loader.
   */
  @Test
  public void testSetClassLoaderForUnsetClassloader() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution -----*/
    ClassLoader classLoader = session.setClassLoader(NAME1, ClassLoader.getSystemClassLoader());

    /*----- Verify -----*/
    Assert.assertNull(classLoader);
    Assert.assertEquals(session.getClassLoader(NAME1), ClassLoader.getSystemClassLoader());
  }

  /**
   * Test of method {@link ClassLoaderSession#setClassLoader} for set null class loader which
   * "unset" the class loader.
   */
  @Test
  public void testSetNullClassLoader() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();
    session.setClassLoader(NAME1, ClassLoader.getSystemClassLoader());
    session.setClassLoader(NAME1, null);

    /*----- Execution & Verify -----*/
    Assert.assertNull(session.getClassLoader(NAME1));
  }

  /**
   * Test of method {@link ClassLoaderSession#setClassLoader} where are set two different class
   * loaders for same name.
   */
  @Test
  public void testReSetClassLoader() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();
    ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
    ClassLoader classLoader2 = new ClassLoader() {};

    /*----- Execution -----*/
    ClassLoader oldClassLoader1 = session.setClassLoader(NAME1, classLoader1);
    ClassLoader oldClassLoader2 = session.setClassLoader(NAME1, classLoader2);

    /*----- Verify -----*/
    Assert.assertNull(oldClassLoader1);
    Assert.assertEquals(classLoader1, oldClassLoader2);
    Assert.assertEquals(classLoader2, session.getClassLoader(NAME1));
  }

  /**
   * Test of method {@link ClassLoaderSession#getClassLoader} for null name. Expected exception
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetClassLoaderForNullName() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    session.getClassLoader(null);
  }

  /**
   * Test of method {@link ClassLoaderSession#getClassLoader} for empty name. Expected exception
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetClassLoaderForEmptyName() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    session.getClassLoader("");
  }

  /**
   * Test of method {@link ClassLoaderSession#getClassLoader} for blank name. Expected exception
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetClassLoaderForBlankName() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    session.getClassLoader(" ");
  }

  /**
   * Test of method {@link ClassLoaderSession#getClassLoader} for reserved name. Expected exception
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetClassLoaderForReservedName() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    session.getClassLoader(ClassLoaderConst.SYSTEM);
  }

  /**
   * Test of method {@link ClassLoaderSession#getClassLoader} for not get class loader.
   */
  @Test
  public void testGetClassLoaderForUngetClassloader() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();

    /*----- Execution & Verify -----*/
    Assert.assertNull(session.getClassLoader(NAME1));
  }

  /**
   * Test of method {@link ClassLoaderSession#getClassLoader} for get class loader.
   */
  @Test
  public void testGetClassLoader() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();
    session.setClassLoader(NAME1, ClassLoader.getSystemClassLoader());

    /*----- Execution & Verify -----*/
    Assert.assertEquals(ClassLoader.getSystemClassLoader(), session.getClassLoader(NAME1));
  }

  /**
   * Test of methods {@link ClassLoaderSession#setClassLoader} and
   * {@link ClassLoaderSession#getClassLoader} for setting/getting of two different class loaders
   * for two different names.
   */
  @Test
  public void testSetAndGetClassLoaderForDifferentNames() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();
    ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
    ClassLoader classLoader2 = new ClassLoader() {};

    /*----- Execution -----*/
    ClassLoader oldClassLoader1 = session.setClassLoader(NAME1, classLoader1);
    ClassLoader oldClassLoader2 = session.setClassLoader(NAME2, classLoader2);

    /*----- Verify -----*/
    Assert.assertNull(oldClassLoader1);
    Assert.assertNull(oldClassLoader2);
    Assert.assertEquals(classLoader1, session.getClassLoader(NAME1));
    Assert.assertEquals(classLoader2, session.getClassLoader(NAME2));
  }

  /**
   * Test of method {@link ClassLoaderSession#clear()} which clears session.
   */
  @Test
  public void testClear() {
    /*----- Preparation -----*/
    ClassLoaderSessionImpl session = new ClassLoaderSessionImpl();
    session.getClassLoader(NAME1);

    /*----- Execution -----*/
    session.clear();

    /*----- Verify -----*/
    Assert.assertNull(session.getClassLoader(NAME1));
  }
}
