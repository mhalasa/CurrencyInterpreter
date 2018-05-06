package structures;

/**
 * Created by wprzecho on 31.05.16.
 */
public class Literal extends Node {
    public double value;

    public Literal() {
    }

    public Literal(double value) {
        this.value = value;
    }

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
