package cz.zcu.kiv.jop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Helper annotation which represents empty parameters. This annotation can be
 * used in implementations of class provider, value generator, etc. Which don't
 * support the custom parameters and which are used with annotation
 * <code>&#64;CustomXXX</code>.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
@Documented
@Target({}) // this annotation is only helper
@Retention(RetentionPolicy.RUNTIME)
public @interface Empty {

}
