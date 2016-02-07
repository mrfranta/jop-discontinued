package cz.zcu.kiv.jop.session;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.util.Preconditions;

/**
 * Implementation of session which stores random generators for annotations (parameters of
 * generators, providers, etc). This session stores random generator for annotation instance. It
 * stores/returns the same random generator for annotation of some type independently on its
 * attributes.
 * <p>
 * For example for annotations <code>&#64;Foo("bar")</code> and <code>&#64;Foo("foo")</code> the
 * session stores same random generator.
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@Singleton
public class RandomGeneratorSessionImpl implements RandomGeneratorSession {

  /**
   * Map (session) of stored random generators.
   */
  protected final Map<Class<? extends Annotation>, Random> session = new HashMap<Class<? extends Annotation>, Random>();

  /**
   * {@inheritDoc}
   */
  @Override
  public Random setRandomGenerator(Annotation annotation, Random random) {
    Preconditions.checkArgumentNotNull(annotation, "Annotation cannot be null");
    return storeRandomGenerator(annotation.annotationType(), random);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Random getRandomGenerator(Annotation annotation) {
    Preconditions.checkArgumentNotNull(annotation, "Annotation cannot be null");
    Class<? extends Annotation> annotationType = annotation.annotationType();
    Random rand = session.get(annotationType);
    if (rand == null) {
      rand = new Random();
      storeRandomGenerator(annotationType, rand);
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
  protected Random storeRandomGenerator(Class<? extends Annotation> annotation, Random random) {
    return session.put(annotation, random);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    session.clear();
  }

}
