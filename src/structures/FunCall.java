package structures;

import java.util.LinkedList;
import java.util.List;


public class FunCall extends Node {
    private String name;
    private List<Node> arguments;

    public FunCall(String name, List<Node> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addArgument(Node argument) {
        arguments.add(argument);
    }
    public Type getType() {
        return Type.FunCall;
    }
    public String getName() {
        return name;
    }
    public List<Node> getArguments() {
        return arguments;
    }


    @Override
    public Literal execute(Scope scope, Program program) {
        Function function = program.getFunctions().get(name);

        List<Literal> arguments = new LinkedList<>();
        for (Node argument : this.arguments) {
            arguments.add(argument.execute(scope, program));
        }

        return function.execute(program, arguments);
    }
}
