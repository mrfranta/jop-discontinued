package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * Class provider annotation which will select random class which may be used
 * for new instance creation. For selection of random class will be used
 * <em>Categorical distribution</em> - it will be used one of value from
 * {@link #values() values} based on their {@link #probabilities()
 * probabilities}.
 * <p>
 * This annotation can be also used as generator annotation which marks property
 * for which will be generated of class (<code>Class&lt;?&gt;</code>) value.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@ClassProviderAnnotation
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomClass {

  /**
   * Required parameter for outcome values of generator where each value has a
   * probability of occurrence in parameter {@link #probabilities()
   * probabilities} on same index like value.
   */
  public Class<?>[] values();

  /**
   * Required parameter for probabilities of outcome values. The number of
   * probabilities has to be same like number of values. Also value of each
   * probability should be greater or equal to 0 and lesser or equal to 1.To
   * ensure that the sum of probabilities will be always 1, the values are
   * automatically normalized.
   */
  public double[] probabilities();

}
