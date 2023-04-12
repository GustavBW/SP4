package g7.sp4.common.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class WHItemTest {

    @BeforeAll
    static void beforeAll(){
        System.out.println("||| Testing WHItem ================== Testing WHItem |||");
    }

    @BeforeEach
    void beforeEach(){
        System.out.println();
    }

    @Test
    void fromWHRequest() {
        System.out.println("\t\t Testing WHItem fromWHRequest");

        List<WHItem> actualResult = WHItem.fromWHRequest(xmlTestContent, true);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult.size(),expectedListResult.size());

        for(int i = 0; i < expectedListResult.size(); i++){
            Assertions.assertEquals(1, expectedListResult.get(i).compareTo(actualResult.get(i)));
        }
    }

    private final String xmlTestContent =
        "{" +
            "\"Inventory\": [" +
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
    private final List<WHItem> expectedListResult = List.of(
            new WHItem(1,"Item 1"),
            new WHItem(2,"Item 2"),
            new WHItem(3,"Item 3"),
            new WHItem(4,"Item 4"),
            new WHItem(5,"Item 5"),
            new WHItem(6,"Item 6"),
            new WHItem(7,"Item 7"),
            new WHItem(8,"Item 8"),
            new WHItem(9,"Item 9"),
            new WHItem(10,"Item 10")
    );
}