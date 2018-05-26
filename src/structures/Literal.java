package structures;

public class Literal extends Node {
    private double value;
    private String currency;
    private boolean isBool = false;

    public Literal() {
        value = 0;
        currency = null;
    }

    public Literal(double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

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

    public Literal add(Literal sec, ConfigBlock conf) {
        if (currency != null && sec.getCurrency() != null) {
            if (currency.equals(sec.getCurrency())) {
                return new Literal(value + sec.getValue(), currency);
            } else {
                return new Literal(value / conf.getExchangeRate(currency)
                        + sec.getValue() / conf.getExchangeRate(sec.getCurrency()), conf.getDefaultCurrency());
            }
        } else {
            String resultCurrency = currency == null ? sec.getCurrency() : currency;
            return new Literal(value + sec.getValue(), resultCurrency);
        }
    }

    public Literal sub(final Literal sec, ConfigBlock conf) {
        if (currency != null && sec.getCurrency() != null) {
            if (currency.equals(sec.getCurrency())) {
                return new Literal(value - sec.getValue(), currency);
            } else {
                return new Literal(value / conf.getExchangeRate(currency)
                        - sec.getValue() / conf.getExchangeRate(sec.getCurrency()), conf.getDefaultCurrency());
            }
        } else {
            String resultCurrency = currency == null ? sec.getCurrency() : currency;
            return new Literal(value - sec.getValue(), resultCurrency);
        }
    }

    public Literal multi(final Literal sec) {

        if (currency != null && sec.getCurrency() != null) {
            System.err.println("Cannot multiply currency by currency");
            System.exit(1);
        }else {
            String resultCurrency = currency == null ? sec.getCurrency() : currency;
            return new Literal(value * sec.getValue(), resultCurrency);
        }
        return null;
    }

    public Literal div(final Literal sec) {
        if (sec.getCurrency() == null && sec.getValue() != 0) {
            return new Literal(value / sec.getValue(), currency);
        } else {
          //Exception
          return new Literal();
        }
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        return this;
    }

    public String getString() {
        if (currency != null) {
            return value + " " + currency;
        } else {
            return String.valueOf(value);
        }
    }

    public void setIsBool(boolean isBool) {
        this.isBool = isBool;
    }

    public boolean isBool() {
        return isBool;
    }

    public boolean isTruthy() {
        return value == 1;
    }
}
