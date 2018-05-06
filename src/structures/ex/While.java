package structures.ex;

import java.util.Map;

/**
 * Created by wprzecho on 15.06.16.
 */
public class While extends Instruction {
    private ConditionEx condition;
    private Block block;

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        LiteralEx result = null;
        while (condition.execute(scope, functions).isTruthy()) {
            result = block.execute(scope, functions);
        }
        return result;
    }

    public void setCondition(final ConditionEx condition) {
        this.condition = condition;
    }

    public void setBlock(final Block block) {
        this.block = block;
    }
}
