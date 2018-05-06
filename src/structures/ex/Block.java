package structures.ex;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Block extends Instruction {
    protected Scope scope = new Scope();
    protected List<Instruction> instructions = new LinkedList<>();

    public Scope getScope() {
        return scope;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        final Scope newScope = this.scope.getScope(scope);
        for (final Instruction instruction : instructions) {
            final LiteralEx result = instruction.execute(newScope, functions);
            if (instruction instanceof Return)
                return result;
        }
        return null;
    }

    public void addInstruction(final Instruction instruction) {
        instructions.add(instruction);
    }
}
