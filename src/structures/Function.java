package structures;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Function {
    private String name;
    private List<String> parameters = new LinkedList<>();
    private StatementBlock statementBlock = new StatementBlock();

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
