package structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Scope {
    private Scope parentScope = null;
    private Map<String, Literal> variables = new HashMap<>();
    private List<String> varOrder = new LinkedList<>();

    public boolean addVariable(String name) {
        if (variables.containsKey(name)) {
            return false;
        } else {
            variables.put(name, new Literal());
            varOrder.add(name);
            return true;
        }
    }

    public Map<String, Literal> getVariables() {
        return variables;
    }

    public void setVariable(String name, Literal value) {
        variables.put(name, value);
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public boolean hasVariable(String variableName) {
        if (!variables.containsKey(variableName) && parentScope != null) {
            return parentScope.hasVariable(variableName);
        }
        return variables.containsKey(variableName);
    }

    public void setVariableValue(String name, Literal value) {
          variables.put(name, value);
    }

    public String getVarName(int index) {
        return varOrder.get(index);
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public String getString(String name) {
        if (variables.get((name)) != null)
            return variables.get(name).getString();
        else
            return parentScope.getString(name);
    }

    public Literal getVariableValue(String name) {
        if (variables.get(name) != null)
            return variables.get(name);
        else
            return parentScope.getVariableValue(name);
    }
}
