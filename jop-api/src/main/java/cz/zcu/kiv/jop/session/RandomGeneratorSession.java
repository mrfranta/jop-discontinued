package cz.zcu.kiv.jop.session;

import java.lang.annotation.Annotation;
import java.util.Random;

/**
 * Interface for session which stores random generators for annotations
 * (parameters of generators, providers, etc). Session may store random
 * generator for each annotation type or annotation instance, it depends on
 * implementation.
 * <p>
 * If session stores random generators for annotation type, it will store/return
 * the same random generator for annotation of some type independently on its
 * attributes. For example for annotations <code>&#64;Foo("bar")</code> and
 * <code>&#64;Foo("foo")</code> the session stores same random generator.
 * <p>
 * If session stores random generators for annotation instance, it will
 * store/return the same random generator only for annotation of same type which
 * has same attributes. For example for annotations <code>&#64;Foo("bar")</code>
 * and <code>&#64;Foo("foo")</code> the session stores different random
 * generator.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface RandomGeneratorSession {

  /**
   * Sets (stores) given random generator to given annotation. If the annotation
   * already had set (stored) random generator, it will be replaced. This method
   * can be used for setting of custom random generators to specific
   * annotations. Also can be used for removal of random generator for
   * annotation when the given random generator is <code>null</code>.
   * 
   * @param annotation the annotation for which will be set the given random
   *          generator.
   * @param random the random generator to set (may be <code>null</code>).
   */
  public void setRandomGenerator(Annotation annotation, Random random);

  /**
   * Returns (stored) random generator for given annotation. If the generator
   * wasn't set (stored) or is <code>null</code>, it will create and store the
   * new random generator (with default seed).
   * 
   * @param annotation the annotation for which will be returned random
   *          generator.
   * @return Stored or created random generator.
   */
  public Random getRandomGenerator(Annotation annotation);

  /**
   * Clears the session (removes all stored random generators).
   */
  public void clear();

}
