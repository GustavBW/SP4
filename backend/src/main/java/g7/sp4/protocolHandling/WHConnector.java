package g7.sp4.protocolHandling;


import g7.sp4.common.models.*;
import g7.sp4.util.XMLParser;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.*;


public class WHConnector implements WHConnectionService {

    @FunctionalInterface
    /**
     * Functional interface
     */
    public interface PayLoadCreator{
        /**
         *
          * @return WH-method
         */
       public SOAPMessage create() throws Exception;
    }
    public String URL = "http://localhost:8081/Service.asmx";

    public static void main(String[] args) throws Exception {

        WHConnector d= new WHConnector();
        //Creating part and component for testing
        Part part = new Part("HellBlade",3,"The sword every man lust after",  new Recipe());
        Component component = new Component("steelBoard");
        //placing them in autoStore
        d.autoStore(component);
        d.autoStore(part);
        //Printing the content of inventory
// Print SOAP Response
        SOAPMessage response = d.sendSOAPRequest(d.connectToWH(),()->{
            return d.getInventoryPayload();
        });
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeTo(out);
        String sda = out.toString();
       System.out.println(sda);


       //testing getPart
        System.out.println(d.getPart(1));

        d.getStatus();


    }

    
    
    //autoStore()
    @Override
    public void autoStore(Part part) throws Exception {
      List<WHItem> inventory=getInventory();
        for (int i=0; i< inventory.size();i++){
            if (inventory.get(i).Content.isEmpty()){
                insertItem(i+1,"P:"+part.getName());
                return;
            }
        }
        System.out.println("No space in inventory");
    }
    
    @Override
    public void autoStore(Component component) throws Exception {
        List<WHItem> inventory=getInventory();
        for (int i=0; i< inventory.size();i++){
            if (inventory.get(i).Content.isEmpty()){
                insertItem(i+1,"C:"+component.getName());
                return;
            }
        }
        System.out.println("No space in inventory");
        
    }
    
    
    
    
    //WHConnector getPart(id)
    public String getPart(int trayId) throws Exception {
        List<WHItem> inventory = getInventory();
        String result =inventory.get(trayId-1).Content;
        
        if(result.isEmpty()){
            return null;
        }
        
        return result;
    }

    public void emptyAllContent() throws Exception {
        for (int i=1;i<=10;i++){
            WHConnector d= new WHConnector();
// Print SOAP Response
            int finalI = i;
            SOAPMessage response = d.sendSOAPRequest(d.connectToWH(),()->{
                return pickItemPayload(finalI);
            });
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.writeTo(out);
            String sda = out.toString();
            System.out.println(sda);

        }

    }

    public SOAPMessage sendSOAPRequest(SOAPConnection connection, PayLoadCreator payLoadCreator) throws Exception {

        SOAPMessage soapResponse = connection.call(payLoadCreator.create(), URL);
        connection.close();

        return soapResponse;
    }

    public SOAPConnection connectToWH() throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        return soapConnection;

    }


    public static SOAPMessage getInventoryPayload() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("tempuri", "http://tempuri.org/");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GetInventory", "tempuri");
        soapBodyElem.setAttribute("xmlns", "http://tempuri.org/");

        soapMessage.saveChanges();
        return soapMessage;
    }

    public static SOAPMessage pickItemPayload(int trayId) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        String envelopeURI = "http://schemas.xmlsoap.org/soap/envelope/";
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("", envelopeURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        soapBody.addNamespaceDeclaration("temp", "http://tempuri.org/");
        SOAPElement soapBodyElem = soapBody.addChildElement("PickItem", "temp");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("trayId", "temp");
        soapBodyElem1.addTextNode(Integer.toString(trayId));

        soapMessage.saveChanges();

        // Print the request message
        System.out.print("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    public static SOAPMessage insertItemPayload(int trayID, String item) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://tempuri.org/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("", serverURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("InsertItem", "");
        SOAPElement trayId = soapBodyElem.addChildElement("trayId", "");
        trayId.addTextNode(Integer.toString(trayID));
        SOAPElement name = soapBodyElem.addChildElement("name", "");
        name.addTextNode(item);

        soapMessage.saveChanges();

        return soapMessage;
    }



    @Override
    public List<WHItem> getInventory() throws Exception {
// Print SOAP Response
        WHConnector d= new WHConnector();


// Print SOAP Response
        SOAPMessage response = d.sendSOAPRequest(d.connectToWH(),()->{
            return d.getInventoryPayload();
        });



        String sda = responseToString(response);
        XMLParser sss= new XMLParser();



        List<WHItem> sdaf= WHItem.fromWHRequest(sss.getFieldContent(sda,"GetInventoryResult"),true);
        return sdaf;
    }

    public void insertItem(int trayID, String item) throws Exception {
        WHConnector d= new WHConnector();


// Print SOAP Response
        SOAPMessage response = d.sendSOAPRequest(d.connectToWH(),()->{
            return d.insertItemPayload(trayID,item);
        });

    }

    public void pickItem(int trayId) throws Exception {
        WHConnector d= new WHConnector();


// Print SOAP Response
        SOAPMessage response = d.sendSOAPRequest(d.connectToWH(),()->{
            return d.pickItemPayload(trayId);
        });

    }


    public String responseToString(SOAPMessage response) throws SOAPException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeTo(out);
        return out.toString();
    }

    @Override
    public WHStatus getStatus() throws Exception {
        // Print SOAP Response
        WHConnector d= new WHConnector();


// Print SOAP Response
        SOAPMessage response = d.sendSOAPRequest(d.connectToWH(),()->{
            return d.getInventoryPayload();
        });



        String sda = responseToString(response);
        XMLParser sss= new XMLParser();

        String largerString= sss.getFieldContent(sda,"GetInventoryResult");
        String substring = "\"State\":";
        int position = largerString.indexOf(substring);

        if (position != -1) {
            String smallerString = largerString.substring(position + substring.length(),position + substring.length()+1);
            WHStatus whStatus = new WHStatus(smallerString,smallerString, Integer.parseInt(smallerString));
            return whStatus;

        }


        WHStatus whStatus = new WHStatus("Current process","Message", 0);



        return whStatus;
    }

    @Override
    public Flag prepareItem() {
        return null;
    }

    //getPart(id)

}
/*

    private SOAPConnection connectToWH() throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        return soapConnection;
    }

 */