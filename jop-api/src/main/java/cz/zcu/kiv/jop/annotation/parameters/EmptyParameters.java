package cz.zcu.kiv.jop.annotation.parameters;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Helper annotation which represents empty parameters. This annotation may be used in
 * implementations of class provider, value generator, etc. which don't require/support parameters.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Documented
@Target({}) // this annotation is only helper
@Retention(RetentionPolicy.RUNTIME)
public @interface EmptyParameters {

}
