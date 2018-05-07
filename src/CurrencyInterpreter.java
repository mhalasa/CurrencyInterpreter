import lexer.Lexer;
import parser.Parser;
import semcheck.SemCheck;
import structures.Program;


public class CurrencyInterpreter {
    public static void main(String[] args) {



      /*  Lexer lexer = new Lexer("test2");
        Token token;
        while ( (token = lexer.getNextToken()).getTokenType() != TokenType.END_OF_FILE)
            System.out.println(String.format("%-15s %-10s %s" ,token.getTokenType() , token.getPosition(), token.getstringValue()));
      */

        Lexer lexer = new Lexer("test1");
        Parser parser = new Parser(lexer);
        SemCheck semCheck = new SemCheck();
        try {
            Program program = parser.parse();
            semCheck.check(program);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
