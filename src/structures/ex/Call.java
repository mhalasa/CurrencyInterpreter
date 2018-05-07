package structures.ex;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Call extends Instruction implements ExpressionOperand {
    private String name;
    private List<ExpressionEx> arguments = new LinkedList<>();


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void addArgument(final ExpressionEx assignable) {
        arguments.add(assignable);
    }
}
