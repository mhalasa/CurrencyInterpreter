package structures;

/**
 * Created by wprzecho on 31.05.16.
 */
public abstract class Node {
    protected Node parent;

    public enum Type {
        Assignment,
        Call,
        Condition,
        Expression,
        ConvertExpression,
        FunDefinition,
        IfStatement,
        Program,
        ReturnStatement,
        StatementBlock,
        VarDeclaration,
        Literal,
        Variable,
        WhileStatement,
        PrintFun
    }

    public abstract Type getType();


}
