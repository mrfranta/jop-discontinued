package cz.zcu.kiv.jop.annotation.populator;

/**
 * This enumeration is used by {@link PopulatorAnnotation} annotation for
 * determination of an type of populator type (whatever can be combined with
 * another populator annotation).
 *
 * @author Mr.FrAnTA
 * @since 1.0
 *
 * @see PopulatorAnnotation
 */
public enum PopulatorType {

  /**
   * <b>Master type of populator annotation</b> - it can be combined with slave
   * populator annotation (it can be chained in hierarchy master -> slave).
   */
  MASTER,

  /**
   * <b>Slave type of populator annotation</b> - it can be combined with master
   * populator annotation (it can be chained in hierarchy master -> slave).
   */
  SLAVE,

  /* Next field, */;
}
