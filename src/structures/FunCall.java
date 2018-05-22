package structures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class FunCall extends Node {
    private String name;
    private List<Node> arguments = new LinkedList<>();

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

    public Literal execute(final Scope scope, final Map<String, Function> functions) {
        Function function = functions.get(name);

        List<Literal> parameters = new ArrayList<>();
        for (Node argument : arguments) {
            parameters.add(argument.execute(scope, functions));
        }

        return function.execute(scope,functions, parameters);
    }
}
