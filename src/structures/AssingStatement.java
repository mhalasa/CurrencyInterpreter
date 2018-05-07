package structures;


public class AssingStatement extends Node {
    private Variable variable;
    private Node value;

    public Variable getVariable() {
        return variable;
    }

    public Node getValue() {
        return value;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public void setValue(Node value) {
        this.value = value;
    }

    public Type getType() {
        return Type.AssignStatement;
    }

}
