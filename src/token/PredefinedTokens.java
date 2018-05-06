package token;

import java.util.HashMap;
import java.util.Map;

public class PredefinedTokens {
    public static Map<String, TokenType> keywords = new HashMap<>();
    public static Map<String, TokenType> operators = new HashMap<>();

    static {
        keywords.put("function", TokenType.FUNCTION);
        keywords.put("defaultCurrency", TokenType.DEF_CURRENCY);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("return", TokenType.RETURN);
        keywords.put("print", TokenType.PRINT);

        operators.put("<", TokenType.LOWER);
        operators.put("<=", TokenType.LOWER_EQUALS);
        operators.put(">", TokenType.GREATER);
        operators.put(">=", TokenType.GREATER_EQUALS);
        operators.put("(", TokenType.PARENT_OPEN);
        operators.put(")", TokenType.PARENT_CLOSE);
        operators.put("{", TokenType.BRACKET_OPEN);
        operators.put("}", TokenType.BRACKET_CLOSE);
        operators.put("==", TokenType.EQUALS);
        operators.put("!=", TokenType.NOT_EQUALS);
        operators.put("+", TokenType.ADD);
        operators.put("-", TokenType.SUB);
        operators.put("*", TokenType.MUL);
        operators.put("/", TokenType.DIV);
        operators.put(";", TokenType.SEMICOLON);
        operators.put("=", TokenType.ASSIGN);
        operators.put(",", TokenType.COMMA);
        operators.put("!", TokenType.UNARY);
        operators.put("&&", TokenType.AND);
        operators.put("||", TokenType.OR);
    }
}
