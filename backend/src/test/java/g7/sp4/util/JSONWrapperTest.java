package g7.sp4.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JSONWrapperTest {

    @BeforeEach
    void setUp() {
        System.out.println();
    }

    @AfterEach
    void tearDown() {
        System.out.println();
    }

    @Test
    void parseObjectArray() {
        System.out.println("=====JSONWrapper Testing parseObjectArray");
        assertArrayEquals(expectedSplit,JSONWrapper.parseObjectArray(jsonTestString));
    }

    @Test
    void parseObject(){
        System.out.println("=====JSONWrapper Testing parseObject");
        Map<String,String> actualParsing = JSONWrapper.parseObject(nestedJsonString);
        for(String expectedKey: expectedNestedParsing.keySet()){
            assertNotNull(actualParsing);
            assertTrue(actualParsing.containsKey(expectedKey));
            assertEquals(expectedNestedParsing.get(expectedKey),actualParsing.get(expectedKey));
        }
    }

    private final String jsonTestString =
                "[" +
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
                "]";
    private final String[] expectedSplit = new String[]{
            "{\"Id\":1,\"Content\":\"Item 1\"}" ,
            "{\"Id\":2,\"Content\":\"Item 2\"}" ,
            "{\"Id\":3,\"Content\":\"Item 3\"}" ,
            "{\"Id\":4,\"Content\":\"Item 4\"}" ,
            "{\"Id\":5,\"Content\":\"Item 5\"}" ,
            "{\"Id\":6,\"Content\":\"Item 6\"}" ,
            "{\"Id\":7,\"Content\":\"Item 7\"}" ,
            "{\"Id\":8,\"Content\":\"Item 8\"}" ,
            "{\"Id\":9,\"Content\":\"Item 9\"}" ,
            "{\"Id\":10,\"Content\":\"Item 10\"}"
    };

    private final String nestedJsonString =
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

    private final Map<String,String> expectedNestedParsing = Map.of(
            "Inventory","[" +
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
                    "]",
            "State", "0",
            "DateTime","2023-04-10T19:36:32.2605226+00:00"
    );
}