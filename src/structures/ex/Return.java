package structures.ex;

import java.util.Map;


public class Return extends Instruction {
    private ExpressionEx value;


    public void setValue(final ExpressionEx value) {
        this.value = value;
    }


}