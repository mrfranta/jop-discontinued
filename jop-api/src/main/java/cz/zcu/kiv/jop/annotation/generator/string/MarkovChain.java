package cz.zcu.kiv.jop.annotation.generator.string;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.GeneratorAnnotation;

/**
 * This annotation marks property for which will be generated random string value based on
 * <em>Markov chain</em> with given corpus. The corpus serves to determine probability of one letter
 * following another letter or sequence of letters.
 *
 * <p>
 * The Markov chain creates words from corpus according to the probability of one letter following
 * another letter or sequence of letters that precedes. The simplest example can be given for the
 * generator with depth 1. In such case, the process needs a table that gives the probability of
 * each combination of two consequent letters (for example "h" follows "t" with probability 0.0253,
 * "x" follows "t" with probability 0.0001, ...). The generators works in similar way as FSM, where
 * each state of <em><a
 * href="https://en.wikipedia.org/wiki/Finite-state_machine">FSM</a></em> is given by the last
 * letter in generated word and the probability of transition to the next state (in another words
 * probability of next generated letter) is determined from the table of letter combinations. The
 * depth means, that only one preceding letter is used to determine the next one. It is possible to
 * use higher depths, where the state is given by two or three preceding letters, but this rapidly
 * leads to higher size of the probability table. Maximal allowed depth of Markov chains is 4.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Markov_chain">Markov chain</a>
 *
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
@GeneratorAnnotation
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MarkovChain {

  /**
   * Required parameter for used corpus. The value can be absolute or relative path to file with
   * corpus (file that contains probability table). In case that the path is relative to
   * <i>classpath</i>, the prefix <code>classpath:</code> can be used.
   */
  public String corpus();

  /**
   * Optional parameter for depth which means, that only one preceding letter is used to determine
   * the next one. It is possible to use higher depths, where the state is given by two or three
   * preceding letters, but this rapidly leads to higher size of the probability table. Minimal (and
   * default) depth is 1 and maximal supported depth is 4.
   */
  public int depth() default 1;

  /**
   * Optional parameter for maximal number of characters in generated string. The value has to be
   * greater or equal to 0.
   */
  public int maxLen() default Integer.MAX_VALUE;

}
