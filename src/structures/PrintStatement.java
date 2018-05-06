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
        return null;
    }
}
