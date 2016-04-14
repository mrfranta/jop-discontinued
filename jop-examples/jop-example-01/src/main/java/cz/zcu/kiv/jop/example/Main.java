package cz.zcu.kiv.jop.example;

import java.util.List;

import cz.zcu.kiv.jop.ObjectPopulator;
import cz.zcu.kiv.jop.ObjectPopulatorProvider;
import cz.zcu.kiv.jop.example.obj.Child;
import cz.zcu.kiv.jop.example.obj.Parent;

/**
 * The runnable main class of this example.
 *
 * @author Mr.FrAnTA
 */
public class Main {

  /** Constant for default number of generated children. */
  private static final int DEFAULT_CHILDREN_COUNT = 100;

  /** Constant for default number of generated parents. */
  private static final int DEFAULT_PARENT_COUNT = 20;

  /**
   * Main method which prepares object populator and then generates X children and Y parents and
   * prints them into console.
   *
   * @param args the arguments of program which accepts only two optional arguments. First argument
   *          serves as number of genrated children and second one is for number of generated
   *          parents.
   */
  public static void main(String[] args) {
    try {
      int childrenCount = DEFAULT_CHILDREN_COUNT;
      int parentsCount = DEFAULT_PARENT_COUNT;

      if (args.length >= 2) {
        parentsCount = Integer.parseInt(args[1]);
      }

      if (args.length >= 1) {
        childrenCount = Integer.parseInt(args[0]);
      }

      // prepares populator
      ObjectPopulator populator = ObjectPopulatorProvider.getObjectPopulator();

      System.out.printf("Generating %d children\n", childrenCount);
      long start = System.currentTimeMillis();
      List<Child> children = populator.populate(Child.class, childrenCount);
      long end = System.currentTimeMillis();
      System.out.printf("Generating of children finished in %dms\n", (end - start));

      System.out.printf("Generating %d parents\n", parentsCount);
      start = System.currentTimeMillis();
      List<Parent> parents = populator.populate(Parent.class, parentsCount);
      end = System.currentTimeMillis();
      System.out.printf("Generating of parents finished in %dms\n", (end - start));

      System.out.println("Generated children:");
      for (Child child : children) {
        System.out.println(child);
      }

      System.out.println("Generated parents:");
      for (Parent parent : parents) {
        System.out.println(parent);
      }
    }
    catch (Exception exc) {
      System.err.println("Some error occurs during generating of objects");
      exc.printStackTrace();
    }
  }

}
