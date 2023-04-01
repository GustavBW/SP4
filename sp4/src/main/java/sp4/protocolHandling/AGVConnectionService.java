package sp4.protocolHandling;

import sp4.common.AGVStatus;

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
