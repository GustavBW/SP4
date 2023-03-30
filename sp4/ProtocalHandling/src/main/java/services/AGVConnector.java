package services;

import models.AGVStatus;

import java.net.HttpURLConnection;

public class AGVConnector implements AGVConnectionService {

    @Override
    public Flag moveToCharger() {
        return null;
    }

    @Override
    public Flag moveToAssembly() {
        return null;
    }

    @Override
    public Flag moveToWarehouse() {
        return null;
    }

    @Override
    public Flag putItemAtAssembly() {
        return null;
    }

    @Override
    public AGVStatus getStatus() {
        return null;
    }

    @Override
    public Flag putItemAtWarehouse() {
        return null;
    }

    @Override
    public Flag pickupAtWarehouse() {
        return null;
    }

    @Override
    public Flag pickUpAtAssembly() {
        return null;
    }
}
