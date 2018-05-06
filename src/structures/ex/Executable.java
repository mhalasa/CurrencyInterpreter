package structures.ex;

import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public interface Executable {
    public abstract LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions);
}
