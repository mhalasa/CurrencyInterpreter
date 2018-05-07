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
}
