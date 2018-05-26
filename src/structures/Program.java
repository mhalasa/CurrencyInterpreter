package structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Program {
    private Map<String, Function> functions = new HashMap<>();
    private ConfigBlock configBlock;

    public ConfigBlock getConfigBlock() {
        return configBlock;
    }
    public void setConfigBlock(ConfigBlock configBlock) {
        this.configBlock = configBlock;
    }

    public Map<String, Function> getFunctions() {
        return functions;
    }

    public void addFunction(Function function) {
        this.functions.put(function.getName(), function);
    }
}
