package sp4.protocolHandling;

import g7.sp4.protocolHandling.AGVConnector;
import g7.sp4.protocolHandling.Flag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AGVConnectorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void moveToCharger() {
        System.out.println("\t\tAGVConnector - Testing moveToCharger()");
        Flag flag = new AGVConnector().moveToCharger();
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        boolean flagState = false;
        while(timeA + 5 * 1000 < System.currentTimeMillis()){
            if(flag.get()) break;
        }
        Assertions.assertTrue(flag.get());
    }

    @Test
    void moveToAssembly() {
    }

    @Test
    void moveToWarehouse() {
    }

    @Test
    void putItemAtAssembly() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void putItemAtWarehouse() {
    }

    @Test
    void pickupAtWarehouse() {
    }

    @Test
    void pickUpAtAssembly() {
    }
}