package cz.zcu.kiv.jop;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.util.List;

public class App {

  public static void main(String[] args) throws NoSuchMethodException, SecurityException {
    Method method = App.class.getMethod("method", List.class);
    ParameterizedType listType = (ParameterizedType)method.getGenericParameterTypes()[0];
    ParameterizedType classType = (ParameterizedType)listType.getActualTypeArguments()[0];
    WildcardType genericType = (WildcardType)classType.getActualTypeArguments()[0];
    Class<?> genericClass = (Class<?>)genericType.getUpperBounds()[0];

    boolean isException = Exception.class.isAssignableFrom(genericClass);
    // vvv Prints out "Is Class<? extends Exception>: true"
    System.out.println("Is Class<? extends Exception>: " + isException);

    boolean isRuntimeException = RuntimeException.class.isAssignableFrom(genericClass);
    // vvv Prints out "Is Class<? extends RuntimeException>: false"
    System.out.println("Is Class<? extends RuntimeException>: " + isRuntimeException);
  }

  public void method(List<Class<? extends Exception>> exceptionClasses) {
    // Do something with "exceptionClasses," I would imagine...
  }

}
