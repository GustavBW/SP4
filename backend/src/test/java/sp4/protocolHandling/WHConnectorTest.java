package sp4.protocolHandling;

import g7.sp4.protocolHandling.WHConnector;
import org.junit.jupiter.api.*;

import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WHConnectorTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("||| Testing WHConnector ================== Testing WHConnector |||");
    }

    @Test
    public void testGetInventoryPayload() throws Exception {
        WHConnector whConnector = new WHConnector();
// Print SOAP Response
        SOAPMessage response = whConnector.sendSOAPRequest(whConnector.connectToWH(),()->{
            return whConnector.getInventoryPayload();
        });
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeTo(out);



        String inventoryPayload = out.toString();
        // Checking the result is in the right topic
        String result = "GetInventoryResult";

        assertTrue(inventoryPayload.contains(result));
        HttpURLConnection connection = (HttpURLConnection) new URL(whConnector.URL).openConnection();
        // Checking the response code is 200
        Assertions.assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testPickItemPayload() throws Exception {
        WHConnector whConnector = new WHConnector();
// Print SOAP Response
        SOAPMessage response = whConnector.sendSOAPRequest(whConnector.connectToWH(),()->{
            return whConnector.pickItemPayload(1);
        });
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeTo(out);

        // Checking the result is in the right topic
        String result = "Received pick operation.";
        String pickItemPayload=out.toString();

        assertTrue(pickItemPayload.contains(result));
        HttpURLConnection connection = (HttpURLConnection) new URL(whConnector.URL).openConnection();
        // Checking the response code is 200
        Assertions.assertEquals(200, connection.getResponseCode());

    }
    @Test
    public void testInsertItemPayload() throws Exception {
        WHConnector whConnector = new WHConnector();
// Print SOAP Response
        SOAPMessage response = whConnector.sendSOAPRequest(whConnector.connectToWH(),()->{
            return whConnector.insertItemPayload(1,"TestingItem");
        });
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeTo(out);

        // Checking the result is in the right topic
        String insertItemPayload=out.toString();
        String result = "Received insert operation.";

        assertTrue(insertItemPayload.contains(result));
        HttpURLConnection connection = (HttpURLConnection) new URL(whConnector.URL).openConnection();
        // Checking the response code is 200
        Assertions.assertEquals(200, connection.getResponseCode());

    }


}




