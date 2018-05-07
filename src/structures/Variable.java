package structures;

public class Variable extends Node {
    private String name;
    private Literal value;

    public Literal getValue() {
        return value;
    }

    public void setValue(Literal value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return Type.Variable;
    }

    public String getName() {
        return name;
    }
}
