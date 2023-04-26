package g7.sp4.common.models;

import com.google.gson.GsonBuilder;
import g7.sp4.util.*;

import java.util.List;

import com.google.gson.Gson;

public class WHItem implements Comparable<WHItem> {

    private static class GetInventoryResult{
        public List<WHItem> Inventory;
        public int State;
        public String DateTime;
        public GetInventoryResult(List<WHItem> inventory, int state, String DateTime){
            this.Inventory = inventory;
            this.State = state;
            this.DateTime = DateTime;
        }
    }

    public int Id;
    public String Content;

    public WHItem(){};
    public WHItem(int id, String content){
        this.Id = id;
        this.Content = content;
    }

    /**
     * Creates an immutable array of WHItems from some XML request
     * @param xmlContent - the full content as known from the WHConnector.getInventory()
     * @param isInReducedForm - wether or not the input string is only the content of the GetInventoryResult field
     * @return a list of wh items.
     */
    public static List<WHItem> fromWHRequest(String xmlContent, boolean isInReducedForm)
    {
        if(!isInReducedForm)
            xmlContent = XMLParser.getFieldContent(xmlContent,"GetInventoryResult");

        return GsonUtil.DECODER_WH_INVENTORY.fromJson(xmlContent, GetInventoryResult.class).Inventory;
    }

    @Override
    public String toString()
    {
        return "WHItem{\"id\":"+ Id +", \"content\":\""+ Content +"\"}";
    }

    @Override
    public int compareTo(WHItem item2){
        if(item2.Id == this.Id && item2.Content.equals(this.Content))
            return 1;
        return 0;
    }
}
