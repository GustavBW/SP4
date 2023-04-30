package g7.sp4.common.models;

public enum WHState {
    IDLE(0),EXECUTING(1),ERROR(2), ERROR_UNKNOWN(-1);
    public int state;
    WHState(int state){
        this.state=state;
    }
    public static WHState valueOf(int state){
        return switch (state){
            case 0 -> IDLE;
            case 1 -> EXECUTING;
            case 2 -> ERROR;
            default -> ERROR_UNKNOWN;

        };
    }


}
