package g7.sp4.util;

//imported from gbw TheScheduler
public class LongUtil {

    public static Long parseOr(String value, long onFailure){
        try{
            return Long.parseLong(value);
        }catch (NumberFormatException | NullPointerException e){
            return onFailure;
        }
    }

}
