package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * Class provider annotation which will select random class name which will be searched in
 * <em>classpath</em> and which may be used for new instance creation. For selection of random class
 * will be used <em>Categorical
 * distribution</em> - it will be used one of {@link #value() values} based on specified
 * {@link #probabilities() probabilities}. This annotation is very similar to {@link RandomClass}
 * annotation.
 * <p>
 * This annotation can be also used as generator annotation which marks property for which will be
 * generated of class (<code>Class&lt;?&gt;</code>) value.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @see <a href="https://en.wikipedia.org/wiki/Categorical_distribution"> Categorical
 *      distribution</a>
 */
@ClassProviderAnnotation
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomClassForName {

  /**
   * Required parameter for fully qualified names of classes in <em>classpath</em> where each value
   * has a probability of occurrence in parameter {@link #probabilities() probabilities} on same
   * index like value.
   */
  public String[] value();

  /**
   * Optional parameter for probabilities of outcome values. If the probabilities won't be set the
   * uniform distribution will be used (all values has same probability).
   * <p>
   * The number of probabilities should be lesser that or equals to number of values. In case of
   * lesser number of probabilities, the value 0.0 will be used for rest of values.
   * <p>
   * Values of probabilities should be greater or equal to 0 and lesser or equal to 1.0. To ensure
   * that the summary of probabilities will be always 1.0, the values are automatically normalized.
   */
  public double[] probabilities() default {};

  /**
   * Optional parameter which determines whether the class must be initialized. Default value is
   * <code>true</code>.
   */
  public boolean initialize() default true;

  /**
   * Optional parameter which determines type or name of class loader which will be used for loading
   * of class. There is possibility to use one of three basic types of class loaders which names are
   * defined in interface {@link ClassLoaderConst} or it can be set own name of class loader which
   * has to be stored in {@link cz.zcu.kiv.jop.session.ClassLoaderSession ClassLoaderSession}.
   */
  public String classLoader() default ClassLoaderConst.CALLER;

}
