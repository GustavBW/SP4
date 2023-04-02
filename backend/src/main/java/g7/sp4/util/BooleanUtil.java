package g7.sp4.util;


//imported from gbw TheScheduler
public class BooleanUtil {

    public static final String[] FALSES = {
            "no","off","false","f","0"
    };
    public static final String[] TRUES = {
            "true","yes","t","1","on"
    };

    public static boolean parseOr(String s, boolean state){
        if(ArrayUtil.contains(FALSES,s.trim().toLowerCase())){
            return false;
        }else if(ArrayUtil.contains(TRUES,s.trim().toLowerCase())){
            return true;
        }

        return state;
    }

}
