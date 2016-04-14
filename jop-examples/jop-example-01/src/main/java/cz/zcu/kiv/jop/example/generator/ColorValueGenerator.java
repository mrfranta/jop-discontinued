package cz.zcu.kiv.jop.example.generator;

import java.awt.Color;
import java.util.Random;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.annotation.parameters.EmptyParameters;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * The implementation of value generator of random colors.
 *
 * @author Mr.FrAnTA
 */
@Singleton
public class ColorValueGenerator extends AbstractValueGenerator<Color, EmptyParameters> {

  /**
   * {@inheritDoc}
   */
  public Class<Color> getValueType() {
    return Color.class;
  }

  /**
   * Generates random color.
   */
  public Color getValue(EmptyParameters params) throws ValueGeneratorException {
    Random rand = getRandomGenerator(params);

    int r = rand.nextInt(256);
    int g = rand.nextInt(256);
    int b = rand.nextInt(256);

    return new Color(r, g, b);
  }

}
