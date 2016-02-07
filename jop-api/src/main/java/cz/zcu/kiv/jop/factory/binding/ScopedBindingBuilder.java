package cz.zcu.kiv.jop.factory.binding;

/**
 * An interface for sub-type of {@link BindingBuilder} which serves for setting of binding scope.
 * 
 * @author Mr.FrAnTA
 * @since 1.0.0
 */
public interface ScopedBindingBuilder {

  /**
   * Marks bound object as singleton.
   */
  public void asSingleton();

  /**
   * Marks bound object as eager singleton. It's possible to create (eager) singleton instance of
   * bound object in this method.
   *
   * @throws BindingException It may try to create instance of bound object and the exception can be
   *           thrown if some error occurs during obtaining of singleton instance of bound object.
   */
  public void asEagerSingleton();

}
