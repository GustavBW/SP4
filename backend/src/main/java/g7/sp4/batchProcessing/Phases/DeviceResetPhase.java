package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.AssmState;
import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.WHState;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.services.IEventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceResetPhase extends Phase{
    //As per documentation, the DeviceResetPhase, executed by the DeviceResetService, returns all devices to some "original" position.

    private int stateTracker = 0;

    private long timeStartWH = 0;
    private boolean awaitWHIdleHasBegun = false;
    private final int maxAllowedWHTimeoutSeconds = 15;

    private long timeStartAssm = 0;
    private boolean awaitAssmHasBegun = false;
    private final int maxAllowedAssmTimeoutSeconds = 15;

    private Flag moveAGVToWHFlag;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {
        switch (stateTracker) {
            case 0 -> { // reset the agv to be at the warehouse
                if(moveAGVToWHFlag == null){
                    moveAGVToWHFlag = agvConnector.moveToWarehouse();
                }

                if(moveAGVToWHFlag.get()){ //the agv is now at the warehouse.
                    stateTracker++;
                }

                if(moveAGVToWHFlag.hasError()){
                    //some fatal error that cant be automatically resolved
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 1 -> { //assure that the wh is idle
                if(!awaitWHIdleHasBegun){
                    timeStartWH = System.currentTimeMillis();
                    awaitWHIdleHasBegun = true;
                }

                if (whConnector.getStatus().whState() == WHState.IDLE){
                    stateTracker++;
                }
                //accounting for timeouts, however allowing some time for the connection to reestablish
                if(timeStartWH + maxAllowedWHTimeoutSeconds < System.currentTimeMillis()){
                    //this has taken too long, there is an error somewhere
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 2 -> { // assure that the Assembler is idle
                if(!awaitAssmHasBegun){
                    awaitAssmHasBegun = true;
                    timeStartAssm = System.currentTimeMillis();
                }

                if(assmConnector.getStatus().state() == AssmState.IDLE){
                    stateTracker++;
                }
                //accounting for timeouts, however allowing some time for the connection to reestablish
                if(timeStartAssm + maxAllowedAssmTimeoutSeconds < System.currentTimeMillis()){
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 3 -> { //and we're done
                return new PhaseUpdateResult(true, false);
            }

        }

        return PhaseUpdateResult.FALSE_FALSE;
    }

}
