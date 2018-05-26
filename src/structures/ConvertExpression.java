package structures;

import java.util.LinkedList;
import java.util.List;

public class ConvertExpression extends Node{
    private PrimaryExpression operand;
    private List<String> currencies = new LinkedList<>();

    public PrimaryExpression getOperand() {
        return operand;
    }

    public void setOperand(PrimaryExpression operand) {
        this.operand = operand;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    @Override
    public Type getType() {
        return Type.ConvertExpression;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        Literal literal = operand.execute(scope, program);
        for (String currency : currencies) {
            if (literal.getCurrency() != null && !currency.equals(literal.getCurrency())) {
                literal.setValue(literal.getValue() / program.getConfigBlock().getExchangeRate(currency));
            }
            literal.setCurrency(currency);
        }
        return literal;
    }
}
