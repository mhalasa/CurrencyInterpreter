package structures;


public class Literal extends Node {
    private double value;

    public void setValue(final double value) {
        this.value = value;
    }

    public Type getType() {
        return Type.Literal;
    }

    public double getValue() {
        return value;
    }
}
