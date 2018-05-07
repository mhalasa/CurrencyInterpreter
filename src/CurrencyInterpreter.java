import lexer.Lexer;
import parser.Parser;
import semcheck.SemCheck;
import structures.AssingStatement;
import structures.Expression;
import structures.Literal;
import structures.Program;
import structures.ex.FunctionEx;
import structures.ex.ProgramEx;
import token.Token;
import token.TokenType;

import java.util.List;

public class CurrencyInterpreter {
    public static void main(String[] args) {



      /*  Lexer lexer = new Lexer("test2");
        Token token;
        while ( (token = lexer.getNextToken()).getTokenType() != TokenType.END_OF_FILE)
            System.out.println(String.format("%-15s %-10s %s" ,token.getTokenType() , token.getPosition(), token.getstringValue()));
      */

        Lexer lexer = new Lexer("test1");
        Parser parser = new Parser(lexer);
        Program program = null;
        SemCheck semCheck = new SemCheck();
        try {
            program = parser.parse();
            ProgramEx functions = semCheck.check(program);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
