package executor;


import structures.Function;
import structures.Literal;
import structures.Program;

import java.util.LinkedList;
import java.util.List;

public class Executor {
    public void execute(Program program) {
        Function mainFunction = program.getFunctions().get("fun");
        List<Literal> list = new LinkedList<>();
        Literal l1 = new Literal();
        Literal l2 = new Literal();
        l1.setValue(12);
        l1.setCurrency("dol");
        l2.setValue(3.22);
        l2.setCurrency("pln");
        list.add(l1); list.add(l2);
        mainFunction.execute(null, null, list);
    }
}
