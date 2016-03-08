package cz.zcu.kiv.jop.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests of helper static class {@link ArrayUtils}.
 *
 * @author Mr.FrAnTA
 */
public class ArrayUtilsTest {

  /** Random generator for generating arrays. */
  private static final Random rand = new Random();

  /** Minimal length of array. */
  private static final int MIN_ARRAY_LENGTH = 10;
  /** Maximal length of array. */
  private static final int MAX_ARRAY_LENGTH = 50;

  /** Constant for array of wrapper (object) types. */
  @SuppressWarnings("rawtypes")
  //@formatter:off
  private static final Class[] wrappers = {
      Boolean.class,
      Byte.class,
      Character.class,
      Short.class,
      Integer.class,
      Long.class,
      Float.class,
      Double.class
  };
  // @formatter:on

  /** Constant for array of primitive types. */
  @SuppressWarnings("rawtypes")
  //@formatter:off
  private static final Class[] primitives = {
      boolean.class,
      byte.class,
      char.class,
      short.class,
      int.class,
      long.class,
      float.class,
      double.class
  };
  // @formatter:on

  /**
   * Returns random array length.
   *
   * @return The random array length.
   */
  protected int getArrayLength() {
    return MIN_ARRAY_LENGTH + rand.nextInt(MAX_ARRAY_LENGTH - MIN_ARRAY_LENGTH);
  }

  // ----- Boolean/boolean -----------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(boolean[])} for <code>null</code> value.
   */
  @Test
  public void testToBooleanArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((boolean[])null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(boolean[])} for empty array.
   */
  @Test
  public void testToBooleanArrayForEmptyArray() {
    Assert.assertArrayEquals(new Boolean[0], ArrayUtils.toObjectArray(new boolean[0]));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(boolean[])} for non-empty random array.
   */
  @Test
  public void testToBooleanArrayForRandomArray() {
    int size = getArrayLength();
    boolean[] primitive = new boolean[size];
    Boolean[] object = new Boolean[size];
    for (int i = 0; i < size; i++) {
      boolean value = rand.nextBoolean();
      primitive[i] = value;
      object[i] = new Boolean(value);
    }

    Assert.assertArrayEquals(object, ArrayUtils.toObjectArray(primitive));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Boolean[])} and
   * {@link ArrayUtils#toPrimitiveArray(Boolean[], boolean)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveBooleanArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Boolean[])null));
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Boolean[])null, true));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Boolean[])} and
   * {@link ArrayUtils#toPrimitiveArray(Boolean[], boolean)} for empty array.
   */
  @Test
  public void testToPrimitiveBooleanArrayForEmptyArray() {
    Assert.assertArrayEquals(new boolean[0], ArrayUtils.toPrimitiveArray(new Boolean[0]));
    Assert.assertArrayEquals(new boolean[0], ArrayUtils.toPrimitiveArray(new Boolean[0], true));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Boolean[])} and
   * {@link ArrayUtils#toPrimitiveArray(Boolean[], boolean)} for non-empty random array.
   */
  @Test
  public void testToPrimitiveBooleanArrayForRandomArray() {
    int size = getArrayLength();
    final boolean valueForNull = true;

    boolean[] primitive = new boolean[size];
    boolean[] primitive2 = new boolean[size];
    Boolean[] object = new Boolean[size];
    for (int i = 0; i < size; i++) {
      boolean value = rand.nextBoolean();
      if (i % 10 == 0) { // each 10th value is null
        primitive[i] = false;
        primitive2[i] = valueForNull;
        object[i] = null;
      }
      else {
        primitive[i] = value;
        primitive2[i] = value;
        object[i] = new Boolean(value);
      }
    }

    Assert.assertArrayEquals(primitive, ArrayUtils.toPrimitiveArray(object));
    Assert.assertArrayEquals(primitive2, ArrayUtils.toPrimitiveArray(object, valueForNull));
  }

  // ----- Byte/byte -----------------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(byte[])} for <code>null</code> value.
   */
  @Test
  public void testToByteArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((byte[])null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(byte[])} for empty array.
   */
  @Test
  public void testToByteArrayForEmptyArray() {
    Assert.assertArrayEquals(new Byte[0], ArrayUtils.toObjectArray(new byte[0]));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(byte[])} for non-empty random array.
   */
  @Test
  public void testToByteArrayForRandomArray() {
    int size = getArrayLength();
    byte[] primitive = new byte[size];
    Byte[] object = new Byte[size];
    for (int i = 0; i < size; i++) {
      byte value = (byte)rand.nextInt(Byte.MAX_VALUE);
      primitive[i] = value;
      object[i] = new Byte(value);
    }

    Assert.assertArrayEquals(object, ArrayUtils.toObjectArray(primitive));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Byte[])} and
   * {@link ArrayUtils#toPrimitiveArray(Byte[], byte)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveByteArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Byte[])null));
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Byte[])null, (byte)42));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Byte[])} and
   * {@link ArrayUtils#toPrimitiveArray(Byte[], byte)} for empty array.
   */
  @Test
  public void testToPrimitiveByteArrayForEmptyArray() {
    Assert.assertArrayEquals(new byte[0], ArrayUtils.toPrimitiveArray(new Byte[0]));
    Assert.assertArrayEquals(new byte[0], ArrayUtils.toPrimitiveArray(new Byte[0], (byte)42));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Byte[])} and
   * {@link ArrayUtils#toPrimitiveArray(Byte[], byte)} for non-empty random array.
   */
  @Test
  public void testToPrimitiveByteArrayForRandomArray() {
    int size = getArrayLength();
    final byte valueForNull = (byte)42;

    byte[] primitive = new byte[size];
    byte[] primitive2 = new byte[size];
    Byte[] object = new Byte[size];
    for (int i = 0; i < size; i++) {
      byte value = (byte)rand.nextInt(Byte.MAX_VALUE);
      if (i % 10 == 0) { // each 10th value is null
        primitive[i] = 0;
        primitive2[i] = valueForNull;
        object[i] = null;
      }
      else {
        primitive[i] = value;
        primitive2[i] = value;
        object[i] = new Byte(value);
      }
    }

    Assert.assertArrayEquals(primitive, ArrayUtils.toPrimitiveArray(object));
    Assert.assertArrayEquals(primitive2, ArrayUtils.toPrimitiveArray(object, valueForNull));
  }

  // ----- Character/char ------------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(char[])} for <code>null</code> value.
   */
  @Test
  public void testToCharacterArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((char[])null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(char[])} for empty array.
   */
  @Test
  public void testToCharacterArrayForEmptyArray() {
    Assert.assertArrayEquals(new Character[0], ArrayUtils.toObjectArray(new char[0]));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(char[])} for non-empty random array.
   */
  @Test
  public void testToCharacterArrayForRandomArray() {
    int size = getArrayLength();
    char[] primitive = new char[size];
    Character[] object = new Character[size];
    for (int i = 0; i < size; i++) {
      char value = (char)rand.nextInt(Character.MAX_VALUE);
      primitive[i] = value;
      object[i] = new Character(value);
    }

    Assert.assertArrayEquals(object, ArrayUtils.toObjectArray(primitive));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Character[])} and
   * {@link ArrayUtils#toPrimitiveArray(Character[], char)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveCharacterArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Character[])null));
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Character[])null, (char)42));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Character[])} and
   * {@link ArrayUtils#toPrimitiveArray(Character[], char)} for empty array.
   */
  @Test
  public void testToPrimitiveCharacterArrayForEmptyArray() {
    Assert.assertArrayEquals(new char[0], ArrayUtils.toPrimitiveArray(new Character[0]));
    Assert.assertArrayEquals(new char[0], ArrayUtils.toPrimitiveArray(new Character[0], (char)42));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Character[])} and
   * {@link ArrayUtils#toPrimitiveArray(Character[], char)} for non-empty random array.
   */
  @Test
  public void testToPrimitiveCharacterArrayForRandomArray() {
    int size = getArrayLength();
    final char valueForNull = 42;

    char[] primitive = new char[size];
    char[] primitive2 = new char[size];
    Character[] object = new Character[size];
    for (int i = 0; i < size; i++) {
      char value = (char)rand.nextInt(Character.MAX_VALUE);
      if (i % 10 == 0) { // each 10th value is null
        primitive[i] = 0;
        primitive2[i] = valueForNull;
        object[i] = null;
      }
      else {
        primitive[i] = value;
        primitive2[i] = value;
        object[i] = new Character(value);
      }
    }

    Assert.assertArrayEquals(primitive, ArrayUtils.toPrimitiveArray(object));
    Assert.assertArrayEquals(primitive2, ArrayUtils.toPrimitiveArray(object, valueForNull));
  }

  // ----- Short/short ---------------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(short[])} for <code>null</code> value.
   */
  @Test
  public void testToShortArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((short[])null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(short[])} for empty array.
   */
  @Test
  public void testToShortArrayForEmptyArray() {
    Assert.assertArrayEquals(new Short[0], ArrayUtils.toObjectArray(new short[0]));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(short[])} for non-empty random array.
   */
  @Test
  public void testToShortArrayForRandomArray() {
    int size = getArrayLength();
    short[] primitive = new short[size];
    Short[] object = new Short[size];
    for (int i = 0; i < size; i++) {
      short value = (short)rand.nextInt(Short.MAX_VALUE);
      primitive[i] = value;
      object[i] = new Short(value);
    }

    Assert.assertArrayEquals(object, ArrayUtils.toObjectArray(primitive));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Short[])} and
   * {@link ArrayUtils#toPrimitiveArray(Short[], short)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveShortArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Short[])null));
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Short[])null, (short)42));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Short[])} and
   * {@link ArrayUtils#toPrimitiveArray(Short[], short)} for empty array.
   */
  @Test
  public void testToPrimitiveShortArrayForEmptyArray() {
    Assert.assertArrayEquals(new short[0], ArrayUtils.toPrimitiveArray(new Short[0]));
    Assert.assertArrayEquals(new short[0], ArrayUtils.toPrimitiveArray(new Short[0], (short)42));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Short[])} and
   * {@link ArrayUtils#toPrimitiveArray(Short[], short)} for non-empty random array.
   */
  @Test
  public void testToPrimitiveShortArrayForRandomArray() {
    int size = getArrayLength();
    final short valueForNull = 42;

    short[] primitive = new short[size];
    short[] primitive2 = new short[size];
    Short[] object = new Short[size];
    for (int i = 0; i < size; i++) {
      short value = (short)rand.nextInt(Short.MAX_VALUE);
      if (i % 10 == 0) { // each 10th value is null
        primitive[i] = 0;
        primitive2[i] = valueForNull;
        object[i] = null;
      }
      else {
        primitive[i] = value;
        primitive2[i] = value;
        object[i] = new Short(value);
      }
    }

    Assert.assertArrayEquals(primitive, ArrayUtils.toPrimitiveArray(object));
    Assert.assertArrayEquals(primitive2, ArrayUtils.toPrimitiveArray(object, valueForNull));
  }

  // ----- Integer/int ---------------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(int[])} for <code>null</code> value.
   */
  @Test
  public void testToIntegerArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((int[])null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(int[])} for empty array.
   */
  @Test
  public void testToIntegerArrayForEmptyArray() {
    Assert.assertArrayEquals(new Integer[0], ArrayUtils.toObjectArray(new int[0]));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(int[])} for non-empty random array.
   */
  @Test
  public void testToIntegerArrayForRandomArray() {
    int size = getArrayLength();
    int[] primitive = new int[size];
    Integer[] object = new Integer[size];
    for (int i = 0; i < size; i++) {
      int value = rand.nextInt();
      primitive[i] = value;
      object[i] = new Integer(value);
    }

    Assert.assertArrayEquals(object, ArrayUtils.toObjectArray(primitive));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Integer[])} and
   * {@link ArrayUtils#toPrimitiveArray(Integer[], int)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveIntegerArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Integer[])null));
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Integer[])null, 42));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Integer[])} and
   * {@link ArrayUtils#toPrimitiveArray(Integer[], int)} for empty array.
   */
  @Test
  public void testToPrimitiveIntegerArrayForEmptyArray() {
    Assert.assertArrayEquals(new int[0], ArrayUtils.toPrimitiveArray(new Integer[0]));
    Assert.assertArrayEquals(new int[0], ArrayUtils.toPrimitiveArray(new Integer[0], 42));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Integer[])} and
   * {@link ArrayUtils#toPrimitiveArray(Integer[], int)} for non-empty random array.
   */
  @Test
  public void testToPrimitiveIntegerArrayForRandomArray() {
    int size = getArrayLength();
    final int valueForNull = 42;

    int[] primitive = new int[size];
    int[] primitive2 = new int[size];
    Integer[] object = new Integer[size];
    for (int i = 0; i < size; i++) {
      int value = rand.nextInt();
      if (i % 10 == 0) { // each 10th value is null
        primitive[i] = 0;
        primitive2[i] = valueForNull;
        object[i] = null;
      }
      else {
        primitive[i] = value;
        primitive2[i] = value;
        object[i] = new Integer(value);
      }
    }

    Assert.assertArrayEquals(primitive, ArrayUtils.toPrimitiveArray(object));
    Assert.assertArrayEquals(primitive2, ArrayUtils.toPrimitiveArray(object, valueForNull));
  }

  // ----- Long/long -----------------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(long[])} for <code>null</code> value.
   */
  @Test
  public void testToLongArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((long[])null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(long[])} for empty array.
   */
  @Test
  public void testToLongArrayForEmptyArray() {
    Assert.assertArrayEquals(new Long[0], ArrayUtils.toObjectArray(new long[0]));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(long[])} for non-empty random array.
   */
  @Test
  public void testToLongArrayForRandomArray() {
    int size = getArrayLength();
    long[] primitive = new long[size];
    Long[] object = new Long[size];
    for (int i = 0; i < size; i++) {
      long value = rand.nextLong();
      primitive[i] = value;
      object[i] = new Long(value);
    }

    Assert.assertArrayEquals(object, ArrayUtils.toObjectArray(primitive));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Long[])} and
   * {@link ArrayUtils#toPrimitiveArray(Long[], long)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveLongArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Long[])null));
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Long[])null, 42L));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Long[])} and
   * {@link ArrayUtils#toPrimitiveArray(Long[], long)} for empty array.
   */
  @Test
  public void testToPrimitiveLongArrayForEmptyArray() {
    Assert.assertArrayEquals(new long[0], ArrayUtils.toPrimitiveArray(new Long[0]));
    Assert.assertArrayEquals(new long[0], ArrayUtils.toPrimitiveArray(new Long[0], 42L));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Long[])} and
   * {@link ArrayUtils#toPrimitiveArray(Long[], long)} for non-empty random array.
   */
  @Test
  public void testToPrimitiveLongArrayForRandomArray() {
    int size = getArrayLength();
    final long valueForNull = 42;

    long[] primitive = new long[size];
    long[] primitive2 = new long[size];
    Long[] object = new Long[size];
    for (int i = 0; i < size; i++) {
      long value = rand.nextLong();
      if (i % 10 == 0) { // each 10th value is null
        primitive[i] = 0;
        primitive2[i] = valueForNull;
        object[i] = null;
      }
      else {
        primitive[i] = value;
        primitive2[i] = value;
        object[i] = new Long(value);
      }
    }

    Assert.assertArrayEquals(primitive, ArrayUtils.toPrimitiveArray(object));
    Assert.assertArrayEquals(primitive2, ArrayUtils.toPrimitiveArray(object, valueForNull));
  }

  // ----- Float/float ---------------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(float[])} for <code>null</code> value.
   */
  @Test
  public void testToFloatArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((float[])null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(float[])} for empty array.
   */
  @Test
  public void testToFloatArrayForEmptyArray() {
    Assert.assertArrayEquals(new Float[0], ArrayUtils.toObjectArray(new float[0]));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(float[])} for non-empty random array.
   */
  @Test
  public void testToFloatArrayForRandomArray() {
    int size = getArrayLength();
    float[] primitive = new float[size];
    Float[] object = new Float[size];
    for (int i = 0; i < size; i++) {
      float value = rand.nextFloat();
      primitive[i] = value;
      object[i] = new Float(value);
    }

    Assert.assertArrayEquals(object, ArrayUtils.toObjectArray(primitive));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Float[])} and
   * {@link ArrayUtils#toPrimitiveArray(Float[], float)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveFloatArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Float[])null));
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Float[])null, 42f));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Float[])} and
   * {@link ArrayUtils#toPrimitiveArray(Float[], float)} for empty array.
   */
  @Test
  public void testToPrimitiveFloatArrayForEmptyArray() {
    Assert.assertArrayEquals(new float[0], ArrayUtils.toPrimitiveArray(new Float[0]), 0.0f);
    Assert.assertArrayEquals(new float[0], ArrayUtils.toPrimitiveArray(new Float[0], 42f), 0.0f);
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Float[])} and
   * {@link ArrayUtils#toPrimitiveArray(Float[], float)} for non-empty random array.
   */
  @Test
  public void testToPrimitiveFloatArrayForRandomArray() {
    int size = getArrayLength();
    final float valueForNull = 42f;

    float[] primitive = new float[size];
    float[] primitive2 = new float[size];
    Float[] object = new Float[size];
    for (int i = 0; i < size; i++) {
      float value = rand.nextFloat();
      if (i % 10 == 0) { // each 10th value is null
        primitive[i] = 0f;
        primitive2[i] = valueForNull;
        object[i] = null;
      }
      else {
        primitive[i] = value;
        primitive2[i] = value;
        object[i] = new Float(value);
      }
    }

    Assert.assertArrayEquals(primitive, ArrayUtils.toPrimitiveArray(object), 0.0f);
    Assert.assertArrayEquals(primitive2, ArrayUtils.toPrimitiveArray(object, valueForNull), 0.0f);
  }

  // ----- Double/double -------------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(double[])} for <code>null</code> value.
   */
  @Test
  public void testToDoubleArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((double[])null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(double[])} for empty array.
   */
  @Test
  public void testToDoubleArrayForEmptyArray() {
    Assert.assertArrayEquals(new Double[0], ArrayUtils.toObjectArray(new double[0]));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(double[])} for non-empty random array.
   */
  @Test
  public void testToDoubleArrayForRandomArray() {
    int size = getArrayLength();
    double[] primitive = new double[size];
    Double[] object = new Double[size];
    for (int i = 0; i < size; i++) {
      double value = rand.nextDouble();
      primitive[i] = value;
      object[i] = new Double(value);
    }

    Assert.assertArrayEquals(object, ArrayUtils.toObjectArray(primitive));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Double[])} and
   * {@link ArrayUtils#toPrimitiveArray(Double[], double)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveDoubleArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Double[])null));
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Double[])null, 42.0));
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Double[])} and
   * {@link ArrayUtils#toPrimitiveArray(Double[], double)} for empty array.
   */
  @Test
  public void testToPrimitiveDoubleArrayForEmptyArray() {
    Assert.assertArrayEquals(new double[0], ArrayUtils.toPrimitiveArray(new Double[0]), 0.0);
    Assert.assertArrayEquals(new double[0], ArrayUtils.toPrimitiveArray(new Double[0], 42.0), 0.0);
  }

  /**
   * Test of methods {@link ArrayUtils#toPrimitiveArray(Double[])} and
   * {@link ArrayUtils#toPrimitiveArray(Double[], double)} for non-empty random array.
   */
  @Test
  public void testToPrimitiveDoubleArrayForRandomArray() {
    int size = getArrayLength();
    final double valueForNull = 42.0;

    double[] primitive = new double[size];
    double[] primitive2 = new double[size];
    Double[] object = new Double[size];
    for (int i = 0; i < size; i++) {
      double value = rand.nextDouble();
      if (i % 10 == 0) { // each 10th value is null
        primitive[i] = 0.0;
        primitive2[i] = valueForNull;
        object[i] = null;
      }
      else {
        primitive[i] = value;
        primitive2[i] = value;
        object[i] = new Double(value);
      }
    }

    Assert.assertArrayEquals(primitive, ArrayUtils.toPrimitiveArray(object), 0.0);
    Assert.assertArrayEquals(primitive2, ArrayUtils.toPrimitiveArray(object, valueForNull), 0.0);
  }

  // ----- Generic -------------------------------------------------------------

  /**
   * Test of method {@link ArrayUtils#toObjectArray(Object)} for <code>null</code> value.
   */
  @Test
  public void testToObjectArrayForNull() {
    Assert.assertNull(ArrayUtils.toObjectArray((Object)null));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(double[])} for non-array value. Expected
   * exception: {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testToObjectArrayForNonArray() {
    Assert.assertArrayEquals(new Double[0], ArrayUtils.toObjectArray(new String("Hello world")));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(double[])} for multidimensional array value.
   * Expected exception: {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testToObjectArrayForMultidimensionalArray() {
    ArrayUtils.toObjectArray(new Double[0][0]);
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(double[])} for object arrays (will return same
   * array).
   */
  @Test
  public void testToObjectArrayForObjectArray() {
    Object[] array = new Object[getArrayLength()];
    for (int i = 0; i < array.length; i++) {
      array[i] = rand.nextBoolean() ? new Integer(i) : new Double(i);
    }

    Assert.assertArrayEquals(array, ArrayUtils.toObjectArray(array));
  }

  /**
   * Test of method {@link ArrayUtils#toObjectArray(double[])} for arrays of primitives.
   */
  @Test
  public void testToObjectArrayForPrimitiveArray() {
    for (int i = 0; i < primitives.length; i++) {
      int size = getArrayLength();
      Object[] expected = (Object[])Array.newInstance(wrappers[i], size);
      Object array = Array.newInstance(primitives[i], size);
      for (int j = 0; j < size; j++) {
        expected[j] = Defaults.getDefaultValue(primitives[i]);
        Array.set(array, j, Defaults.getDefaultValue(primitives[i]));
      }
      Assert.assertArrayEquals(expected, ArrayUtils.toObjectArray(array));
    }
  }

  /**
   * Test of method {@link ArrayUtils#toPrimitiveArray(Object)} for <code>null</code> value.
   */
  @Test
  public void testToPrimitiveArrayForNull() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray((Object)null));
  }

  /**
   * Test of method {@link ArrayUtils#toPrimitiveArray(Object)} for non-array value. Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testToPrimitiveArrayForNotArray() {
    Assert.assertNull(ArrayUtils.toPrimitiveArray(new Object()));
  }

  /**
   * Test of method {@link ArrayUtils#toPrimitiveArray(Object)} for primitive array value.
   */
  @Test
  public void testToPrimitiveArrayForPrimitiveArray() {
    boolean[] expected = new boolean[] {true, false, true};

    Assert.assertArrayEquals(expected, (boolean[])ArrayUtils.toPrimitiveArray(expected));
  }

  /**
   * Test of method {@link ArrayUtils#toPrimitiveArray(Object)} for multidimensional array value.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testToPrimitiveArrayForMultidimensionalArray() {
    Double[][] array = new Double[0][0];

    ArrayUtils.toPrimitiveArray(array);
  }

  /**
   * Test of method {@link ArrayUtils#toPrimitiveArray(Object)} for array of Void values. Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testToPrimitiveArrayForVoidArray() {
    Void[] array = new Void[0];

    ArrayUtils.toPrimitiveArray(array);
  }

  /**
   * Test of method {@link ArrayUtils#toPrimitiveArray(Object)} for array of non-wrapper values.
   * Expected {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testToPrimitiveArrayForNonWrapperArray() {
    Object[] array = new Object[0];
    ArrayUtils.toPrimitiveArray(array);
  }

  /**
   * Test of method {@link ArrayUtils#toPrimitiveArray(Object)} for arrays of wrapper values.
   */
  @Test
  public void testToPrimitiveArrayForWrapperArray() {
    Assert.assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, (boolean[])ArrayUtils.toPrimitiveArray((Object)ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY));
    Assert.assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, (byte[])ArrayUtils.toPrimitiveArray((Object)ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY));
    Assert.assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, (char[])ArrayUtils.toPrimitiveArray((Object)ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY));
    Assert.assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, (short[])ArrayUtils.toPrimitiveArray((Object)ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY));
    Assert.assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, (int[])ArrayUtils.toPrimitiveArray((Object)ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY));
    Assert.assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, (long[])ArrayUtils.toPrimitiveArray((Object)ArrayUtils.EMPTY_LONG_OBJECT_ARRAY));
    Assert.assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, (float[])ArrayUtils.toPrimitiveArray((Object)ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY), 0);
    Assert.assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, (double[])ArrayUtils.toPrimitiveArray((Object)ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY), 0);
  }

  /**
   * Test of method {@link ArrayUtils#cloneArray} for null.
   */
  @Test
  public void testCloneArrayForNull() {
    Assert.assertEquals(null, ArrayUtils.cloneArray(null));
  }

  /**
   * Test of method {@link ArrayUtils#cloneArray} for non-array object.Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCloneArrayForNoArray() {
    ArrayUtils.cloneArray(new Object());
  }

  /**
   * Test of method {@link ArrayUtils#cloneArray} for multidimensional array object.
   */
  @Test
  public void testCloneArrayForMultidimensionalArray() {
    Double[][] expected = new Double[][] { {0d, 1d, 2d}, {3d}, {4d, 5d}};

    Assert.assertArrayEquals(expected, (Double[][])ArrayUtils.cloneArray(expected));
  }

  /**
   * Test of method {@link ArrayUtils#cloneArray} for object arrays.
   */
  @Test
  public void testCloneArrayForObjectArray() {
    Object[] array = new Object[getArrayLength()];
    for (int i = 0; i < array.length; i++) {
      switch (rand.nextInt(5)) {
        case 0 :
          array[i] = null;
          break;

        case 1 :
          array[i] = new Integer(i);
          break;

        case 2 :
          array[i] = new Double(i);
          break;

        case 3 :
          array[i] = new Object();
          break;

        case 4 :
          array[i] = String.valueOf(i);
          break;
      }
    }

    Assert.assertArrayEquals(array, (Object[])ArrayUtils.cloneArray(array));
  }

  /**
   * Test of method {@link ArrayUtils#cloneArray} for arrays of primitives.
   */
  @Test
  public void testCloneArrayForPrimitiveArray() {
    for (int i = 0; i < primitives.length; i++) {
      int size = getArrayLength();
      Object[] expected = (Object[])Array.newInstance(wrappers[i], size);
      Object array = Array.newInstance(primitives[i], size);
      for (int j = 0; j < size; j++) {
        expected[j] = Defaults.getDefaultValue(primitives[i]);
        Array.set(array, j, Defaults.getDefaultValue(primitives[i]));
      }

      Object clone = ArrayUtils.cloneArray(array);
      Assert.assertEquals(size, Array.getLength(clone));
      for (int j = 0; j < size; j++) {
        Assert.assertEquals("Wrong value at: " + j, expected[j], Array.get(clone, j));
      }
    }
  }

  /**
   * Test of method {@link ArrayUtils#hashCode(Object)} for null.
   */
  @Test
  public void testHashCodeForNull() {
    Assert.assertEquals(Arrays.hashCode((Object[])null), ArrayUtils.hashCode(null));
  }

  /**
   * Test of method {@link ArrayUtils#hashCode(Object)} for non-array object. Expected
   * {@link IllegalArgumentException}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testHashCodeForNoArray() {
    ArrayUtils.hashCode(new Object());
  }

  /**
   * Test of method {@link ArrayUtils#cloneArray} for multidimensional array object.
   */
  @Test
  public void testHashCodeForMultidimensionalArray() {
    Double[][] array = new Double[][] { {0d, 1d, 2d}, {3d}, {4d, 5d}};

    Assert.assertEquals(Arrays.deepHashCode(array), ArrayUtils.hashCode(array));
  }

  /**
   * Test of method {@link ArrayUtils#hashCode(Object)} for object arrays.
   */
  @Test
  public void testHashCodeForObjectArray() {
    Object[] array = new Object[getArrayLength() * 2];
    for (int i = 0; i < array.length; i++) {
      switch (rand.nextInt(5)) {
        case 0 :
          array[i] = null;
          break;

        case 1 :
          array[i] = new Integer(i);
          break;

        case 2 :
          array[i] = new Double(i);
          break;

        case 3 :
          array[i] = new Object();
          break;

        case 4 :
          array[i] = String.valueOf(i);
          break;
      }
    }

    Assert.assertEquals(Arrays.hashCode(array), ArrayUtils.hashCode(array));
  }

  /**
   * Test of method {@link ArrayUtils#hashCode(Object)} for array of primitives.
   */
  @Test
  public void testHashCodeForPrimitiveArray() {
    double[] array = new double[getArrayLength()];
    for (int i = 0; i < array.length; i++) {
      array[i] = rand.nextDouble();
    }

    Assert.assertEquals(Arrays.hashCode(array), ArrayUtils.hashCode(array));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for one or two null values.
   */
  @Test
  public void testEqualsForNullValues() {
    Assert.assertFalse(ArrayUtils.equals(null, ArrayUtils.EMPTY_BOOLEAN_ARRAY));
    Assert.assertFalse(ArrayUtils.equals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, null));
    Assert.assertFalse(ArrayUtils.equals(null, null));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for one or two non-array values.
   */
  @Test
  public void testEqualsForNonArrayValues() {
    Assert.assertFalse(ArrayUtils.equals(new Object(), ArrayUtils.EMPTY_BOOLEAN_ARRAY));
    Assert.assertFalse(ArrayUtils.equals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, new Object()));
    Assert.assertFalse(ArrayUtils.equals(new Object(), new Object()));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for same values (instances).
   */
  @Test
  public void testEqualsForSameArrayValues() {
    Assert.assertTrue(ArrayUtils.equals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.EMPTY_BOOLEAN_ARRAY));
    Assert.assertTrue(ArrayUtils.equals(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY, ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for different lengths.
   */
  @Test
  public void testEqualsForDifferentLengths() {
    double[] a1 = new double[1];
    Double[] a2 = new Double[2];
    Assert.assertFalse(ArrayUtils.equals(a1, a2));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for equals primitive arrays.
   */
  @Test
  public void testEqualsForEqualityOfPrimitiveArrays() {
    double[] a1 = new double[] {1.0, 2.0, 3.0, 4.0};
    double[] a2 = new double[] {1.0, 2.0, 3.0, 4.0};

    Assert.assertTrue(ArrayUtils.equals(a1, a2));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for not equals primitive arrays.
   */
  @Test
  public void testEqualsForNotEqualityOfPrimitiveArrays() {
    double[] a1 = new double[] {1.0, 2.0, 3.0, 4.0};
    double[] a2 = new double[] {4.0, 3.0, 2.0, 1.0};

    Assert.assertFalse(ArrayUtils.equals(a1, a2));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for equals object arrays.
   */
  @Test
  public void testEqualsForEqualityOfObjectArrays() {
    Double[] a1 = new Double[] {1.0, null, 3.0, 4.0};
    Double[] a2 = new Double[] {1.0, null, 3.0, 4.0};

    Assert.assertTrue(ArrayUtils.equals(a1, a2));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for not equals object arrays.
   */
  @Test
  public void testEqualsForNotEqualityOfObjectArrays() {
    Double[] a1 = new Double[] {1.0, null, 3.0, 4.0};
    Double[] a2 = new Double[] {4.0, 3.0, null, 1.0};

    Assert.assertFalse(ArrayUtils.equals(a1, a2));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for equals primitive and object
   * arrays.
   */
  @Test
  public void testEqualsForEqualityOfPrimitiveAnObjectArrays() {
    double[] a1 = new double[] {1.0, 2.0, 3.0, 4.0};
    Double[] a2 = new Double[] {1.0, 2.0, 3.0, 4.0};

    Assert.assertTrue(ArrayUtils.equals(a1, a2));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for not equals primitive and object
   * arrays.
   */
  @Test
  public void testEqualsForNotEqualityOfPrimitiveAnObjectArrays() {
    double[] a1 = new double[] {1.0, 2.0, 3.0, 4.0};
    Double[] a2 = new Double[] {1.0, null, 3.0, 4.0};

    Assert.assertFalse(ArrayUtils.equals(a1, a2));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for equals multidimensional primitive
   * and object arrays.
   */
  @Test
  public void testEqualsForEqualityOfMultidimensionalArrays() {
    double[][] a1 = new double[][] { {1.0, 2.0}, {3.0, 4.0, 5.0}, {6.0}};
    Double[][] a2 = new Double[][] { {1.0, 2.0}, {3.0, 4.0, 5.0}, {6.0}};

    Assert.assertTrue(ArrayUtils.equals(a1, a2));
  }

  /**
   * Test of method {@link ArrayUtils#equals(Object, Object)} for not equals multidimensional
   * primitive and object arrays.
   */
  @Test
  public void testEqualsForNotEqualityOfMultidimensionalArrays() {
    double[][] a1 = new double[][] { {1.0, 2.0}, {3.0, 4.0, 5.0}, {6.0}};
    Double[][] a2 = new Double[][] { {1.0, 2.0}, {3.0}, {6.0}};

    Assert.assertFalse(ArrayUtils.equals(a1, a2));
  }

}
