import java.util.concurrent.atomic.AtomicBoolean;

public class Flag {

    String name;
    AtomicBoolean state;

    public void set(
            boolean state
    ) {
        this.state.set(state);
    }

}
