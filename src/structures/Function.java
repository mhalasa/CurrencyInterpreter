package structures;

import java.util.LinkedList;
import java.util.List;


public class Function {
    public String name;
    public List<String> parameters = new LinkedList<>();
    public StatementBlock statementBlock;

    public void setName(final String name) {
        this.name = name;
    }

    public void setParameters(final List<String> parameters) {
        this.parameters = parameters;
    }

    public void setStatementBlock(final StatementBlock statementBlock) {
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
}
