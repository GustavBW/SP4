package g7.sp4.protocolHandling;


import g7.sp4.common.models.WHItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.*;


public class WHConnector implements WHConnectionService {
    String url = "http://localhost:8081/Service.asmx";
    public static void main(String[] args) throws Exception {

        WHConnector d = new WHConnector();
// Print SOAP Response
        SOAPConnection response = d.connectToWH();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeTo(out);

        String sda = out.toString();
        System.out.println(sda);

    }

    private static SOAPMessage getInventoryPayload() throws Exception {
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

    private static SOAPMessage pickItemPayload(int trayId) throws Exception {
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

    private static SOAPMessage createSOAPRequest3() throws Exception {
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
        trayId.addTextNode("1");
        SOAPElement name = soapBodyElem.addChildElement("name", "");
        name.addTextNode("RocketLauncher");

        soapMessage.saveChanges();

        return soapMessage;
    }

    private SOAPConnection connectToWH() throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        return soapConnection;
    }

    private SOAPMessage sendSOAPRequest(SOAPConnection connection) throws Exception {

        SOAPMessage soapResponse = connection.call(pickItemPayload(1), url);
        connection.close();

        return soapResponse;
    }

    @Override
    public List<WHItem> getInventory() {
        return new ArrayList<>();
    }

    @Override
    public Flag prepareItem() {
        return null;
    }

}
