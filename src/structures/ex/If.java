package structures.ex;

import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class If extends Instruction {
    private ConditionEx condition;
    private Block trueBlock;
    private Block elseBlock = null;

    public ConditionEx getCondition() {
        return condition;
    }

    public void setCondition(final ConditionEx condition) {
        this.condition = condition;
    }

    public Block getTrueBlock() {
        return trueBlock;
    }

    public void setTrueBlock(final Block trueBlock) {
        this.trueBlock = trueBlock;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(final Block elseBlock) {
        this.elseBlock = elseBlock;
    }

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        if (condition.execute(scope, functions).isTruthy()) {
            return trueBlock.execute(scope, functions);
        } else if (elseBlock != null) {
            return elseBlock.execute(scope, functions);
        }
        return null;
    }
}
