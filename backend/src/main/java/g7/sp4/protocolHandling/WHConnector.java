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
    public final String WHUrl = "http://" + SystemConfigurationService.WH_IP + ":" + SystemConfigurationService.WH_PORT + "/Service.asmx";


    public static void main(String[] args) {
        // showing output for "testing"
        WHConnector whConnector = new WHConnector(); System.out.println(whConnector.insertItemPayload(1, "RocketLauncher"));
        System.out.println(whConnector.pickItemPayload(1));

        System.out.println(whConnector.getInventoryPayload());

    }

    //setup
    public String getInventoryPayload() {

        final String getInventoryPayload = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <Body>\n" +
                "    <GetInventory xmlns=\"http://tempuri.org/\" />\n" +
                "  </Body>\n" +
                "</Envelope>";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(WHUrl).openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setDoOutput(true);

            // Construct the request body
            String requestBody = String.format(getInventoryPayload);

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


            // cleaning up resources
            reader.close();
            responseStream.close();
            connection.disconnect();


            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("PUT request failed with response code " + responseCode);
            }

            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR: Failed receiving response";
    }

    public String pickItemPayload(Integer id) {

        final String pickItemPayload = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <Body>\n" +
                "    <PickItem xmlns=\"http://tempuri.org/\">\n" +
                "      <trayId>" + id + "</trayId>\n" +
                "    </PickItem>\n" +
                "  </Body>\n" +
                "</Envelope>";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(WHUrl).openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setDoOutput(true);

            // Construct the request body
            String requestBody = String.format(pickItemPayload);

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


            // cleaning up resources
            reader.close();
            responseStream.close();
            connection.disconnect();


            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("PUT request failed with response code " + responseCode);
            }

            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR: Failed receiving response";
    }


    public String insertItemPayload(Integer id, String Item) {

        final String insertItemPayload = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <Body>\n" +
                "    <InsertItem xmlns=\"http://tempuri.org/\">\n" +
                "      <trayId>" + id + "</trayId>\n" +
                "      <name>" + Item + "</name>\n" +
                "    </InsertItem>\n" +
                "  </Body>\n" +
                "</Envelope>";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(WHUrl).openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setDoOutput(true);

            // Construct the request body
            String requestBody = String.format(insertItemPayload);

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


            // cleaning up resources
            reader.close();
            responseStream.close();
            connection.disconnect();


            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("PUT request failed with response code " + responseCode);
            }

            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR: Failed receiving response";
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
