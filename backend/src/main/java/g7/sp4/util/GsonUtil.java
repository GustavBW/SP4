package g7.sp4.util;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

public class GsonUtil {

    @FunctionalInterface
    private interface CaseStrategy extends FieldNamingStrategy {
        String translateName(Field field);
    }
    public static final CaseStrategy IGNORE_CASING = (field) -> field.getName().toLowerCase();

    public static Gson DECODER_WH_INVENTORY = new GsonBuilder()
            .create();


}
