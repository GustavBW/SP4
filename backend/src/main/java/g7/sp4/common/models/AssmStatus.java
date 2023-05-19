package g7.sp4.common.models;

import java.util.Date;

public record AssmStatus(String currentProcess, String message, AssmState state, Date timestamp) {

    @Override
    public String toString(){
        return "AssmState{"+state+","+message+"}";
    }

}
