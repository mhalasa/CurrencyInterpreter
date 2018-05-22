package structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Program {
    //private List<Function> functions = new LinkedList<>();
    private Map<String, Function> functions = new HashMap<>();
    private ConfigBlock configBlock;

//    public List<Function> getFunctions() {
//        return functions;
//    }
    public ConfigBlock getConfigBlock() {
        return configBlock;
    }
    public void setConfigBlock(ConfigBlock configBlock) {
        this.configBlock = configBlock;
    }
//    public void addFunction(Function function) {
//        functions.add(function);
//    }


    public Map<String, Function> getFunctions() {
        return functions;
    }

    public void addFunction(Function function) {
        this.functions.put(function.getName(), function);
    }
}
