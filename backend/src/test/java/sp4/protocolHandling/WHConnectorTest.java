package sp4.protocolHandling;

import g7.sp4.common.models.*;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.protocolHandling.WHConnector;
import org.junit.jupiter.api.*;

import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WHConnectorTest {
    Part part = new Part("NA",5,"partForTesting", new Recipe());
    Part part2 = new Part("NA",5,"partForTesting", new Recipe());
    Component component = new Component("NA");
    Component component2 = new Component("NA");

    @BeforeAll
    static void beforeAll() {
        System.out.println("||| Testing WHConnector ================== Testing WHConnector |||");
    }
    //autoStore()
    @Test
    public void testAutoStorePart(){
        WHConnector whConnector=new WHConnector();
        //empty warehouse
        whConnector.loadComponents(null);
        part.setId((long)42);
        part2.setId((long)24);

        Flag flag=whConnector.autoStore(part);
        Flag flag2=whConnector.autoStore(part2);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        while(timeA + 5 * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }
        while(timeA + 5 * 1000 > System.currentTimeMillis()){
            if(flag2.get()) break;
        }
        Assertions.assertTrue(flag.get());
        Assertions.assertTrue(flag2.get());


    }
    @Test
    public void testAutoStoreComponent(){
        WHConnector whConnector=new WHConnector();
        component.setId((long)43);
        component2.setId((long)34);


        Flag flag=whConnector.autoStore(component);
        Flag flag2=whConnector.autoStore(component2);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        while(timeA + 5 * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }
        while(timeA + 5 * 1000 > System.currentTimeMillis()){
            if(flag2.get()) break;
        }
        Assertions.assertTrue(flag.get());
        Assertions.assertTrue(flag2.get());
    }
    @Test
    public void testGetInventory(){
        WHConnector whConnector=new WHConnector();
        List<WHItem> inventory=whConnector.getInventory();


        System.out.println(inventory);
        assertTrue(inventory.get(0).Id==1);
    }
    @Test
    public void testGetStatus(){
        WHConnector whConnector=new WHConnector();
        WHStatus whStatus=whConnector.getStatus();
        //0 means that the warehouse are in the state of executing
        assertEquals(0,whStatus.whState().state);
    }
    @Test
    public void testPrepareItemPart(){
        WHConnector whConnector=new WHConnector();
        part.setId((long)42);

        Flag flag= whConnector.prepareItem(part);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        while(timeA + 5 * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }
        Assertions.assertTrue(flag.get());
    }
    @Test
    public void testPrepareItemComponent(){
        WHConnector whConnector=new WHConnector();
        component.setId((long)43);

        Flag flag= whConnector.prepareItem(component);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        while(timeA + 5 * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }
        Assertions.assertTrue(flag.get());
    }
    @Test
    public void testPrepareComponent(){
        WHConnector whConnector=new WHConnector();
        component2.setId((long)34);

        Flag flag= whConnector.prepareComponent(34);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        while(timeA + 5 * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }
        Assertions.assertTrue(flag.get());
    }
    @Test
    public void testPreparePart(){
        WHConnector whConnector=new WHConnector();
        part2.setId((long)24);

        Flag flag= whConnector.preparePart(24);
        long timeA = System.currentTimeMillis();

        //While the connection has not timed out - expect flag.get() to eventually be true;
        while(timeA + 5 * 1000 > System.currentTimeMillis()){
            if(flag.get()) break;
        }
        Assertions.assertTrue(flag.get());
    }
    

}




