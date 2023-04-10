package sp4.protocolHandling;

import g7.sp4.common.models.AGVState;
import g7.sp4.common.models.AGVStatus;
import g7.sp4.protocolHandling.AGVConnector;
import g7.sp4.protocolHandling.Flag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.function.Function;

class AGVConnectorTest {

    private final int toleratedTimeout = 15; //seconds

    @BeforeEach
    void setUp() {
        System.out.println();
    }

    @AfterEach
    void tearDown() {
        System.out.println();
    }

    private void testResponse(int timeoutSeconds, String text, Function<Void,Flag> requestFunction){
        System.out.println("\t\t Idle within N seconds test - " + text);
        Flag flag = requestFunction.apply(null);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        boolean flagState = false;
        while(timeA + timeoutSeconds * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }
        Assertions.assertTrue(flag.get());
    }

    @Test
    void moveToCharger() {
        System.out.println("======AGVConnector moveToCharger======");
        testResponse(toleratedTimeout,"AGVConnector - moveToCharger()",(e) -> new AGVConnector().moveToCharger());
    }

    @Test
    void moveToAssembly() {
        System.out.println("======AGVConnector moveToAssembly======");
        testResponse(toleratedTimeout,"AGVConnector - moveToAssembly()",(e) -> new AGVConnector().moveToAssembly());
    }

    @Test
    void moveToWarehouse() {
        System.out.println("======AGVConnector moveToWarehouse======");
        testResponse(toleratedTimeout,"AGVConnector - moveToWarehouse()",(e) -> new AGVConnector().moveToWarehouse());
    }

    @Test
    void putItemAtAssembly() {
        System.out.println("======AGVConnector putItemAtAssembly======");
        testResponse(toleratedTimeout,"AGVConnector - putItemAtAssembly()",(e) -> new AGVConnector().putItemAtAssembly());
    }

    @Test
    void getStatus() {
        System.out.println("======AGVConnector getStatus======");
        AGVConnector connector = new AGVConnector();
        AGVStatus stateInformation = connector.getStatus();

        System.out.println("\t\t Expecting successfull connection...");
        Assertions.assertEquals(stateInformation.code(), 200);

        System.out.println("\t\t Expecting valid battery state...");
        Assertions.assertNotEquals(stateInformation.battery(), -1);

        System.out.println("\t\t Expecting valid program...");
        Assertions.assertNotNull(stateInformation.programName());
        Assertions.assertFalse(stateInformation.programName().isEmpty());

        System.out.println("\t\t Expecting valid state...");
        Assertions.assertNotEquals(stateInformation.state(),AGVState.ERROR_UNKNOWN);

        System.out.println("\t\t Expecting valid timestamp...");
        Assertions.assertNotNull(stateInformation.timestamp());
        Assertions.assertFalse(stateInformation.timestamp().isEmpty());
    }

    @Test
    void putItemAtWarehouse() {
        System.out.println("======AGVConnector putItemAtWarehouse======");
        testResponse(toleratedTimeout,"AGVConnector - putItemAtWarehouse()",(e) -> new AGVConnector().putItemAtWarehouse());
    }

    @Test
    void pickupAtWarehouse() {
        System.out.println("======AGVConnector pickupAtWarehouse======");
        testResponse(toleratedTimeout,"AGVConnector - pickupAtWarehouse()",(e) -> new AGVConnector().pickupAtWarehouse());
    }

    @Test
    void pickUpAtAssembly() {
        System.out.println("======AGVConnector pickUpAtAssembly======");
        testResponse(toleratedTimeout,"AGVConnector - pickUpAtAssembly()",(e) -> new AGVConnector().pickUpAtAssembly());
    }
}