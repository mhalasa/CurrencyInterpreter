package structures;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;


public class AdditiveExpression extends Node {
    private List<TokenType> operations;
    private List<MuliplicativeExpression> operands;

    public AdditiveExpression(List<TokenType> operations, List<MuliplicativeExpression> operands) {
        this.operations = operations;
        this.operands = operands;
    }

    public void addOperator(final TokenType operator) {
        operations.add(operator);
    }

    public void addOperand(final MuliplicativeExpression operand) {
        operands.add(operand);
    }

    public List<MuliplicativeExpression> getOperands() {
        return operands;
    }


    @Override
    public Type getType() {
        return Type.AdditiveExpression;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        Literal left = operands.get(0).execute(scope, program);
        if (operations.size() == 0) {
            return left;
        } else {
            int index = 0;
            for (TokenType op : operations) {
                MuliplicativeExpression operand = operands.get(index + 1);
                index++;
                if (op == TokenType.ADD) {
                     left = left.add(operand.execute(scope, program), program.getConfigBlock());
                } else if (op == TokenType.SUB) {
                     left = left.sub(operand.execute(scope, program), program.getConfigBlock());
                }
            }
        }
        return left;
    }
}
