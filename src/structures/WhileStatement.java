package structures;


public class WhileStatement extends Node {
    private Condition condition;
    private StatementBlock statementBlock;

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setStatementBlock(StatementBlock statementBlock) {
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

    @Override
    public Literal execute(Scope scope, Program program) {
        while (condition.execute(scope, program).isTruthy()) {
            statementBlock.execute(scope, program);
        }
        return null;
    }
}
