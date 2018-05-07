package structures.ex;

import java.util.*;

public class Scope {
    private Scope parentScope = null;
    //private Set<String> variables = new HashSet<>();
    private List<String> variables = new LinkedList<>();

    public boolean addVariable(final String name) {
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

    public void setVariable(final String name) {
        variables.add(name);
    }

    public void setParentScope(final Scope parentScope) {
        this.parentScope = parentScope;
    }

    public boolean hasVariable(final String variableName) {
        if (!variables.contains(variableName) && parentScope != null) {
            return parentScope.hasVariable(variableName);
        }
        return variables.contains(variableName);
    }

}
