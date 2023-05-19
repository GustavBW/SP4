package g7.sp4.protocolHandling;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class Flag {

    /**
     * This function is run on Flag.get() and is passed to the flag instance by whichever connector created it.
     */
    @FunctionalInterface
    public interface FlagCheckFunction {
        /**
         * @param state The current state of the flag.
         * @param flag The flag instance. (Usually this).
         * @return wether or not the operation this flag represents is done.
         */
        boolean apply(AtomicBoolean state, Flag flag);
    }

    private AtomicBoolean state = new AtomicBoolean(false);

    private Error error;
    /**
     * This function checks the condition set by the author of the flag
     * (i.e. where the flag is created).
     */
    private FlagCheckFunction checkFunction;

    public Flag(FlagCheckFunction checkFunction){
        this.checkFunction = checkFunction;
    }
    public Flag() {
        this.checkFunction = (state, flag) -> state.get();
    }

    /**
     * @return the result of the provided checkFunction.
     */
    public boolean get(){
        return checkFunction.apply(state,this);
    }

    public Flag setState(boolean newState) {
        state.set(newState);
        return this;
    }

    public Error getError() {
        return error;
    }

    public boolean hasError(){
        return error != null;
    }

    public Flag setError(Error error) {
        this.error = error;
        return this;
    }
}
