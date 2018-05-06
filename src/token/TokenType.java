package token;

public enum TokenType {
    UNDEFINED,
    NUMBER,
    FUNCTION,
    DEF_CURRENCY,

    IF,
    WHILE,
    ELSE,
    RETURN,
    ID,
    PRINT,

    LOWER,
    LOWER_EQUALS,
    GREATER,
    GREATER_EQUALS,
    EQUALS,
    NOT_EQUALS,

    PARENT_OPEN,
    PARENT_CLOSE,
    BRACKET_OPEN,
    BRACKET_CLOSE,

    ADD,
    SUB,
    MUL,
    DIV,
    SEMICOLON,
    ASSIGN,
    COMMA,
    AND,
    OR,
    UNARY,
    END_OF_FILE
}
