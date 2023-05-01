package g7.sp4.protocolHandling;

import g7.sp4.common.models.AGVState;
import g7.sp4.common.models.AGVStatus;
import g7.sp4.common.models.SystemConfigurationService;
import g7.sp4.util.IntUtil;
import g7.sp4.util.JSONWrapper;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AGVConnector implements AGVConnectionService {

    private enum Programs {
        MoveToChargerOperation("MoveToChargerOperation"),
        MoveToAssemblyOperation("MoveToAssemblyOperation"),
        MoveToStorageOperation("MoveToStorageOperation"),
        PutAssemblyOperation("PutAssemblyOperation"),
        PickAssemblyOperation("PickAssemblyOperation"),
        PickWarehouseOperation("PickWarehouseOperation"),
        PutWarehouseOperation("PutWarehouseOperation");
        final String value;
        Programs(String value)
        {
            this.value = value;
        }
    }

    private final String baseUrl = "http://"+SystemConfigurationService.AGV_IP+":"+SystemConfigurationService.AGV_PORT+"/v1/status/";

    private void setProgram(Programs program, int state){
        // Create the HTTP connection
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl).openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Construct the request body
            String requestBody = "{\"state\":"+state+"}";
            if(program != null)
                requestBody = "{\"Program name\":\""+program.value+"\", \"state\":"+state+"}";

            // Send the request
            connection.getOutputStream().write(requestBody.getBytes());

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.err.println("PUT request failed with response code " + responseCode);
            }
            connection.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startCurrentProgram(){
        setProgram(null,2);
    }

    private Flag STATE_IS_IDLE_FLAG(){
        return new Flag(
                (bool, flag) -> {
                    AGVState state = getStatus().state();
                    if(state == AGVState.ERROR_UNKNOWN){
                        flag.setError(
                                new Error("AGV Error", "An error occurred while the AGV was operating.")
                        );
                    }
                    return state == AGVState.IDLE;
                }
        );
    }


    @Override
    public Flag moveToCharger() {
        setProgram(Programs.MoveToChargerOperation,1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG();
    }

    @Override
    public Flag moveToAssembly() {
        setProgram(Programs.MoveToAssemblyOperation,1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG();
    }

    @Override
    public Flag moveToWarehouse() {
        setProgram(Programs.MoveToStorageOperation,1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG();
    }

    @Override
    public Flag putItemAtAssembly() {
        setProgram(Programs.PutAssemblyOperation,1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG();
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
        }catch (Exception e){
            e.printStackTrace();
            bodyAsString = null;
            code = 500;
        }

        if(bodyAsString == null){
            return new AGVStatus(-1,"unknown", AGVState.ERROR_UNKNOWN, System.currentTimeMillis() + "", code);
        }

        JSONWrapper wrapped = new JSONWrapper(bodyAsString);

        return new AGVStatus(
                IntUtil.parseOr(wrapped.get("battery"),-1),
                wrapped.get("program name"),
                AGVState.valueOf(IntUtil.parseOr(wrapped.get("state"),-1)),
                wrapped.get("timestamp"),
                code
        );
    }

    @Override
    public Flag putItemAtWarehouse() {
        setProgram(Programs.PutWarehouseOperation,1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG();
    }

    @Override
    public Flag pickupAtWarehouse() {
        setProgram(Programs.PickWarehouseOperation,1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG();
    }

    @Override
    public Flag pickUpAtAssembly() {
        setProgram(Programs.PickAssemblyOperation,1);
        startCurrentProgram();
        return STATE_IS_IDLE_FLAG();
    }
}
