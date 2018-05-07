package structures;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;


public class Expression extends Node {
    private List<TokenType> operations = new LinkedList<>();
    private List<Node> operands = new LinkedList<>();

    public void addOperator(final TokenType operator) {
        operations.add(operator);
    }

    public void addOperand(final Node operand) {
        operands.add(operand);
    }

    public Type getType() {
        return Type.Expression;
    }

    public List<Node> getOperands() {
        return operands;
    }

    public List<TokenType> getOperations() {
        return operations;
    }
}
