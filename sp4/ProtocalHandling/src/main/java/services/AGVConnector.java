package services;

import models.AGVState;
import models.AGVStatus;
import models.SystemConfigurationService;
import util.IntUtil;
import util.JSONWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AGVConnector implements AGVConnectionService {

    private enum Programs {
        MoveToChargerOperation,MoveToAssemblyOperation,MoveToStorageOperation,PutAssemblyOperation,PickAssemblyOperation,PickWarehouseOperation,PutWarehouseOperation;
    }

    private final String baseUrl = "http://"+SystemConfigurationService.AGV_IP+":"+SystemConfigurationService.AGV_PORT+"/v1/status/";

    private void setProgram(String operationName, int state){
        // Create the HTTP connection
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Construct the request body
            String requestBody = String.format("{\"Program name\":\"%s\", \"state\":\"%d\"}", operationName, state);


            // Send the request
            connection.getOutputStream().write(requestBody.getBytes());

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("PUT request failed with response code " + responseCode);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startCurrentProgram(){
        setProgram("",2);
    }

    private final Flag STATE_IS_IDLE_FLAG = new Flag((bool) -> getStatus().state() == AGVState.IDLE);


    @Override
    public Flag moveToCharger() {
        setProgram(Programs.MoveToChargerOperation.name(),1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG;
    }

    @Override
    public Flag moveToAssembly() {
        setProgram(Programs.MoveToAssemblyOperation.name(),1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG;
    }

    @Override
    public Flag moveToWarehouse() {
        setProgram(Programs.MoveToStorageOperation.name(),1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG;
    }

    @Override
    public Flag putItemAtAssembly() {
        setProgram(Programs.PutAssemblyOperation.name(),1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG;
    }

    @Override
    public AGVStatus getStatus() {
        String bodyAsString;
        int code = 200;
        try {
            // Create the HTTP connection
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("GET request failed with response code " + responseCode);
            }

            // Read the response body
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();
            bodyAsString = responseBody.toString();
        }catch (IOException e){
            e.printStackTrace();
            bodyAsString = null;
            code = 500;
        }

        JSONWrapper wrapped = new JSONWrapper(bodyAsString);

        return new AGVStatus(
                IntUtil.parseOr(wrapped.get("Battery"),-1),
                wrapped.get("Program name"),
                AGVState.valueOf(IntUtil.parseOr(wrapped.get("State"),-1)),
                wrapped.get("TimeStamp"),
                code
        );
    }

    @Override
    public Flag putItemAtWarehouse() {
        setProgram(Programs.PutWarehouseOperation.name(),1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG;
    }

    @Override
    public Flag pickupAtWarehouse() {
        setProgram(Programs.PickWarehouseOperation.name(),1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG;
    }

    @Override
    public Flag pickUpAtAssembly() {
        setProgram(Programs.PickAssemblyOperation.name(),1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG;
    }
}
