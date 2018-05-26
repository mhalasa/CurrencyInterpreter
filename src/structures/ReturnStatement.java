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

    @Override
    public Literal execute(Scope scope, Program program) {
        return returnValue.execute(scope, program);
    }
}
