package structures.ex;

import token.TokenType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConditionEx implements ConditionOperand {
    private boolean isNegated;
    public TokenType operation = TokenType.UNDEFINED;
    public List<ConditionOperand> operands = new LinkedList<>();

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
