package structures;

import java.util.LinkedList;
import java.util.List;

public class ConvertExpression extends Node{
    private Node operand;
    private List<String> currencies = new LinkedList<>();

    public Node getOperand() {
        return operand;
    }

    public void setOperand(Node operand) {
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
}
