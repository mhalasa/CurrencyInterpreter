package structures;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wprzecho on 30.05.16.
 */
public class Condition extends Node {
    public boolean isNegated = false;
    public TokenType operator = TokenType.UNDEFINED;
    public List<Node> operands = new LinkedList<>();

    public void setOperator(final TokenType operator) {
        this.operator = operator;
    }

    public void setNegated(boolean isNegated) {
        this.isNegated = isNegated;
    }

    public void addOperand(final Node operand) {
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
}
