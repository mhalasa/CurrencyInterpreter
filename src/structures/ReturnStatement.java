package structures;

/**
 * Created by wprzecho on 30.05.16.
 */
public class ReturnStatement extends Node {
    public Node returnValue;

    public void setReturnValue(final Node value) {
        this.returnValue = value;
    }

    public Type getType() {
        return Type.ReturnStatement;
    }

    public Node getReturnValue() {
        return returnValue;
    }
}
