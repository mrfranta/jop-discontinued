package cz.zcu.kiv.jop.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.zcu.kiv.jop.annotation.generator.ValueGeneratorAnnotation;

/**
 * Annotation for value generator which generates random values from loaded dictionary file with
 * expressions.
 *
 * @author Mr.FrAnTA
 */
@ValueGeneratorAnnotation
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictionaryGenerator {

  /** Path prefix for loading files from Class path. */
  String CLASSPATH_PREFIX = "classpath:";

  /** Path to dictionary file. */
  public String path();

  /** Encoding of dictionary file. */
  public String encoding() default "UTF-8";

}
