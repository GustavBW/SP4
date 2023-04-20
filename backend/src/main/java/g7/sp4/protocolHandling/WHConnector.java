package g7.sp4.protocolHandling;


import g7.sp4.common.models.WHItem;
import g7.sp4.common.models.WHStatus;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.*;


public class WHConnector implements WHConnectionService {

    @FunctionalInterface
    /**
     * Functional interface
     */
      private interface PayLoadCreator{
        /**
         *
          * @return WH-method
         */
        SOAPMessage create();
    }


    String url = "http://localhost:8081/Service.asmx";
    private SOAPConnection connectToWH() throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        return soapConnection;
    }
    private SOAPMessage sendSOAPRequest(SOAPConnection connection, PayLoadCreator payLoadCreator) throws Exception {

        SOAPMessage soapResponse = connection.call(payLoadCreator.create(), url);
        connection.close();

        return soapResponse;
    }


    public static void main(String[] args) throws Exception {

        WHConnector d= new WHConnector();

// Print SOAP Response

        SOAPMessage response = d.sendSOAPRequest(d.connectToWH(), ()->{
            return (SOAPMessage) d.getInventory();
        });

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeTo(out);
        String sda = out.toString();
        System.out.println(sda);
    }




    private SOAPMessage connectToWH() throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message
        String url = "http://localhost:8081/Service.asmx";
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
        soapConnection.close();
        return soapResponse;

    }


    private static SOAPMessage createSOAPRequest() throws Exception {
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

    private static SOAPMessage createSOAPRequest2() throws Exception {
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
        soapBodyElem1.addTextNode("1");

        soapMessage.saveChanges();

        // Print the request message
        System.out.print("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    private static SOAPMessage InsertItem(int trayID, String item) throws Exception {
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
    public List<WHItem> getInventory() {
        return new ArrayList<>();
    }

    @Override
    public WHStatus getStatus() {
        return null;
    }

    @Override
    public Flag prepareItem() {
        return null;
    }

}
