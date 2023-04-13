package sp4.protocolHandling;

import g7.sp4.protocolHandling.WHConnector;
import org.junit.jupiter.api.*;

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
    public void testGetInventoryPayload() throws IOException {
        WHConnector whConnector = new WHConnector();
        String inventoryPayload = whConnector.getInventoryPayload();
        // Checking the result is in the right topic
        String result = "GetInventoryResult";

        assertTrue(inventoryPayload.contains(result));
        HttpURLConnection connection = (HttpURLConnection) new URL(whConnector.WHUrl).openConnection();
        // Checking the response code is 200
        Assertions.assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testPickItemPayload() throws IOException {
        WHConnector whConnector = new WHConnector();
        String inventoryPayload = whConnector.pickItemPayload(1);
        // Checking the result is in the right topic
        String result = "Received pick operation.";

        assertTrue(inventoryPayload.contains(result));
        HttpURLConnection connection = (HttpURLConnection) new URL(whConnector.WHUrl).openConnection();
        // Checking the response code is 200
        Assertions.assertEquals(200, connection.getResponseCode());

    }
    @Test
    public void testInsertItemPayload() throws IOException {
        WHConnector whConnector = new WHConnector();
        String inventoryPayload = whConnector.insertItemPayload(1,"A GrenadeLauncher");
        // Checking the result is in the right topic
        String result = "Received insert operation.";

        assertTrue(inventoryPayload.contains(result));
        HttpURLConnection connection = (HttpURLConnection) new URL(whConnector.WHUrl).openConnection();
        // Checking the response code is 200
        Assertions.assertEquals(200, connection.getResponseCode());

    }


}




