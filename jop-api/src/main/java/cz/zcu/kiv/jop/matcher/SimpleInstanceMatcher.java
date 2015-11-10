package cz.zcu.kiv.jop.matcher;

import cz.zcu.kiv.jop.annotation.Empty;
import cz.zcu.kiv.jop.annotation.matcher.CustomInstanceMatcher;

/**
 * Abstract implementation of {@link InstanceMatcher} interface which don't need
 * parameters for matching of objects (instances). Also implementations of this
 * class can be used in {@link CustomInstanceMatcher} annotation.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public abstract class SimpleInstanceMatcher extends SimpleTypedInstanceMatcher<Object> implements InstanceMatcher<Empty> {

}
