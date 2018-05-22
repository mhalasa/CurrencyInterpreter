package structures;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Function extends Node{
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

    @Override
    public Type getType() {
        return Type.Function;
    }


    public Literal execute(Scope scope, Map<String, Function> functions, List<Literal> arguments) {
        int varIdx = 0;

        for (Literal argument : arguments) {
            statementBlock.getScope().setVariable(statementBlock.getScope().getVarName(varIdx), argument);
        }

        return statementBlock.execute(functions);
    }
}
