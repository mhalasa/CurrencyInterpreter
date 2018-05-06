package structures.ex;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class ConditionEx implements ConditionOperand {
    private boolean isNegated;
    public TokenType operation = TokenType.UNDEFINED;
    public List<ConditionOperand> operands = new LinkedList<>();

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        final LiteralEx result = new LiteralEx();
        result.setIsBool(true);
        switch (operation) {
            case UNDEFINED:
                if (!isNegated) {
                    return operands.get(0).execute(scope, functions);
                } else {
                    result.setValue(operands.get(0).execute(scope, functions).isTruthy() ? 0 : 1);
                }
                break;
            case OR:
                for (final ConditionOperand operand : operands) {
                    if (operand.execute(scope, functions).isTruthy()) {
                        result.setValue(1);
                        return result;
                    }
                }
                result.setValue(0);
                return result;
            case AND:
                for (final ConditionOperand operand : operands) {
                    if (!operand.execute(scope, functions).isTruthy()) {
                        result.setValue(0);
                        return result;
                    }
                }
                result.setValue(0);
                return result;
            case EQUALS:
                LiteralEx left = operands.get(0).execute(scope, functions);
                LiteralEx right = operands.get(1).execute(scope, functions);
                if (left.isBool() && right.isBool()) {
                    result.setValue(left.isTruthy() == right.isTruthy() ? 1 : 0);
                } else if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() == right.getValue() ? 1 : 0);
                }
                break;
            case NOT_EQUALS:
                left = operands.get(0).execute(scope, functions);
                right = operands.get(1).execute(scope, functions);
                if (left.isBool() && right.isBool()) {
                    result.setValue(left.isTruthy() != right.isTruthy() ? 1 : 0);
                } else if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() != right.getValue() ? 1 : 0);
                }
                break;
            case LOWER:
                left = operands.get(0).execute(scope, functions);
                right = operands.get(1).execute(scope, functions);
                if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() < right.getValue() ? 1 : 0);
                }
                break;
            case LOWER_EQUALS:
                left = operands.get(0).execute(scope, functions);
                right = operands.get(1).execute(scope, functions);
                if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() <= right.getValue() ? 1 : 0);
                }
                break;
            case GREATER:
                left = operands.get(0).execute(scope, functions);
                right = operands.get(1).execute(scope, functions);
                if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() > right.getValue() ? 1 : 0);
                }
                break;
            case GREATER_EQUALS:
                left = operands.get(0).execute(scope, functions);
                right = operands.get(1).execute(scope, functions);
                if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() >= right.getValue() ? 1 : 0);
                }
                break;
        }
        return result;
    }

    @Override
    public boolean isTruthy() {
        return false;
    }

    public void setIsNegated(boolean isNegated) {
        this.isNegated = isNegated;
    }

    public void setOperation(final TokenType operation) {
        this.operation = operation;
    }

    public void addOperand(final ConditionOperand conditionEx) {
        operands.add(conditionEx);
    }
}
