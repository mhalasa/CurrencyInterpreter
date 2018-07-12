package structures;

import java.util.Map;

public class Variable extends Node {
    private String name;
    private Literal value;



    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return Type.Variable;
    }

    public String getName() {
        return name;
    }

    public Literal execute(final Scope scope, final Map<String, Function> functions) {
        return  value;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        return scope.getVariableValue(name);
    }

    public String getString() {
        return value.getString();
    }
}
