package g7.sp4.util;

//imported from gbw TheScheduler
public class IntUtil {

    public static int parseOr(Object value, int onFail){
        try{
            return Integer.parseInt(""+value);
        }catch (NullPointerException | NumberFormatException e){
            return onFail;
        }
    }

}
