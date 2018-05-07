package structures;


public abstract class Node {
    public enum Type {
        AssignStatement,
        FunCall,
        Condition,
        Expression,
        ConvertExpression,
        IfStatement,
        ReturnStatement,
        StatementBlock,
        Literal,
        Variable,
        WhileStatement,
        PrintStatement
    }
    public abstract Type getType();
}
