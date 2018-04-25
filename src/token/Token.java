package token;

import lexer.Position;

public class Token {

    private TokenType tokenType;
    private String stringValue;
    private double doubleValue;
    private Position position;

    public Token(String stringValue, TokenType tokenType){
        this.tokenType = tokenType;
        this.stringValue = stringValue;
        if (tokenType == TokenType.NUMBER)
            this.doubleValue = Double.parseDouble(stringValue);
        else
            this.doubleValue = 0;
    }

    public Token(String stringValue, TokenType tokenType, Position position){
        this.tokenType = tokenType;
        this.stringValue= stringValue;
        this.position = position;
        if (tokenType == TokenType.NUMBER)
            this.doubleValue = Double.parseDouble(stringValue);
        else
            this.doubleValue = 0;

    }

    public String getstringValue() {
        return stringValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Position getPosition() {
        return position;
    }
}
