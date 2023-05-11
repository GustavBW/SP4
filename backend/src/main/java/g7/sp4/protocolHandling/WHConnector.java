package g7.sp4.protocolHandling;


import g7.sp4.common.models.*;
import g7.sp4.util.XMLParser;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.soap.*;

@Service
public class WHConnector implements WHConnectionService {
 private static final WHStatus NO_CONNECTION_STATUS =new WHStatus("Connection Error","None", WHState.ERROR_UNKNOWN, "Unknown");

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
    public final String URL = "http://localhost:8081/Service.asmx";

    
    //autoStore()
    @Override
    public Flag autoStore(Part part) {
        try{
            int emptyTray= findEmptyTray();
            if (emptyTray==-1){
                return new Flag().setError(new Error(
                        "No empty trays",
                        "While trying autoStore part with id: "+part.getId() ));
            }
           return insertItem(emptyTray,"P:"+part.getId());


        }catch (Exception e){

            return new Flag().setError(getInsertionError("P:"+part.getId()));
        }

    }
    
    @Override
    public Flag autoStore(Component component) {
        try{
            int emptyTray= findEmptyTray();
            if (emptyTray==-1){
                return new Flag().setError(new Error(
                        "No empty trays",
                        "While trying autoStore component with id: "+component.getId() ));
            }
            return insertItem(emptyTray,"C:"+component.getId());


        }catch (Exception e){
            return new Flag().setError(getInsertionError("C:"+component.getId()));
        }
        
    }
    
    
    
    
    //WHConnector getPart(id)


    private void emptyAllContent() {

        try {
            for (int i=1;i<=10;i++){
                // Print SOAP Response
                int finalI = i;
                SOAPMessage response = sendSOAPRequest(connectToWH(),()->{
                    return pickItemPayload(finalI);
                });

            }
        }catch (Exception ignored){

        }


    }

    private SOAPMessage sendSOAPRequest(SOAPConnection connection, PayLoadCreator payLoadCreator) throws Exception {

        SOAPMessage soapResponse = connection.call(payLoadCreator.create(), URL);
        connection.close();

        return soapResponse;
    }

    private SOAPConnection connectToWH() throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        return soapConnection;

    }


    private static SOAPMessage getInventoryPayload()  {
        try{ MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
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
            return soapMessage;}catch (Exception e){return null;}

    }

    private static SOAPMessage pickItemPayload(int trayId)  {
        try{ MessageFactory messageFactory = MessageFactory.newInstance();
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

            return soapMessage;
        }catch (Exception e){
            return null;
        }

    }

    private static SOAPMessage insertItemPayload(int trayID, String item){
        try{MessageFactory messageFactory = MessageFactory.newInstance();
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

            return soapMessage;}catch (Exception e){
            return null;
        }

    }



    @Override
    public List<WHItem> getInventory() {

// Print SOAP Response
        try {
            return WHItem.fromWHRequest(XMLParser.getFieldContent(getRawInventory(),"GetInventoryResult"),true);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private String getRawInventory(){

// Print SOAP Response
        try {
            SOAPMessage response = sendSOAPRequest(connectToWH(), WHConnector::getInventoryPayload);

            return responseToString(response);
        } catch (Exception e) {
            return null;
        }

    }
    private Error getInsertionError(String item){

        return new Error("Inserting Error","Error during the insertion of: "+ item);
    }
    private Error getInventoryError(){

        return new Error(
                "Unable to fetch inventory",
                "While trying prepare Component, getInventory returned no inventory");
    }

    private Error getItemNotFound(String item){
        return new Error(
                item+" not Found",
                "An item was requested, but was not found");

    }

    private Flag getFlagStatusIsIdle(Error errorWhichMayHappen){
        return new Flag(
                (state, flag) -> {
                    WHStatus status = getStatus();
                    if (status.whState()==WHState.ERROR_UNKNOWN){
                        flag.setError(
                                errorWhichMayHappen
                        );
                    }

                    return status.whState()==WHState.IDLE;
                }
            );
    }
    private Flag insertItem(int trayID, String item)  {

        try{
            // Print SOAP Response
            SOAPMessage response = sendSOAPRequest(connectToWH(),()->{
                return insertItemPayload(trayID,item);
            });
            return getFlagStatusIsIdle(getInsertionError(item));

        }catch (Exception e){
            return new Flag().setError(
                   getInsertionError(item)
            );
        }


    }

    private Flag pickItem(int trayId){

        try {
            // Print SOAP Response
            SOAPMessage response = sendSOAPRequest(connectToWH(),()->{
                return pickItemPayload(trayId);
            });

            return getFlagStatusIsIdle(new Error("Pick Error", "Error occurred while pick an Item"));
        }catch (Exception e){
            return new Flag().setError(new Error("PickItem method","Exception error in pickItem()"));
        }


    }


    private String responseToString(SOAPMessage response) {

        try{ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.writeTo(out);
            return out.toString();}catch(Exception e){return "";}


    }


    @Override
    public WHStatus getStatus(){

        String rawInventory= getRawInventory();
        if (rawInventory==null){
            return NO_CONNECTION_STATUS;
        }

        String largerString= XMLParser.getFieldContent(rawInventory,"GetInventoryResult");
        String substring = "\"State\":";

        if (largerString==null){
            return NO_CONNECTION_STATUS;
        }
        int position = largerString.indexOf(substring);

        if (position != -1) {
            String smallerString = largerString.substring(position + substring.length(),position + substring.length()+1);
            return new WHStatus(smallerString,smallerString, WHState.valueOf(Integer.parseInt(smallerString)),new Date().toString());

        }

        return NO_CONNECTION_STATUS;
    }

    @Override
    public Flag prepareItem(Part part){
        int trayIdFound=findTrayWithItem("P",part.getId().intValue());

        if (trayIdFound==-1){
            return new Flag().setError(getItemNotFound("P:"+ part.getId()));
        }

        return pickItem(trayIdFound);
    }

    private int findEmptyTray(){
        int trayIdFound =-1;
        for(WHItem item:getInventory()){

            if(item.Content.isEmpty()){
                trayIdFound= item.Id;
                break;
            }
        }
        return trayIdFound;
    }

    private int findTrayWithItem(String prefix, int id){
        int trayIdFound =-1;
        for(WHItem item:getInventory()){
            String[] split = item.Content.split(":");
            if(split[0].equalsIgnoreCase(prefix) && split[1].equals(id+"")){
                trayIdFound= item.Id;
                break;
            }

        }
        return trayIdFound;
    }

    @Override
    public Flag prepareItem(Component component) {
      int trayIdFound=findTrayWithItem("C",component.getId().intValue());

        if (trayIdFound==-1){
           return new Flag().setError(getItemNotFound("C:"+ component.getId()));
        }
        return pickItem(trayIdFound);


    }

    @Override
    public void loadComponents(List<Component> components){
        emptyAllContent();
        if (components!=null){
        for(Component item:components){
        autoStore(item);
        }
       }
    }

    @Override
    public Flag preparePart(long id){
        int trayIdFound=findTrayWithItem("P",(int)id);

        if (trayIdFound==-1){
            return new Flag().setError(getItemNotFound("P:"+ id));
        }
        return pickItem(trayIdFound);
    }

    @Override
    public Flag prepareComponent(long id){
        int trayIdFound=findTrayWithItem("C",(int)id);
        if (trayIdFound==-1){
            return new Flag().setError(getItemNotFound("C:"+id));
        }
        return pickItem(trayIdFound);
    }


    //getPart(id)

}
