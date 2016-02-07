package cz.zcu.kiv.jop.session;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.util.Preconditions;

/**
 * Implementation of session which stores random generators for annotations (parameters of
 * generators, providers, etc). This session stores random generator for annotation types. It
 * stores/returns the same random generator only for annotation of same type which has same
 * attributes.
 * <p>
 * For example for annotations <code>&#64;Foo("bar")</code> and <code>&#64;Foo("foo")</code> the
 * session stores different random generator.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class ExtendedRandomGeneratorSessionImpl implements RandomGeneratorSession {

  /**
   * Map (session) of stored random generators.
   */
  protected final Map<Annotation, Random> session = new HashMap<Annotation, Random>();

  /**
   * {@inheritDoc}
   */
  public Random setRandomGenerator(Annotation annotation, Random random) {
    Preconditions.checkArgumentNotNull(annotation, "Annotation cannot be null");
    return storeRandomGenerator(annotation, random);
  }

  /**
   * {@inheritDoc}
   */
  public Random getRandomGenerator(Annotation annotation) {
    Preconditions.checkArgumentNotNull(annotation, "Annotation cannot be null");
    Random rand = session.get(annotation);
    if (rand == null) {
      rand = new Random();
      storeRandomGenerator(annotation, rand);
    }

    return rand;
  }

  /**
   * Stores given random generator into session.
   *
   * @param annotation the annotation for which will be stored the given random generator.
   * @param random the random generator to store (may be <code>null</code>).
   * @return Previous stored random generator.
   */
  protected Random storeRandomGenerator(Annotation annotation, Random random) {
    return session.put(annotation, random);
  }

  /**
   * {@inheritDoc}
   */
  public void clear() {
    session.clear();
  }

}
