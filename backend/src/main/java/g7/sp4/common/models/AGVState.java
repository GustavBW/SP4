package g7.sp4.common.models;

public enum AGVState {
    IDLE(1), EXECUTING(2), CHARGING(3), ERROR_UNKNOWN(-1);
    public final int value;
    AGVState(int value){
        this.value = value;
    }
    public static AGVState valueOf(int val){
        return switch(val){
            case 1 -> IDLE;
            case 2 -> EXECUTING;
            case 3 -> CHARGING;
            default -> ERROR_UNKNOWN;
        };
    }
}
