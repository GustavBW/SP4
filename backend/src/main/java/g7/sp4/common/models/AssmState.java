package g7.sp4.common.models;

public enum AssmState {
	IDLE(0), EXECUTING(1), ERROR(2), ERROR_UNKNOWN(-1);

	public final int value;
	AssmState(int value){
		this.value = value;
	}

	public static AssmState valueOf(int val){
		return switch(val){
			case 0 -> IDLE;
			case 1 -> EXECUTING;
			case 2 -> ERROR;
			default -> ERROR_UNKNOWN;
		};
	}
}
