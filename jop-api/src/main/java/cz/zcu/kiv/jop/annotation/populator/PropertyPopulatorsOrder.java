package cz.zcu.kiv.jop.annotation.populator;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Helper annotation which may be used for specification of exact order (hiearchy) of populators of
 * property. In case that this annotation is not used, the order is specified by order of
 * annotations for annotated property - it's may be problem in case that JDK won't return the
 * annotations in correct order. Because of that is suggested to use this helper annotation to
 * determine exact order.
 * <p>
 * Example: We want to use populators to populate array by string values of random numbers.
 *
 * <p>
 * Solution using implicit definition:
 *
 * <pre>
 * &#064;ArrayValue(max = 1024, target = String.class)
 * &#064;StringValue
 * &#064;UniformGenerator(min = 0, max = Double.MAX_VALUE)
 * String[] identifiers;
 * </pre>
 *
 * <p>
 * Solution using explicit definition:
 *
 * <pre>
 * &#064;UniformGenerator(min = 0, max = Double.MAX_VALUE)
 * &#064;StringValue
 * &#064;ArrayValue(max = 1024, target = String.class)
 * &#64;PopulatorOrder({ArrayValue.class, StringValue.class});
 * String[] identifiers;
 * </pre>
 * <p>
 * Also this annotations specifies which property populators will be used for annotated property. If
 * some property annotated by this annotation is also annotated by 3 annotations for property
 * populator and the order contains only 1 annotation, the remaining two annotations will be ignored
 * (won't be used). On another hand this annotation should not ordering more annotations than
 * property owns.
 * <p>
 * This annotation also shouldn't contains another than property populator annotations.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyPopulatorsOrder {

  /**
   * Parameter for array of populator annotations in order how they will be executed. This parameter
   * specifies the hiearchy (order) of populators. The first populator in array will populates the
   * field and the last populator will adjust values given from generator or provider.
   */
  public Class<? extends Annotation>[] value();

}
