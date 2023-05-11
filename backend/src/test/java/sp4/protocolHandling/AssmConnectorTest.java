package sp4.protocolHandling;

import g7.sp4.protocolHandling.AssmConnector;
import g7.sp4.protocolHandling.Flag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class AssmConnectorTest {

    private final int toleratedTimeout = 10; //seconds

    private void testResponse(int timeoutSeconds, String text, Function<Void, Flag> requestFunction){
        System.out.println("\t\t Idle within " + toleratedTimeout + " seconds test - " + text);
        Flag flag = requestFunction.apply(null);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        boolean flagState = false;
        while(timeA + timeoutSeconds * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }

        if(!flag.get()) {
            System.out.println(flag.getError().description());
            Assertions.assertTrue(flag.hasError());
            Assertions.assertNotNull(flag.getError());
        }
    }

    @BeforeAll
    static void beforeAll(){
        System.out.println("||| Testing AssmConnector ================== Testing AssmConnector |||");
    }

    //Tester unhealthy build (9999)
    @Test
    public void testUnhealthyBuild() {
        AssmConnector assmConnector = new AssmConnector();
        System.out.println("======AssmConnector testing unhealthy build======");
        testResponse(toleratedTimeout,"AssmConnector - build()",(e) -> assmConnector.build(9999));
    }

    //Tester normalt build
    @Test
    public void testBuild() {
        AssmConnector assmConnector = new AssmConnector();
        System.out.println("======AssmConnector testing healthy build======");
        testResponse(toleratedTimeout,"AssmConnector - build()",(e) -> assmConnector.build(1234));
    }
}
