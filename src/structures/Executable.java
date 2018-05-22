package structures;

import java.util.Map;

public interface Executable {
    public abstract Literal execute(Map<String, Function> functions, Scope scope );
}
