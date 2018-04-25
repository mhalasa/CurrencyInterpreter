import lexer.Lexer;
import token.Token;
import token.TokenType;

public class CurrencyInterpreter {
    public static void main(String[] args) {

        Lexer lexer = new Lexer("test2");
        Token token;
        while ( (token = lexer.getNextToken()).getTokenType() != TokenType.END_OF_FILE)
            System.out.println(String.format("%-15s %-10s %s" ,token.getTokenType() , token.getPosition(), token.getstringValue()));

    }
}
