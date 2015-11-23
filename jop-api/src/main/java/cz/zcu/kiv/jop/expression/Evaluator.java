package cz.zcu.kiv.jop.expression;

import cz.zcu.kiv.jop.bean.Bean;

/**
 * Implementation of this interface may be singleton which will can be used for
 * evaluating of expressions in beans. Value of properties in beans may be
 * dependent on value another property. These properties may be annotated by
 * annotation {@link cz.zcu.kiv.jop.annotation.expression.Expression Expression}
 * which contains the expression to evaluate. The result of expression will be
 * set into annotated property.
 *
 * @author Mr.FrAnTA
 * @since 1.0
 */
public interface Evaluator {

  /**
   * Evaluates all expressions in given bean.
   *
   * @param bean the bean which expressions will be evaluated.
   * @throws EvaluatorException If some error occurs during evaluation of
   *           expressions in given bean.
   */
  public void evaluateExpressions(Bean bean) throws EvaluatorException;

  /**
   * Evaluates expression given as string. In expression may be operations above
   * the variables from given bean. The result of evaluation will be returned.
   *
   * @param expression the string representing expression to evaluate.
   * @param bean the bean which variables may be used in expression.
   * @return A result of evaluation of given expression above the variables
   *         (properties) of given bean.
   * @throws EvaluatorException If some error occurs during evaluation of
   *           expression.
   */
  public Object evaluateExpression(String expression, Bean bean) throws EvaluatorException;

}
