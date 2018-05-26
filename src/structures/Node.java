package structures;


public abstract class Node implements Executable {
    public enum Type {
        AssignStatement,
        FunCall,
        Condition,
        AdditiveExpression,
        MultiplicativeExpression,
        PrimaryExpression,
        ConvertExpression,
        IfStatement,
        ReturnStatement,
        StatementBlock,
        Function,
        Literal,
        Variable,
        WhileStatement,
        PrintStatement
    }
    public abstract Type getType();
}
