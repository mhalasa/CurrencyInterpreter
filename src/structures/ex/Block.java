package structures.ex;

import java.util.LinkedList;
import java.util.List;

public class Block extends Instruction {
    protected Scope scope = new Scope();
    protected List<Instruction> instructions = new LinkedList<>();

    public Scope getScope() {
        return scope;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void addInstruction(final Instruction instruction) {
        instructions.add(instruction);
    }
}
