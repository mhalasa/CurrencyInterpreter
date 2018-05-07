package structures.ex;


public class While extends Instruction {
    private ConditionEx condition;
    private Block block;


    public void setCondition(final ConditionEx condition) {
        this.condition = condition;
    }

    public void setBlock(final Block block) {
        this.block = block;
    }
}
