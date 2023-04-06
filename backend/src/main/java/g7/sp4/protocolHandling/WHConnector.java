package g7.sp4.protocolHandling;

import g7.sp4.common.models.SystemConfigurationService;
import g7.sp4.common.models.WHItem;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class WHConnector implements WHConnectionService {

    // define connection to warehouse
    private final String WHUrl = "http://" + SystemConfigurationService.WH_IP + ":" + SystemConfigurationService.WH_PORT + "/service.asmx";

    private final String getInventoryPayload = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <Body>\n" +
            "    <GetInventory xmlns=\"http://tempuri.org/\" />\n" +
            "  </Body>\n" +
            "</Envelope>";

    private final String pickItemPayload = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <Body>\n" +
            "    <PickItem xmlns=\"http://tempuri.org/\">\n" +
            "      <item>%s</item>\n" +
            "    </PickItem>\n" +
            "  </Body>\n" +
            "</Envelope>";
    private final String insertItemPayload = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "  <Body>\n" +
            "    <InsertItem xmlns=\"http://tempuri.org/\">\n" +
            "      <item>%s</item>\n" +
            "    </InsertItem>\n" +
            "  </Body>\n" +
            "</Envelope>";

    public static void main(String[] args) {
        // showing output for "testing"
        WHConnector whConnector = new WHConnector();
        whConnector.deliverPayload(1, whConnector.getInventoryPayload);


    }

    //setup
    private void deliverPayload(int state, String payload) {
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

            //reading response
            InputStream responseStream = connection.getInputStream();
            var reader = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String responseString = responseBuilder.toString();

            // print the response string
            System.out.println(responseString);
            System.out.println("efter responsestring");

            // cleaning up resources
            reader.close();
            responseStream.close();
            connection.disconnect();

            // testing
            System.out.println(responseString);
            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("PUT request failed with response code " + responseCode);
            }
        } catch (IOException e) {
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
