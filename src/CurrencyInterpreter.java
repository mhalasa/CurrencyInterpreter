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


//
//        Lexer lexer = new Lexer("test2");
//        Token token;
//        while ( (token = lexer.getNextToken()).getTokenType() != TokenType.END_OF_FILE)
//            System.out.println(String.format("%-15s %-10s %s" ,token.getTokenType() , token.getPosition(), token.getstringValue()));


        Lexer lexer = new Lexer("test2");
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
//
//        Literal l1 = new Literal(2, null);
//        Literal l2 = new Literal(14, "eur");
//        List<Literal> l = new ArrayList<>();
//        l.add(l1); l.add(l2);


//        AssingStatement a = (AssingStatement) program.getFunctions().get("main").getStatementBlock().getInstructions().get(1);
//        Literal l = a.getValue().execute(program.getFunctions().get("main").getStatementBlock().getScope(), program);
//
//        System.out.println(l.getValue() + " " + l.getCurrency());

//        Literal l1 = new Literal(2, null);
//        Literal l2 = new Literal(14, "eur");
//        Literal res = l1.multi(l2);
//        System.out.println(res.getValue() + " " + res.getCurrency());
    }
}
