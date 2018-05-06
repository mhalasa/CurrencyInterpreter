package lexer;

import token.PredefinedTokens;
import token.Token;
import token.TokenType;


public class Lexer {

    private Source source;
    private char current;


    public Lexer(String fileName) {
        source = new Source(fileName);
        current = source.getNextChar();
    }

    public Token getNextToken() {
        ignoreWhiteSpaces();

        if (current == 0)
            return new Token("", TokenType.END_OF_FILE);

        Position position = new Position(source.getPosition());
        Token token;

        if ((token = processNumber(position)) != null) {
            return token;
        } else if ((token = processAlpha(position)) != null) {
            return token;
        } else
            return processOperator(position);
    }

    private void ignoreWhiteSpaces() {
        while (Character.isWhitespace(current))
            current = source.getNextChar();
    }

    private boolean isDigit() {
        return current >= '0' && current <= '9';
    }

    private boolean isLetter() {
        return (current >= 'a' && current <= 'z') || (current >= 'A' && current <= 'Z');
    }

    private boolean isNextCharOperator(char next) {
        return ((next == '=') && (current == '<' || current == '>' || current == '=' || current == '!'))
                || (next == '&' && current == '&') || (next == '|' && current == '|');
    }

    private Token processNumber(Position position) {
        if (isDigit()) {
            StringBuilder stringBuilder = new StringBuilder().append(current);
            current = source.getNextChar();
            while (isDigit()) {
                stringBuilder.append(current);
                current = source.getNextChar();
            }

            if (current == '.') {
                stringBuilder.append(current);
                current = source.getNextChar();
                while (isDigit()) {
                    stringBuilder.append(current);
                    current = source.getNextChar();
                }
            }

            String number = stringBuilder.toString();
            return new Token(number, TokenType.NUMBER, position);
        } else
            return null;
    }


    private Token processAlpha(Position position) {
        if (isLetter()) {
            StringBuilder stringBuilder = new StringBuilder().append(current);
            current = source.getNextChar();
            while (isLetter() || isDigit()) {
                stringBuilder.append(current);
                current = source.getNextChar();
            }

            String alpha = stringBuilder.toString();
            TokenType keywordToken = PredefinedTokens.keywords.get(alpha);

            if (keywordToken != null)
                return new Token(alpha, keywordToken, position);
            else
                return new Token(alpha, TokenType.ID, position);
        } else
            return null;
    }

    private Token processOperator(Position position){
        StringBuilder stringBuilder = new StringBuilder().append(current);
        char next = source.getNextChar();

        if (isNextCharOperator(next)) {
            stringBuilder.append(next);
            current = source.getNextChar();
        }
        else
            current = next;

        String operator = stringBuilder.toString();
        TokenType operatorToken = PredefinedTokens.operators.get(operator);

        if (operatorToken != null)
            return new Token(operator, operatorToken, position);
        else
            return new Token(operator, TokenType.UNDEFINED, position);
        }
}



