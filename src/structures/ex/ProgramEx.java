package structures.ex;

import structures.ConfigBlock;

import java.util.List;

public class ProgramEx {
    List<FunctionEx> functions;
    ConfigBlock configuration;

    public ProgramEx(List<FunctionEx> functions, ConfigBlock configuration) {
        this.functions = functions;
        this.configuration = configuration;
    }
}
