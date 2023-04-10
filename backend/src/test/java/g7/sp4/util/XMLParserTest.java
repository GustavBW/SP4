package g7.sp4.util;

import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class XMLParserTest {

    @BeforeAll
    static void beforeAll(){
        System.out.println("||| Testing XMLParser ================== Testing XMLParser |||");
    }

    @BeforeEach
    void setUp() {
        System.out.println();
    }

    @AfterEach
    void tearDown() {
        System.out.println();
    }

    @Test
    void getContentOfField() {
        System.out.println("=====Testing XMLParser getContentOfField=====");

        System.out.println("\t\t Retrieving GetInventoryResult field content");
        assertEquals(getInventoryResultRawContent,XMLParser.getFieldContent(testXMLFull, "GetInventoryResult"));

        System.out.println("\t\t Retrieving GetInventoryResponse field content");
        assertEquals(getInventoryResponseRawContent,XMLParser.getFieldContent(testXMLFull, "GetInventoryResponse"));
    }

    @Test
    void getFieldHeaders(){
        System.out.println("=====Testing XMLParser getFieldHeaders=====");

        System.out.println("\t\t Retrieving Envelope field headers");
        assertArrayEquals(envelopeFieldHeaders,XMLParser.getFieldHeaders(testXMLFull,"s:Envelope"));

        System.out.println("\t\t Retrieving Inventory Response field headers");
        assertArrayEquals(inventoryResponseFieldHeaders,XMLParser.getFieldHeaders(testXMLFull,"GetInventoryResponse"));
    }

    @Test
    void getFieldHeadersMap(){
        System.out.println("=====Testing XMLParser getFieldHeadersMap=====");

        System.out.println("\t\t Testing Envelope field headers map");
        Map<String,String> parsedResult = XMLParser.getFieldHeadersMap(testXMLFull,"s:Envelope");
        assertNotNull(parsedResult);
        for(String expectedKey: envelopeHeadersMap.keySet()){
            assertTrue(parsedResult.containsKey(expectedKey));
            assertEquals(envelopeHeadersMap.get(expectedKey),parsedResult.get(expectedKey));
        }
    }

    private final String testXMLFull =
        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance%22%3E\">" +
            "<s:Body>" +
                "<GetInventoryResponse xmlns=\"http://tempuri.org/%22%3E\">" +
                    "<GetInventoryResult>" +
                        "{" +
                        "\"Inventory\":[" +
                            "{\"Id\":1,\"Content\":\"Item 1\"}," +
                            "{\"Id\":2,\"Content\":\"Item 2\"}," +
                            "{\"Id\":3,\"Content\":\"Item 3\"}," +
                            "{\"Id\":4,\"Content\":\"Item 4\"}," +
                            "{\"Id\":5,\"Content\":\"Item 5\"}," +
                            "{\"Id\":6,\"Content\":\"Item 6\"}," +
                            "{\"Id\":7,\"Content\":\"Item 7\"}," +
                            "{\"Id\":8,\"Content\":\"Item 8\"}," +
                            "{\"Id\":9,\"Content\":\"Item 9\"}," +
                            "{\"Id\":10,\"Content\":\"Item 10\"}" +
                        "]," +
                        "\"State\":0, " +
                        "\"DateTime\":\"2023-04-10T19:36:32.2605226+00:00\"" +
                        "}" +
                    "</GetInventoryResult>" +
                "</GetInventoryResponse>" +
            "</s:Body>" +
        "</s:Envelope>";

    private final String getInventoryResultRawContent =
            "{" +
                "\"Inventory\":[" +
                    "{\"Id\":1,\"Content\":\"Item 1\"}," +
                    "{\"Id\":2,\"Content\":\"Item 2\"}," +
                    "{\"Id\":3,\"Content\":\"Item 3\"}," +
                    "{\"Id\":4,\"Content\":\"Item 4\"}," +
                    "{\"Id\":5,\"Content\":\"Item 5\"}," +
                    "{\"Id\":6,\"Content\":\"Item 6\"}," +
                    "{\"Id\":7,\"Content\":\"Item 7\"}," +
                    "{\"Id\":8,\"Content\":\"Item 8\"}," +
                    "{\"Id\":9,\"Content\":\"Item 9\"}," +
                    "{\"Id\":10,\"Content\":\"Item 10\"}" +
                    "]," +
                "\"State\":0, " +
                "\"DateTime\":\"2023-04-10T19:36:32.2605226+00:00\"" +
            "}";

    private final String getInventoryResponseRawContent =
            "<GetInventoryResult>" +
                "{" +
                    "\"Inventory\":[" +
                        "{\"Id\":1,\"Content\":\"Item 1\"}," +
                        "{\"Id\":2,\"Content\":\"Item 2\"}," +
                        "{\"Id\":3,\"Content\":\"Item 3\"}," +
                        "{\"Id\":4,\"Content\":\"Item 4\"}," +
                        "{\"Id\":5,\"Content\":\"Item 5\"}," +
                        "{\"Id\":6,\"Content\":\"Item 6\"}," +
                        "{\"Id\":7,\"Content\":\"Item 7\"}," +
                        "{\"Id\":8,\"Content\":\"Item 8\"}," +
                        "{\"Id\":9,\"Content\":\"Item 9\"}," +
                        "{\"Id\":10,\"Content\":\"Item 10\"}" +
                        "]," +
                    "\"State\":0, " +
                    "\"DateTime\":\"2023-04-10T19:36:32.2605226+00:00\"" +
                "}" +
            "</GetInventoryResult>";

    private final String[] envelopeFieldHeaders = new String[]{
            "s:Envelope",
            "xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"",
            "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"",
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance%22%3E\""
    };

    private final Map<String,String> envelopeHeadersMap = Map.of(
            "s:Envelope", "",
            "xmlns:s","\"http://schemas.xmlsoap.org/soap/envelope/\"",
            "xmlns:xsd","\"http://www.w3.org/2001/XMLSchema\"",
            "xmlns:xsi","\"http://www.w3.org/2001/XMLSchema-instance%22%3E\""
    );

    private final String[] inventoryResponseFieldHeaders = new String[]{
            "GetInventoryResponse", "xmlns=\"http://tempuri.org/%22%3E\""
    };
}