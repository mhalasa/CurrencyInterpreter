package structures;

import java.util.LinkedList;
import java.util.List;

public class StatementBlock extends Node {

    public List<Node> instructions = new LinkedList<>();

    public void addInstruction(final Node instruction) {
        instructions.add(instruction);
    }

    public List<Node> getInstructions() {
        return instructions;
    }

    public Type getType() {
        return Type.StatementBlock;
    }
}
