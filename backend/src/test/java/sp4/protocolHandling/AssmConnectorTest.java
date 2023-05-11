package sp4.protocolHandling;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import g7.sp4.common.models.AssmState;
import g7.sp4.common.models.AssmStatus;
import g7.sp4.protocolHandling.AGVConnector;
import g7.sp4.protocolHandling.AssmConnector;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.util.MqttJSONtoString;
import org.eclipse.paho.client.mqttv3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.function.Function;


public class AssmConnectorTest {

    private final int toleratedTimeout = 15; //seconds

    private AssmConnector connector;


    private void testResponse(int timeoutSeconds, String text, Function<Void, Flag> requestFunction){
        System.out.println("\t\t Idle within N seconds test - " + text);
        Flag flag = requestFunction.apply(null);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        boolean flagState = false;
        while(timeA + timeoutSeconds * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }

        if(!flag.get()){
            Assertions.assertTrue(flag.hasError());
            Assertions.assertNotNull(flag.getError());
        }
    }


    //Tester unhealthy build (9999)
    @Test
    public void testUnhealthyBuild() {
        System.out.println("======AssmConnector testing unhealthy build======");
        testResponse(toleratedTimeout,"AssmConnector - build()",(e) -> new AssmConnector().build(9999));
        //Assertions.assertEquals(expectedState, connector.build(9999).get());
    }

    //Tester normalt build
    @Test
    public void testBuild() throws InterruptedException {
        //connector = new AssmConnector();
        //boolean expectedState = true;
        //boolean state = false;

        //while(state != true) {
        //    state = connector.build(1234).get();
        //}
        //Assertions.assertEquals(expectedState, state);
        System.out.println("======AssmConnector testing healthy build======");
        testResponse(toleratedTimeout,"AssmConnector - build()",(e) -> new AssmConnector().build(1234));
    }
}
