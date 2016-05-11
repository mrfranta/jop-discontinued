package cz.zcu.kiv.jop.example.domain;

import cz.zcu.kiv.jop.annotation.Constructor;
import cz.zcu.kiv.jop.annotation.matcher.CustomInstanceMatcher;
import cz.zcu.kiv.jop.annotation.strategy.SearchInstance;
import cz.zcu.kiv.jop.example.annotation.DictionaryGenerator;
import cz.zcu.kiv.jop.example.annotation.ChildInstanceMatcherParameters;
import cz.zcu.kiv.jop.example.matcher.ChildInstanceMatcher;

/**
 * The parent.
 *
 * @author Mr.FrAnTA
 */
public class Parent extends Person {

  /** The parent's son. */
  @SearchInstance
  @ChildInstanceMatcherParameters(gender = Gender.MALE)
  @CustomInstanceMatcher(ChildInstanceMatcher.class)
  private Person son;

  /** The parent's daughter. */
  @SearchInstance
  @ChildInstanceMatcherParameters(gender = Gender.FEMALE)
  @CustomInstanceMatcher(ChildInstanceMatcher.class)
  private Person daughter;

  /**
   * Constructs the parent.
   *
   * @param name the name of parent.
   */
  public Parent(String name) {
    super(name);
  }

  /**
   * Constructs the parent.
   *
   * @param firstName the first name of parent.
   * @param lastName the second name of parent.
   */
  @Constructor
  public Parent(@DictionaryGenerator(path = "classpath:/data/firstNames.txt") String firstName,
      @DictionaryGenerator(path = "classpath:/data/lastNames.txt") String lastName) {
    super(firstName + " " + lastName);
  }

  /**
   * Returns the parent's son.
   *
   * @return the parent's son.
   */
  public Person getSon() {
    return son;
  }

  /**
   * Sets the parent's son.
   *
   * @param son the person to set as paren't son.
   */
  public void setSon(Person son) {
    this.son = son;
  }

  /**
   * Returns the parent's daughter.
   *
   * @return the parent's daughter.
   */
  public Person getDaughter() {
    return daughter;
  }

  /**
   * Sets the parent's daughter.
   *
   * @param daughter the person to set as paren't daughter.
   */
  public void setDaughter(Person daughter) {
    this.daughter = daughter;
  }

  /**
   * Returns string representation of parent object.
   *
   * @return String representation of parent.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [name = " + getName() + ", son=" + (son == null ? null : son.getName()) + ", daughter="
        + (daughter == null ? null : daughter.getName()) + "]";
  }

}
