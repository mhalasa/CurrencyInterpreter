package structures;

public class IfStatement extends Node {
    private Condition condition;
    private StatementBlock trueBlock;
    private StatementBlock elseBlock;

    public IfStatement(Condition condition, StatementBlock trueBlock, StatementBlock elseBlock) {
        this.condition = condition;
        this.trueBlock = trueBlock;
        this.elseBlock = elseBlock;
    }

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

    @Override
    public Literal execute(Scope scope, Program program) {
        if (condition.execute(scope, program).isTruthy()) {
            return trueBlock.execute(scope, program);
        } else if (elseBlock != null) {
            return elseBlock.execute(scope, program);
        }
        return null;
    }
}
