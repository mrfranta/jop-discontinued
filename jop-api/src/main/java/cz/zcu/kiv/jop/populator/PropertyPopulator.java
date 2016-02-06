package cz.zcu.kiv.jop.populator;

import java.lang.annotation.Annotation;

/**
 * Extension of {@link TypedPropertyPopulator} interface which don't need type parameter for type of
 * accepted type of populated properties because it allows to populate properties with any declared
 * type.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 *
 * @param <P> Annotation type of parameters for populator of property.
 */
public interface PropertyPopulator<P extends Annotation> extends TypedPropertyPopulator<Object, P> {

}
