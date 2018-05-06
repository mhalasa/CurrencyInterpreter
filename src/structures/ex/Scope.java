package structures.ex;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Scope {
    private Scope parentScope = null;
    private Map<String, LazyValue> variables = new HashMap<>();
    private List<String> varOrder = new LinkedList<>();

    public boolean addVariable(final String name) {
        if (variables.containsKey(name)) {
            return false;
        } else {
            variables.put(name, new LazyValue());
            varOrder.add(name);
            return true;
        }
    }

    public Map<String, LazyValue> getVariables() {
        return variables;
    }

    public void setVariable(final String name, final LazyValue value) {
        variables.put(name, value);
    }

    public void setVariableValue(final String name, final LiteralEx value) {
        LazyValue variable = variables.get(name);
        if (variable != null) {
            variable.setValue(value);
            variable.setCalculated(true);
        } else {
            variable = new LazyValue();
            variable.setValue(value);
            variable.setCalculated(true);
            variables.put(name, variable);
        }
    }

    public String getVarName(final int index) {
        return varOrder.get(index);
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(final Scope parentScope) {
        this.parentScope = parentScope;
    }

    public boolean hasVariable(final String variableName) {
        if (!variables.containsKey(variableName) && parentScope != null) {
            return parentScope.hasVariable(variableName);
        }
        return variables.containsKey(variableName);
    }

    public Scope getScope(final Scope parentScope) {
        final Scope scope = new Scope();
        scope.setParentScope(parentScope);
        scope.setVarOrder(parentScope.getVarOrder());
        parentScope.getVariables().forEach((name, variable) -> scope.setVariable(name, variable));
        variables.forEach((name, variable) -> scope.setVariable(name, variable));
        return scope;
    }

    public List<String> getVarOrder() {
        return varOrder;
    }

    public void setVarOrder(final List<String> varOrder) {
        this.varOrder = varOrder;
    }

    public String getVariableString(final String name) {
        if (variables.containsKey(name)) {
            return variables.get(name).toString();
        } else {
            return null;
        }
    }

    public LazyValue getVariable(final String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            return null;
        }

    }
}
