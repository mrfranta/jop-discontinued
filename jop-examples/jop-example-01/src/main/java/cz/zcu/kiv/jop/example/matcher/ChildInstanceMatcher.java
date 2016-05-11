package cz.zcu.kiv.jop.example.matcher;

import javax.inject.Singleton;

import cz.zcu.kiv.jop.example.annotation.ChildInstanceMatcherParameters;
import cz.zcu.kiv.jop.example.domain.Child;
import cz.zcu.kiv.jop.matcher.InstanceMatcher;
import cz.zcu.kiv.jop.matcher.InstanceMatcherException;

/**
 * Implementation of {@link InstanceMatcher} which filters children by their genders.
 *
 * @author Mr.FrAnTA
 */
@Singleton
public class ChildInstanceMatcher implements InstanceMatcher<ChildInstanceMatcherParameters> {

  /**
   * Returns information whether the given clazz is instance of {@link Child}.
   */
  public boolean supports(Class<?> clazz) {
    return clazz == Child.class; // do not accept parents
  }

  /**
   * Returns information whether the given object is child with gender from given parameters.
   */
  public boolean matches(Object obj, ChildInstanceMatcherParameters params) throws InstanceMatcherException {
    Child child = (Child)obj;

    if (child.getGender() != params.gender()) {
      return false;
    }

    return Math.random() > 0.75;
  }
}
