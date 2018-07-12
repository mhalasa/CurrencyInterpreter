package structures;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StatementBlock extends Node {
    private Scope scope;
    private List<Node> instructions;

    public StatementBlock() {
        instructions = new ArrayList<>();
        scope = new Scope();
    }

    public StatementBlock(List<Node> instructions) {
        this.instructions = instructions;
        scope = new Scope();
    }

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

    @Override
    public Literal execute(Scope scope, Program program) {
        Literal result = null;
        for (Node instruction : instructions) {
            result = instruction.execute(scope, program);
            if (instruction instanceof ReturnStatement) {
                return result;
            }
        }
        return result;
    }
}
