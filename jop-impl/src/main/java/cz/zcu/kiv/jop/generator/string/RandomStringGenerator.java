package cz.zcu.kiv.jop.generator.string;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.generator.string.RandomString;
import cz.zcu.kiv.jop.generator.CategoricalGenerator;

/**
 * Implementation of categorical string generator for annotation {@link RandomString}.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class RandomStringGenerator extends CategoricalGenerator<String, RandomString> {

}
