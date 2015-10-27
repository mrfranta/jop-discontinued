package cz.zcu.kiv.jop;

/**
 * Hello world!
 *
 */
public class App {

  private class Test {
    public void test() {}
  }

  public static void main(String[] args) throws Exception {
    System.err.println("EMPTY ------------------------------------------------------");
    new JopException().printStackTrace();
    System.err.println("DEFAULT ------------------------------------------------------");
    new JopException() {
      @Override
      protected String getDefaultMessage() {
        return "Default message";
      };
    }.printStackTrace();
    System.err.println("DETAIL ------------------------------------------------------");
    new JopException("Detail message").printStackTrace();
    System.err.println("DETAIL ------------------------------------------------------");
    new JopException("Detail message") {
      @Override
      protected String getDefaultMessage() {
        return "Default message";
      };
    }.printStackTrace();
    System.err.println("CAUSE ------------------------------------------------------");
    new JopException(new RuntimeException("Cause detail message")).printStackTrace();
    System.err.println("DEFAULT ------------------------------------------------------");
    new JopException(new RuntimeException("Cause detail message")) {
      @Override
      protected String getDefaultMessage() {
        return "Default message";
      };
    }.printStackTrace();
    System.err.println("DETAIL ------------------------------------------------------");
    new JopException("Detail message", new RuntimeException("Cause detail message")).printStackTrace();
    System.err.println("CAUSE ------------------------------------------------------");
    new JopException(null, new RuntimeException("Cause detail message")).printStackTrace();
    System.err.println("DEFAULT ------------------------------------------------------");
    new JopException(null, new RuntimeException("Cause detail message")) {
      @Override
      protected String getDefaultMessage() {
        return "Default message";
      };
    }.printStackTrace();
    System.err.println("DETAIL ------------------------------------------------------");
    new JopException("Detail message", null).printStackTrace();
    System.err.println("EMPTY ------------------------------------------------------");
    new JopException(null, null).printStackTrace();
  }
}
