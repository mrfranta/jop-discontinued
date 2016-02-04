package cz.zcu.kiv.jop.annotation.class_provider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * Class provider annotation which determines the specific (target) class defined by fully qualified
 * name which may be used for new instance creation. This annotation is very similar to
 * {@link TargetClass} annotation.
 * <p>
 * This annotation can be also used as generator annotation which marks property for which will be
 * generated of class (<code>Class&lt;?&gt;</code>) value.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@ClassProviderAnnotation
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetClassForName {

  /**
   * Required parameter for fully qualified name of class in <em>classpath</em> which will be
   * returned by class provider or value generator.
   */
  public String value();

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
