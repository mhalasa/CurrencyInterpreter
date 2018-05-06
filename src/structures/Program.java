package structures;

import java.util.LinkedList;
import java.util.List;

public class Program {
    public List<Function> getFunctions() {
        return functions;
    }

    public List<Function> functions = new LinkedList<>();



    private ConfigBlock configBlock;

    public ConfigBlock getConfigBlock() {
        return configBlock;
    }

    public void setConfigBlock(ConfigBlock configBlock) {
        this.configBlock = configBlock;
    }

    public void addFunction(final Function function) {
        functions.add(function);
    }
}