package g7.sp4.protocolHandling;


import g7.sp4.common.models.SystemConfigurationService;
import g7.sp4.common.models.WHItem;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.xml.soap.*;



public class WHConnector implements WHConnectionService{
    public static void main(String[] args) throws Exception {

        WHConnector d= new WHConnector();

// Print SOAP Response
        SOAPMessage response = d.connectToWH();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.writeTo(out);

        String sda= out.toString();
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


    @Override
    public WHItem[] getInventory() {
        return new WHItem[0];
    }

    @Override
    public Flag prepareItem() {
        return null;
    }

}
