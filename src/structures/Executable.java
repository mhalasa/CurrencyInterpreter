package structures;

import java.util.Map;

public interface Executable {
    public abstract Literal execute(Scope scope, Program program);
}
