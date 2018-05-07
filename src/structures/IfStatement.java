package structures;

public class IfStatement extends Node {
    private Condition condition;
    private StatementBlock trueBlock;
    private StatementBlock elseBlock = null;

    public void setCondition(Condition cond) {
        this.condition = cond;
    }

    public void setTrueBlock(StatementBlock trueBlock) {
        this.trueBlock = trueBlock;
    }

    public void setElseBlock(StatementBlock elseBlock) {
        this.elseBlock = elseBlock;
    }

    public Type getType() {
        return Type.IfStatement;
    }

    public Condition getCondition() {
        return condition;
    }

    public StatementBlock getTrueBlock() {
        return trueBlock;
    }

    public boolean hasElseBlock() {
        return elseBlock != null;
    }

    public StatementBlock getElseBlock() {
        return elseBlock;
    }
}
