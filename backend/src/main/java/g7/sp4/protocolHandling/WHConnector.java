package g7.sp4.protocolHandling;

import g7.sp4.common.models.SystemConfigurationService;
import g7.sp4.common.models.WHItem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WHConnector implements WHConnectionService {

    // define connection to warehouse
    private final String WHUrl = "http://"+ SystemConfigurationService.WH_IP+":"+SystemConfigurationService.WH_PORT+"/service.asmx";

    private String getInventoryPayload= "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <Body>\n" +
            "    <GetInventory xmlns=\"http://tempuri.org/\" />\n" +
            "  </Body>\n" +
            "</Envelope>";

    private String pickItemPayload = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <Body>\n" +
            "    <PickItem xmlns=\"http://tempuri.org/\">\n" +
            "      <item>%s</item>\n" +
            "    </PickItem>\n" +
            "  </Body>\n" +
            "</Envelope>";
    private String insertItemPayload = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <Body>\n" +
            "    <InsertItem xmlns=\"http://tempuri.org/\">\n" +
            "      <item>%s</item>\n" +
            "    </InsertItem>\n" +
            "  </Body>\n" +
            "</Envelope>";

    //setup
    private void getInventory(String operationName, int state, String payload){
        // Create the HTTP connection
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(WHUrl).openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setDoOutput(true);

            // Construct the request body
            String requestBody = String.format(payload);

            // Send the request
            connection.getOutputStream().write(requestBody.getBytes());

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("PUT request failed with response code " + responseCode);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    @Override
    public WHItem[] getInventory() {
        return new WHItem[0];
    }

    @Override
    public Flag prepareItem() {
        return null;
    }


}
