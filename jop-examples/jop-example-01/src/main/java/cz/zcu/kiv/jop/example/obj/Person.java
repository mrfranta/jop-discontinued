package cz.zcu.kiv.jop.example.obj;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.zcu.kiv.jop.annotation.generator.CustomValueGenerator;
import cz.zcu.kiv.jop.annotation.generator.number.DiscreteUniformGenerator;
import cz.zcu.kiv.jop.annotation.generator.number.UniformGenerator;
import cz.zcu.kiv.jop.annotation.populator.NumberValue;
import cz.zcu.kiv.jop.annotation.strategy.Ignore;
import cz.zcu.kiv.jop.example.annotation.DateGeneratorParameters;
import cz.zcu.kiv.jop.example.generator.ColorValueGenerator;
import cz.zcu.kiv.jop.example.generator.DateValueGenerator;
import cz.zcu.kiv.jop.example.generator.GenderValueGenerator;

/**
 * The person.
 *
 * @author Mr.FrAnTA
 */
public class Person {

  /** The helper constant for formatter of person's birth date. */
  @Ignore
  protected static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  /** The name of person. */
  @Ignore
  private String name;

  /** The age of person. */
  @DiscreteUniformGenerator(min = 0, max = 100)
  private int age;

  /** The person's height. */
  @NumberValue(target = int.class)
  @UniformGenerator(min = 50, max = 180)
  private double height;

  /** The person's weight. */
  @NumberValue(target = int.class)
  @UniformGenerator(min = 2.5, max = 120)
  private double weight;

  /** The person's gender. */
  @CustomValueGenerator(GenderValueGenerator.class)
  private Gender gender;

  /** The person's birth date. */
  @CustomValueGenerator(DateValueGenerator.class)
  @DateGeneratorParameters(since = "1950-01-01")
  private Date birthDate;

  /** The color of person's hair. */
  @CustomValueGenerator(ColorValueGenerator.class)
  private Color hairColor;

  /**
   * Constructs the person.
   *
   * @param name the name of person.
   */
  Person(String name) {
    this.name = name;
  }

  /**
   * Returns the name of person.
   *
   * @return the name of person.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of person.
   *
   * @param name the name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the age of person.
   *
   * @return the age of person.
   */
  public int getAge() {
    return age;
  }

  /**
   * Sets the age of person.
   *
   * @param age the age to set.
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Returns the person's height.
   *
   * @return the person's height.
   */
  public double getHeight() {
    return height;
  }

  /**
   * Sets the person's height.
   *
   * @param height the height to set.
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * Returns the person's weight.
   *
   * @return the person's weight.
   */
  public double getWeight() {
    return weight;
  }

  /**
   * Sets the person's weight.
   *
   * @param weight the weight to set.
   */
  public void setWeight(double weight) {
    this.weight = weight;
  }

  /**
   * Returns the person's gender.
   *
   * @return the person's gender.
   */
  public Gender getGender() {
    return gender;
  }

  /**
   * Sets the color of person's hair.
   *
   * @param gender the gender to set.
   */
  public void setGender(Gender gender) {
    this.gender = gender;
  }

  /**
   * Returns the person's birth date.
   *
   * @return the person's birth date.
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * Sets the person's birth date.
   *
   * @param birthDate the birth date to set.
   */
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * Returns the color of person's hair.
   *
   * @return the color of person's hair.
   */
  public Color getHairColor() {
    return hairColor;
  }

  /**
   * Returns the color of person's hair.
   *
   * @param hairColor the color of hair to set.
   */
  public void setHairColor(Color hairColor) {
    this.hairColor = hairColor;
  }

  /**
   * Returns string representation of person object.
   *
   * @return String representation of person.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [name=" + name + ", age=" + age + ", height=" + height + "cm, weight=" + weight + "kg, gender=" + gender + ", birthDate="
        + (birthDate == null ? birthDate : dateFormatter.format(birthDate)) + ", hairColor=" + hairColor + "]";
  }

}
