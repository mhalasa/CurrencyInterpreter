package structures;

public class PrimaryExpression extends Node {
    private Node node;

    public PrimaryExpression(Node node) {
        this.node = node;
    }

    @Override
    public Type getType() {
        return Type.PrimaryExpression;
    }

    public Type getNodeType() {return node.getType();}

    public Node getNode() {
        return node;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        return node.execute(scope, program);
    }
}
