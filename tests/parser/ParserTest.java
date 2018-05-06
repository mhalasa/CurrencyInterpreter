package parser;

import lexer.Lexer;
import org.junit.Assert;
import org.junit.Test;
import structures.*;

import javax.xml.bind.ValidationEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class ParserTest {

    Lexer lexer;
    Parser parser;

    private void setSource(String string){
        try {
            PrintWriter printWriter = new PrintWriter("test");
            printWriter.println(string);
            printWriter.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        lexer = new Lexer("test");
        parser = new Parser(lexer);
    }

    @Test
   public void assignmentTest() {
        setSource("function fun() {x=5;y=x;}");

        Program program = null;
        try {
            program = parser.parse();
        }catch (Exception e){
            e.printStackTrace();
        }
        Function function = program.functions.get(0);
        Assert.assertEquals(2, function.statementBlock.instructions.size());

        Variable variable = new Variable("x", 0);
        Literal literal = new Literal(5);
        AssingStatement assingStatement = new AssingStatement(variable, literal);
    }

}