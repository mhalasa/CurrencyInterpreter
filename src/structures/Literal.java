package structures;

import lexer.Position;

public class Literal extends Node {
    private double value;
    private String currency;
    private Position position;

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
            System.err.println("Error: Cannot multiply currency by currency in line: " + sec.getPosition().toString());
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
        } else if (sec.getValue() == 0){
            System.err.println("Error: Cannot divide by 0 in line: " + sec.getPosition().toString());
            System.exit(1);
        } else {
            System.err.println("Error: Cannot divide by currency in line: " + sec.getPosition().toString());
            System.exit(1);
        }
        return null;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        return this;
    }

    public String getString() {
        if (currency != null) {
            return round(value, 2) + " " + currency;
        } else {
            return String.valueOf(round(value, 2));
        }
    }


    public boolean isTruthy() {
        return value != 0;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}

