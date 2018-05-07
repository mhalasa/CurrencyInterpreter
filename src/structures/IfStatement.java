package structures;

public class IfStatement extends Node {
    public Condition condition;
    public StatementBlock trueBlock;
    public StatementBlock elseBlock = null;

    public void setCondition(final Condition cond) {
        this.condition = cond;
    }

    public void setTrueBlock(final StatementBlock trueBlock) {
        this.trueBlock = trueBlock;
    }

    public void setElseBlock(final StatementBlock elseBlock) {
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
