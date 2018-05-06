package structures.ex;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Call extends Instruction implements Assignable, ExpressionOperand {
    private String name;
    private List<ExpressionEx> arguments = new LinkedList<>();

    @Override
    public LiteralEx execute(Scope scope, Map<String, FunctionEx> functions) {
        return null;
    }
/*
    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        final List<LazyValue> concreteArguments = new LinkedList<>();
        for (final Assignable argument : arguments) {
            final LazyValue lazyValue = new LazyValue();
            if ((argument instanceof VariableEx && !StdLib.printFun.equals(name)) || isValue(argument)) {
                lazyValue.setCalculated(true);
                lazyValue.setValue(argument.execute(scope, functions));
            } else {
                lazyValue.setCalculated(false);
                lazyValue.setInstructionValue(argument);
            }
            concreteArguments.add(lazyValue);
        }
        if (functions.containsKey(name)) {
            System.out.println("[DEBUG] function \"" + name + "\" start executing...");
            return functions.get(name).execute(scope, functions, concreteArguments);
        } else if (StdLib.printFun.equals(name)) {
            final VariableEx argument = (VariableEx) arguments.get(0);
            System.out.println(argument.getName() + " : " + scope.getVariableString(argument.getName()));
        }
        return null;

    }*/



    private boolean isValue(final Assignable argument) {
        if (argument instanceof ExpressionEx) {
            final ExpressionEx expr = (ExpressionEx) argument;
            if (expr.getOperations().size() == 0) {
                final ExpressionEx exprChild = (ExpressionEx) expr.getOperands().get(0);
                if (exprChild.getOperations().size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void addArgument(final ExpressionEx assignable) {
        arguments.add(assignable);
    }
}
