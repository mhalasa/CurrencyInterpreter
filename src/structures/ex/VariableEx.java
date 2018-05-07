package structures.ex;


public class VariableEx implements ExpressionOperand, ConditionOperand {
    private String name;
    private String currency;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
