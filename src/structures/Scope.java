package structures;

import java.util.LinkedList;
import java.util.List;


public class Scope {
    private Scope parentScope = null;
    private List<String> variables = new LinkedList<>();

    public boolean addVariable(String name) {
        if (variables.contains(name)) {
            return false;
        } else {
            variables.add(name);
            return true;
        }
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariable(String name) {
        variables.add(name);
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public boolean hasVariable(String variableName) {
        if (!variables.contains(variableName) && parentScope != null) {
            return parentScope.hasVariable(variableName);
        }
        return variables.contains(variableName);
    }

}
