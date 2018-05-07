package structures;

import java.util.LinkedList;
import java.util.List;


public class FunCall extends Node {
    private String name;
    private List<Node> arguments = new LinkedList<>();

    public void setName(final String name) {
        this.name = name;
    }
    public void addArgument(final Node argument) {
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
}
