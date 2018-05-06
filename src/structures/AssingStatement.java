package structures;

/**
 * Created by wprzecho on 30.05.16.
 */
public class AssingStatement extends Node {
    public Variable variable;
    public Node value;

    public AssingStatement() {
    }

    public AssingStatement(Variable variable, Node value) {
        this.variable = variable;
        this.value = value;
    }

    public void setVariable(final Variable variable) {
        this.variable = variable;
    }

    public void setValue(final Node value) {
        this.value = value;
    }

    public Type getType() {
        return Type.Assignment;
    }

}
