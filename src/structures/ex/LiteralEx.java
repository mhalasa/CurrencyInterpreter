package structures.ex;

import java.util.Map;


public class LiteralEx implements ExpressionOperand, ConditionOperand {
    private double value;
    private boolean isBool = false;


    public void setValue(double value) {
        this.value = value;
    }

    public void setIsBool(boolean isBool) {
        this.isBool = isBool;
    }

    public boolean isBool() {
        return isBool;
    }

    public double getValue() {
        return value;
    }

    public void plus(final LiteralEx sec) {
        value += sec.getValue();
    }

    public void minus(final LiteralEx sec) {
        value -= sec.getValue();
    }

    public void multi(final LiteralEx sec) {
        value *= sec.getValue();
    }

    public void div(final LiteralEx sec) {
        value /= sec.getValue();
    }


}
