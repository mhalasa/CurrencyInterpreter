package structures.ex;

import structures.Function;

import java.util.List;
import java.util.Map;

public class FunctionEx extends Block {

    private Function function;
    private String name;

    public Function getFunction() {
        return function;
    }

    public void setFunction(final Function function) {
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
