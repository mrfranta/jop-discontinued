package cz.zcu.kiv.jop.annotation;

/**
 * This enumeration is used by {@link Access} annotation for determination of an
 * access type of specific attribute or all attributes of generated class.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @see Access
 */
public enum AccessType {

  /** <b>Field-based access</b> - direct access to field using reflection. */
  FIELD,

  /**
   * <b>Property-based access</b> - access to fields using getters and setters
   * which may not have public access.
   */
  PROPERTY

  /* Next field, */;

}
