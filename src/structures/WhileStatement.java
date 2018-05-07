package structures;


public class WhileStatement extends Node {
    public Condition condition;
    public StatementBlock statementBlock;

    public void setCondition(final Condition condition) {
        this.condition = condition;
    }

    public void setStatementBlock(final StatementBlock statementBlock) {
        this.statementBlock = statementBlock;
    }

    public Type getType() {
        return Type.WhileStatement;
    }

    public Condition getCondition() {
        return condition;
    }

    public StatementBlock getStatementBlock() {
        return statementBlock;
    }
}
