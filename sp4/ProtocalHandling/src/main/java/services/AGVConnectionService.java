package services;

import models.AGVStatus;

public interface AGVConnectionService {

    Flag moveToCharger();
    Flag moveToAssembly();
    Flag moveToWarehouse();
    Flag putItemAtAssembly();
    AGVStatus getStatus();
    Flag putItemAtWarehouse();
    Flag pickupAtWarehouse();
    Flag pickUpAtAssembly();

}
