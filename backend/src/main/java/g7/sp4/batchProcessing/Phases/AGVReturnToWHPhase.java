package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.services.IEventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;

//kenneth og nikolaj
public class AGVReturnToWHPhase extends Phase{

    private int stateTracker = 0;

    private Flag agvMovingToWHFLag, agvPickingUpAtAssmFlag;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {

        switch (stateTracker) {
            case 0 -> {
                //picking up at assembler
                if(agvPickingUpAtAssmFlag == null) {
                    eventService.createNewEvent(
                            batch,
                            "AGV Picking Up Assembled Part",
                            false,
                            (float) (batch.getParts().indexOf(currentPart) + .75) / batch.getParts().size(),
                            "The AGV has awaited the assembly process and is now picking up the part."
                    );
                    agvPickingUpAtAssmFlag = agvConnector.pickUpAtAssembly();
                }

                if(agvPickingUpAtAssmFlag.get()){
                    stateTracker++;
                }


                if(agvMovingToWHFLag.hasError()) {
                    throwErrorEvent(agvMovingToWHFLag, batch);
                    return new PhaseUpdateResult(false, true);
                }

            }
            case 1 -> {
                // Moving the agv to the warehouse
                if (agvMovingToWHFLag==null){

                    eventService.createNewEvent(
                            batch,
                            "Moving AGV towards WH",
                            false,
                            "The AGV is underway towards the Warehouse."
                    );
                    agvMovingToWHFLag=agvConnector.moveToWarehouse();
                }


                if(agvMovingToWHFLag.get()){
                    stateTracker++;
                }

                if(agvMovingToWHFLag.hasError()) {
                    throwErrorEvent(agvMovingToWHFLag, batch);
                    return new PhaseUpdateResult(false, true);
                }

            }

            case 2 -> {
                //Finish
                return new PhaseUpdateResult(true, false);
            }

        }
            return new PhaseUpdateResult(false, false);
    }
}


