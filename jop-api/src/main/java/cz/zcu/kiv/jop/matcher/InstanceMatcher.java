package cz.zcu.kiv.jop.matcher;

import java.lang.annotation.Annotation;

/**
 * Extension of {@link TypedInstanceMatcher} interface which don't need type parameter for type of
 * accepted type of checked objects because allows to check all objects.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <P> Annotation type of instance matcher parameters.
 */
public interface InstanceMatcher<P extends Annotation> extends TypedInstanceMatcher<Object, P> {

}
