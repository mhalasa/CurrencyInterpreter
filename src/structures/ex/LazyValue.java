package structures.ex;

/**
 * Created by wprzecho on 11.06.16.
 */
public class LazyValue {
    private LiteralEx value = null;
    private boolean isCalculated = false;
    private Assignable instructionValue = null;

    public LiteralEx getValue() {
        return value;
    }

    public void setValue(final LiteralEx value) {
        this.value = value;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setCalculated(boolean calculated) {
        isCalculated = calculated;
    }

    public Assignable getInstructionValue() {
        return instructionValue;
    }

    public void setInstructionValue(final Assignable instructionValue) {
        this.instructionValue = instructionValue;
    }

    @Override
    public String toString() {
        if (isCalculated) {
            return String.valueOf(value.getValue());
        } else {
            return "value not set";
        }
    }
}
