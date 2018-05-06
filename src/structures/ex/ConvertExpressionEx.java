package structures.ex;

import structures.ConvertExpression;
import structures.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConvertExpressionEx implements ExpressionOperand {
    public ExpressionOperand operand;
    public List<String> currencies = new LinkedList<>();

    public ExpressionOperand getOperand() {
        return operand;
    }

    public void setOperand(ExpressionOperand operand) {
        this.operand = operand;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }

    @Override
    public LiteralEx execute(Scope scope, Map<String, FunctionEx> functions) {
        return null;
    }
}
