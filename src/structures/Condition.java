package structures;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;


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
        Literal result = new Literal();
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
                result.setValue(1);
                return result;
            case EQUALS:
                result = compareLiterals(scope, program, Condition::isEquals);
                break;
            case NOT_EQUALS:
                result = compareLiterals(scope, program, Condition::isNotEqual);
                break;
            case LOWER:
                result = compareLiterals(scope, program, Condition::isLower);
                break;
            case LOWER_EQUALS:
                result = compareLiterals(scope, program, Condition::isLowerOrEqual);
                break;
            case GREATER:
                result = compareLiterals(scope, program, Condition::isGrater);
                break;
            case GREATER_EQUALS:
                result = compareLiterals(scope, program, Condition::isGraterOrEqual);
                break;
        }
        return result;
    }

    private Literal compareLiterals(Scope scope, Program program, BiPredicate<Double, Double> p) {
        Literal left = operands.get(0).execute(scope, program);
        Literal right = operands.get(1).execute(scope, program);
        Literal result = new Literal();
        if (left.getCurrency() == null && right.getCurrency() == null) {
            result.setValue( p.test(left.getValue(), right.getValue()) ? 1 : 0);
        }
        else if ((left.getCurrency() != null && right.getCurrency() == null) ||
                (left.getCurrency() == null && right.getCurrency() != null)) {
            System.err.println("Error: Cannot compare currency type with not currency type: ");
            System.exit(1);
        }
        else {
            if (left.getCurrency().equals(right.getCurrency())) {
                result.setValue(p.test(left.getValue(), right.getValue()) ? 1 : 0);
            } else {
                double leftValue = left.getValue() / program.getConfigBlock().getExchangeRate(left.getCurrency());
                double rightValue = right.getValue() / program.getConfigBlock().getExchangeRate(right.getCurrency());
                result.setValue(p.test(leftValue, rightValue) ? 1 : 0);
            }
        }
        return result;
    }


    static public boolean isEquals(Double d1, Double d2) {
        return d1.doubleValue() == d2.doubleValue();
    }

    static public boolean isNotEqual(Double d1, Double d2) {
        return d1.doubleValue() != d2.doubleValue();
    }

    static public boolean isLower(Double d1, Double d2) {
        return d1.doubleValue() < d2.doubleValue();
    }

    static public boolean isLowerOrEqual(Double d1, Double d2) {
        return d1.doubleValue() <= d2.doubleValue();
    }

    static public boolean isGrater(Double d1, Double d2) {
        return d1.doubleValue() > d2.doubleValue();
    }

    static public boolean isGraterOrEqual(Double d1, Double d2) {
        return d1.doubleValue() >= d2.doubleValue();
    }
}
