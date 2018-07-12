package structures;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Function {
    private String name;
    private List<String> parameters;
    private StatementBlock statementBlock;

    public Function() {
        parameters = new ArrayList<>();
        statementBlock = new StatementBlock();
    }

    public Function(String name, List<String> parameters, StatementBlock statementBlock) {
        this.name = name;
        this.parameters = parameters;
        this.statementBlock = statementBlock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void setStatementBlock(StatementBlock statementBlock) {
        this.statementBlock = statementBlock;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public StatementBlock getStatementBlock() {
        return statementBlock;
        }

    public Literal execute(Program program, List<Literal> arguments ) {
        int varIdx = 0;

        for (Literal argument : arguments) {
            statementBlock.getScope().getParentScope().setVariableValue(statementBlock.getScope().getParentScope().getVarName(varIdx++), argument);
        }

        return statementBlock.execute(statementBlock.getScope(), program);
    }
}
