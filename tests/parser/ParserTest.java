package parser;

import lexer.Lexer;
import lexer.Source;
import org.junit.Assert;
import org.junit.Test;
import structures.*;


import java.io.Reader;
import java.io.StringReader;


public class ParserTest {

    Parser parser;

    private void setSource(String string){
        Reader reader = new StringReader(string);
        Source source = new Source(reader);
        Lexer lexer = new Lexer(source);
        parser = new Parser(lexer);
    }

    @Test
   public void assignmentTest() {
        setSource("function fun() {x=5.23; y=x;}");

        Program program = null;
        try {
            program = parser.parse();
        }catch (Exception e){
            e.printStackTrace();
        }
        Function function = program.getFunctions().get(0);
        Assert.assertEquals(2, function.getStatementBlock().getInstructions().size());
        Assert.assertEquals(Node.Type.AssignStatement, function.getStatementBlock().getInstructions().get(0).getType());

        AssingStatement assingStatement = (AssingStatement) function.getStatementBlock().getInstructions().get(0);
        Assert.assertEquals("x", assingStatement.getVariable().getName());
    }

    @Test
    public void Test() {

    }
}