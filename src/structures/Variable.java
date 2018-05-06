package structures;

/**
 * Created by wprzecho on 30.05.16.
 */
public class Variable extends Node {
    public String name;
    public double value;

    public Variable() {
    }

    public Variable(String name, int value) {
        this.name = name;
        this.value = value;
    }

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
