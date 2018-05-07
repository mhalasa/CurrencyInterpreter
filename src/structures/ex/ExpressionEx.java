package structures.ex;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExpressionEx implements ExpressionOperand {
    private List<TokenType> operations = new LinkedList<>();
    private List<ExpressionOperand> operands = new LinkedList<>();

    public void addOperand(final ExpressionOperand operand) {
        operands.add(operand);
    }

    public void setOperations(final List<TokenType> operations) {
        this.operations = operations;
    }

    public List<TokenType> getOperations() {
        return operations;
    }

    public List<ExpressionOperand> getOperands() {
        return operands;
    }
}
