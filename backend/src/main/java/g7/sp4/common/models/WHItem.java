package g7.sp4.common.models;

import g7.sp4.util.ArrayUtil;
import g7.sp4.util.IntUtil;
import g7.sp4.util.JSONWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record WHItem(int id, String content) {

    public static List<WHItem> fromWHRequest(String xmlContent)
    {
        List<WHItem> toReturn = new ArrayList<>();
        String inventory = JSONWrapper.parseSimpleObject(xmlContent).get("Inventory");
        System.out.println("inventory: " + inventory);

        String[] objectsSplit = JSONWrapper.parseObjectArray(inventory);
        System.out.println("As js obj array: ");
        ArrayUtil.print(objectsSplit);

        for(String object : objectsSplit){
            Map<String,String> parsed = JSONWrapper.parseSimpleObject(object);
            toReturn.add(new WHItem(
                    IntUtil.parseOr(parsed.get("Id"),-1),
                    parsed.get("Content")
            ));
        }

        return toReturn;
    }
}
