package structures;

public class Variable extends Node {
    private String name;
    private double value;

    public void setName(final String name) {
        this.name = name;
    }

    public void setValue(final double value) {
        this.value = value;
    }

    public Type getType() {
        return Type.Variable;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
