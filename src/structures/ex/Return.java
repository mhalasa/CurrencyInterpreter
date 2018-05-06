package structures.ex;

import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Return extends Instruction {
    private ExpressionEx value;

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        return value.execute(scope, functions);
    }

    public void setValue(final ExpressionEx value) {
        this.value = value;
    }

    @Override
    public boolean canReturn() {
        return true;
    }
}