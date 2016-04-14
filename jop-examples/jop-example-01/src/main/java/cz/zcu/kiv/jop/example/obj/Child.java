package cz.zcu.kiv.jop.example.obj;

import cz.zcu.kiv.jop.annotation.Constructor;
import cz.zcu.kiv.jop.example.annotation.DictionaryGenerator;

/**
 * The child.
 *
 * @author Mr.FrAnTA
 */
public class Child extends Person {

  /**
   * Constructs the child.
   *
   * @param name the name of child.
   */
  public Child(String name) {
    super(name);
  }

  /**
   * Constructs the child.
   *
   * @param firstName the first name of child.
   * @param lastName the second name of child.
   */
  @Constructor
  public Child(@DictionaryGenerator(path = "classpath:/data/firstNames.txt") String firstName,
      @DictionaryGenerator(path = "classpath:/data/lastNames.txt") String lastName) {
    super(firstName + " " + lastName);
  }

}
