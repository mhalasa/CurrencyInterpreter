package structures;

import java.util.LinkedList;
import java.util.List;


public class Function extends StatementBlock{
    private String name;
    private List<String> parameters = new LinkedList<>();
    private StatementBlock statementBlock;

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
}
