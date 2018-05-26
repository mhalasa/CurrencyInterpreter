package structures;


import java.util.ArrayList;
import java.util.List;

public class PrintStatement extends Node{
    private List<Variable> parameters = new ArrayList<>();

    public void addParameter(Variable variable){
        parameters.add(variable);
    }

    @Override
    public Type getType() {
        return Type.PrintStatement;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        for (Variable var : parameters) {
            System.out.print(scope.getString(var.getName()));
        }
        System.out.println();
        return null;
    }

    public List<Variable> getParameters() {
        return parameters;
    }
}
