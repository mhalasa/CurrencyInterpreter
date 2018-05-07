package structures.ex;


public class Assignment extends Instruction {

    private String name;
    private ExpressionEx value;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ExpressionEx getValue() {
        return value;
    }

    public void setValue(final ExpressionEx value) {
        this.value = value;
    }
}
