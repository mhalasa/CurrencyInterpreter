package structures.ex;

import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class VariableEx implements ExpressionOperand, ConditionOperand {
    private String name;
    private String currency;

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        final LazyValue variable = scope.getVariable(name);
        if (variable.isCalculated()) {
            return variable.getValue();
        } else {
            variable.setCalculated(true);
            variable.setValue(variable.getInstructionValue().execute(scope, functions));
            return variable.getValue();
        }
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean isTruthy() {
        return false;
    }

    public String getName() {
        return name;
    }
}
