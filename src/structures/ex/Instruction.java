package structures.ex;

/**
 * Created by wprzecho on 11.06.16.
 */
public abstract class Instruction implements Executable {
    public boolean canReturn() {
        return false;
    }

}
