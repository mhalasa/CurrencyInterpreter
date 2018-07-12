import executor.Executor;
import lexer.Lexer;
import parser.Parser;
import semcheck.SemCheck;
import structures.AssingStatement;
import structures.Function;
import structures.Literal;
import structures.Program;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;


public class CurrencyInterpreter {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("test1");
        Parser parser = new Parser(lexer);
        SemCheck semCheck = new SemCheck();
        Executor executor = new Executor();
        Program program;
        try {
            program = parser.parse();
            semCheck.check(program);
            executor.execute(program);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
