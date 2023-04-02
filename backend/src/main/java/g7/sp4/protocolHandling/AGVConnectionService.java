package g7.sp4.protocolHandling;


import g7.sp4.common.models.AGVStatus;

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
