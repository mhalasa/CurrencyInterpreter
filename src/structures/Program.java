package structures;

import java.util.LinkedList;
import java.util.List;

public class Program {
    private List<Function> functions = new LinkedList<>();
    private ConfigBlock configBlock;

    public List<Function> getFunctions() {
        return functions;
    }
    public ConfigBlock getConfigBlock() {
        return configBlock;
    }
    public void setConfigBlock(ConfigBlock configBlock) {
        this.configBlock = configBlock;
    }
    public void addFunction(Function function) {
        functions.add(function);
    }
}
