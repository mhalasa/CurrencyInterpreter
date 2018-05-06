package structures.ex;

import structures.Function;

import java.util.List;
import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class FunctionEx extends Block {

    private Function function;
    private String name;

    public Function getFunction() {
        return function;
    }

    public void setFunction(final Function function) {
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions, final List<LazyValue> arguments) {
        int varIdx = 0;

        for (final LazyValue argument : arguments) {
            this.scope.setVariable(this.scope.getVarName(varIdx++), argument);
        }
        LiteralEx result = null;
        for (final Instruction instruction : instructions) {
            result = instruction.execute(this.scope, functions);
            if (instruction instanceof Return) {
                return result;
            }
        }
        return result;
    }
}
