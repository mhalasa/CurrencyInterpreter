package structures;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;

public class MuliplicativeExpression extends Node {
    private List<TokenType> operations;
    private List<ConvertExpression> operands;

    public MuliplicativeExpression(List<TokenType> operations, List<ConvertExpression> operands) {
        this.operations = operations;
        this.operands = operands;
    }

    public void addOperator(final TokenType operator) {
        operations.add(operator);
    }

    public void addOperand(final ConvertExpression operand) {
        operands.add(operand);
    }

    public List<ConvertExpression> getOperands() {
        return operands;
    }

    @Override
    public Type getType() {
        return Type.MultiplicativeExpression;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        Literal left = operands.get(0).execute(scope, program);
        Literal result = null;
        if (operations.size() == 0) {
            return left;
        } else {
            int index = 0;
            for (TokenType op : operations) {
                ConvertExpression operand = operands.get(index + 1);
                index++;
                if (op == TokenType.MUL) {
                    left = left.multi(operand.execute(scope, program));
                } else if (op == TokenType.DIV) {
                    left = left.div(operand.execute(scope, program));
                }
            }
        }
        return left;
    }
}
