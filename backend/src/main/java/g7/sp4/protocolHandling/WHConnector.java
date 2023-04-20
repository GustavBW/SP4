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

    public void parseGetInventory(String xml){

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));

            // Get the root element
            Element root = document.getDocumentElement();

            // Get the key-value pair from the GetInventoryResult element
            NodeList list = root.getElementsByTagName("GetInventoryResult");
            String jsonString = list.item(0).getTextContent();

            // Parse the JSON string to get the Inventory array
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray inventory = jsonObject.getJSONArray("Inventory");

            // Iterate through the Inventory array and print the Id and Content values
            for (int i = 0; i < inventory.length(); i++) {
                JSONObject item = inventory.getJSONObject(i);
                int id = item.getInt("Id");
                String content = item.getString("Content");
                System.out.println("Id: " + id + ", Content: " + content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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




    @Override
    public WHItem[] getInventory() {
        return new WHItem[0];
    }

    @Override
    public Flag prepareItem() {
        return null;
    }

}
