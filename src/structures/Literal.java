package structures;


import java.util.Map;

public class Literal extends Node {
    private double value;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Type getType() {
        return Type.Literal;
    }

    public double getValue() {
        return value;
    }

    public void plus(final Literal sec) {
        if (currency != null){
            if (sec.getCurrency() != null) {
                if (currency.equals(sec.getCurrency())) {
                    value += sec.getValue();
                } else {

                }
            }
        }
        value += sec.getValue();
    }

    public void minus(final Literal sec) {

        value -= sec.getValue();
    }

    public void multi(final Literal sec) {

        value *= sec.getValue();
    }

    public void div(final Literal sec) {

        value /= sec.getValue();
    }

    public Literal execute(Scope scope, Map<String, Function> functions) {
        return this;
    }
}
