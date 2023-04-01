package sp4.protocolHandling;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class Flag {

    String name;
    AtomicBoolean state;
    /**
     * This function checks the condition set by the author of the flag
     * (i.e. where the flag is created).
     */
    Function<AtomicBoolean,Boolean> checkFunction;

    public Flag(Function<AtomicBoolean,Boolean> checkFunction){
        this.checkFunction = checkFunction;
    }

    /**
     * @return the result of the provided checkFunction.
     */
    public boolean get(){
        return checkFunction.apply(state);
    }

}
