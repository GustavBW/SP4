package g7.sp4.util;

import java.util.HashMap;
import java.util.Map;

public class XMLParser {

    /**
     * Retrieves the part of the XML that is denoted by the given field name. Headers and Content
     */
    public static String getField(String fullXML, String fieldName){
        final int indexOfFieldNameStart = fullXML.indexOf(fieldName) - 1;
        if(indexOfFieldNameStart == -1) return null;
        int indexOfFieldEnd = fullXML.indexOf("/"+fieldName,indexOfFieldNameStart);
        if(indexOfFieldEnd == -1) return null;
        indexOfFieldEnd += ("/"+fieldName).length() + 1;

        return fullXML.substring(indexOfFieldNameStart,indexOfFieldEnd);
    }

    /**
     * Retrieves the content of the part of the XML that is denoted by the given field name.
     */
    public static String getFieldContent(String fullXML, String fieldName)
    {
        String fieldXML = getField(fullXML,fieldName);
        if(fieldXML == null) return null;
        final int fieldContentEnd = fieldXML.lastIndexOf("<");
        final int fieldContentStart = fieldXML.indexOf(">");
        return fieldXML.substring(fieldContentStart + 1,fieldContentEnd);
    }

    /**
     * Retrieves the headers of the part of the XML that is denoted by the given field name.
     */
    public static String[] getFieldHeaders(String fullXML, String fieldName)
    {
        String fieldXML = getField(fullXML,fieldName);
        if(fieldXML == null) return null;
        final int fieldHeadersStart = fieldXML.indexOf("<");
        final int fieldHeadersEnd = fieldXML.indexOf(">");
        return fieldXML.substring(fieldHeadersStart + 1,fieldHeadersEnd).split(" ");
    }

    public static Map<String,String> getFieldHeadersMap(String fulLXML, String fieldName)
    {
        String[] headers = getFieldHeaders(fulLXML,fieldName);
        if(headers == null) return null;
        Map<String,String> toReturn = new HashMap<>();
        for(String header : headers){
            String[] kv = header.split("=");
            if(kv.length == 1){
                toReturn.put(kv[0].trim(),"");
            }else {
                toReturn.put(kv[0].trim(), kv[1].trim());
            }
        }
        return toReturn;
    }


}
