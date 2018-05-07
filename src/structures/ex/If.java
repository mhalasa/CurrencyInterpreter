package structures.ex;


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

}
