package sp4.protocolHandling;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import g7.sp4.common.models.AssmState;
import g7.sp4.common.models.AssmStatus;
import g7.sp4.protocolHandling.AssmConnector;
import g7.sp4.util.MqttJSONtoString;
import org.eclipse.paho.client.mqttv3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class AssmConnectorTest {


    private AssmConnector connector;



    //Tester unhealthy build (9999)
    @Test
    public void testUnhealthyBuild() {
        connector = new AssmConnector();
        boolean expectedState = false;
        Assertions.assertEquals(expectedState, connector.build(9999).get());
    }

    //Tester normalt build
    @Test
    public void testBuild() throws InterruptedException {
        connector = new AssmConnector();
        boolean expectedState = true;
        boolean state = false;

        while(state != true) {
            state = connector.build(1234).get();
        }
        Assertions.assertEquals(expectedState, state);
    }
}
