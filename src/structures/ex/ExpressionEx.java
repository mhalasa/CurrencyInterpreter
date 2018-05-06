package structures.ex;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class ExpressionEx implements ExpressionOperand {
    private List<TokenType> operations = new LinkedList<>();
    private List<ExpressionOperand> operands = new LinkedList<>();

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        final LiteralEx value = new LiteralEx();
        value.setValue(operands.get(0).execute(scope, functions).getValue());
        if (operations.size() == 0) {
            return value;
        } else {
            int index = 0;
            for (final TokenType op : operations) {
                final ExpressionOperand operand = operands.get(index + 1);
                index++;
                if (op == TokenType.ADD) {
                    value.plus(operand.execute(scope, functions));
                } else if (op == TokenType.SUB) {
                    value.minus(operand.execute(scope, functions));
                } else if (op == TokenType.MUL) {
                    value.multi(operand.execute(scope, functions));
                } else if (op == TokenType.DIV) {
                    value.div(operand.execute(scope, functions));
                }
            }
            return value;
        }
    }

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
