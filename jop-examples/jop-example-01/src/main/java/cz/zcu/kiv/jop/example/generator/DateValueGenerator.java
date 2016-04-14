package cz.zcu.kiv.jop.example.generator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.example.annotation.DateGeneratorParameters;
import cz.zcu.kiv.jop.generator.AbstractValueGenerator;
import cz.zcu.kiv.jop.generator.ValueGeneratorException;

/**
 * The value generator of date values which generates random date value according to given
 * parameters.
 *
 * @author Mr.FrAnTA
 */
@Singleton
public class DateValueGenerator extends AbstractValueGenerator<Date, DateGeneratorParameters> {

  /**
   * Helper constant of date formatter which is used for parsing data values from parameters of
   * value generator.
   */
  protected static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * {@inheritDoc}
   */
  public Class<Date> getValueType() {
    return Date.class;
  }

  /**
   * Returns random date value between date given in parameters and today date.
   */
  public Date getValue(DateGeneratorParameters params) throws ValueGeneratorException {
    checkParamsNotNull(params);
    try {
      Date now = new Date();
      Date since = formatter.parse(params.since());
      if (now.before(since)) {
        throw new ValueGeneratorException("Since parameter cannot be in future");
      }

      long range = now.getTime() - since.getTime();
      long randOffset = (long)(getRandomGenerator(params).nextDouble() * range);

      return new Date(since.getTime() + randOffset);
    }
    catch (ParseException exc) {
      throw new ValueGeneratorException("Cannot parse since date");
    }
  }

}
