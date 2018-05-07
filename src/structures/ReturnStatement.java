package structures;


public class ReturnStatement extends Node {
    private Node returnValue;

    public void setReturnValue(Node value) {
        this.returnValue = value;
    }

    public Type getType() {
        return Type.ReturnStatement;
    }

    public Node getReturnValue() {
        return returnValue;
    }
}
