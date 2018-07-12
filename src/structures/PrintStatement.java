package structures;


public class PrintStatement extends Node{
    private Variable parameter;

    public PrintStatement(Variable parameter) {
        this.parameter = parameter;
    }

    public Variable getParameter() {
        return parameter;
    }

    @Override
    public Type getType() {
        return Type.PrintStatement;
    }

    @Override
    public Literal execute(Scope scope, Program program) {
        System.out.print(scope.getString(parameter.getName()));
        System.out.println();
        return null;
    }
}
