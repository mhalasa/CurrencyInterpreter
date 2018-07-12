package structures;


public interface Executable {
    public abstract Literal execute(Scope scope, Program program);
}
