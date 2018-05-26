package structures;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;


public class Condition extends Node {
    private boolean isNegated = false;
    private TokenType operator = TokenType.UNDEFINED;
    private List<Node> operands = new LinkedList<>();

    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    public void setNegated(boolean isNegated) {
        this.isNegated = isNegated;
    }

    public void addOperand(Node operand) {
        operands.add(operand);
    }

    public Type getType() {
        return Type.Condition;
    }

    public boolean isNegated() {
        return isNegated;
    }

    public TokenType getOperation() {
        return operator;
    }

    public List<Node> getOperands() {
        return operands;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        final Literal result = new Literal();
        result.setIsBool(true);
        switch (operator) {
            case UNDEFINED:
                if (!isNegated) {
                    return operands.get(0).execute(scope, program);
                } else {
                    result.setValue(operands.get(0).execute(scope, program).isTruthy() ? 0 : 1);
                }
                break;
            case OR:
                for (Node operand : operands) {
                    if (operand.execute(scope, program).isTruthy()) {
                        result.setValue(1);
                        return result;
                    }
                }
                result.setValue(0);
                return result;
            case AND:
                for (Node operand : operands) {
                    if (!operand.execute(scope, program).isTruthy()) {
                        result.setValue(0);
                        return result;
                    }
                }
                result.setValue(0);
                return result;
            case EQUALS:
                Literal left = operands.get(0).execute(scope, program);
                Literal right = operands.get(1).execute(scope, program);
                if (left.isBool() && right.isBool()) {
                    result.setValue(left.isTruthy() == right.isTruthy() ? 1 : 0);
                } else if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() == right.getValue() ? 1 : 0);
                }
                break;
            case NOT_EQUALS:
                left = operands.get(0).execute(scope, program);
                right = operands.get(1).execute(scope, program);
                if (left.isBool() && right.isBool()) {
                    result.setValue(left.isTruthy() != right.isTruthy() ? 1 : 0);
                } else if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() != right.getValue() ? 1 : 0);
                }
                break;
            case LOWER:
                left = operands.get(0).execute(scope, program);
                right = operands.get(1).execute(scope, program);
                if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() < right.getValue() ? 1 : 0);
                }
                break;
            case LOWER_EQUALS:
                left = operands.get(0).execute(scope, program);
                right = operands.get(1).execute(scope, program);
                if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() <= right.getValue() ? 1 : 0);
                }
                break;
            case GREATER:
                left = operands.get(0).execute(scope, program);
                right = operands.get(1).execute(scope, program);
                if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() > right.getValue() ? 1 : 0);
                }
                break;
            case GREATER_EQUALS:
                left = operands.get(0).execute(scope, program);
                right = operands.get(1).execute(scope, program);
                if (!left.isBool() && !right.isBool()) {
                    result.setValue(left.getValue() >= right.getValue() ? 1 : 0);
                }
                break;
        }
        return result;
    }
}
