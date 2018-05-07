package structures;


import java.util.LinkedList;
import java.util.List;

public class StatementBlock extends Node {
    private Scope scope = new Scope();
    private List<Node> instructions = new LinkedList<>();

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void addInstruction(Node instruction) {
        instructions.add(instruction);
    }

    public List<Node> getInstructions() {
        return instructions;
    }

    public Type getType() {
        return Type.StatementBlock;
    }
}
