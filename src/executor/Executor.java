package executor;


import structures.Function;
import structures.Literal;
import structures.Program;

import java.util.LinkedList;
import java.util.List;

public class Executor {
    public void execute(Program program) {
        Function mainFunction = program.getFunctions().get("main");
        mainFunction.execute(program, new LinkedList<>());
    }
}
