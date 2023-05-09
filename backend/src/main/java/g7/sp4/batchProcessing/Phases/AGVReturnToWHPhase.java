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

    private Flag agvMovingToWHFLag;

    @Autowired
    private AGVConnectionService agvConnector;

    @Autowired
    private IEventLoggingService eventService;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {

    switch (stateTracker) {
        case 0 -> {
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

        case 1 -> {
            //Finish
            return new PhaseUpdateResult(true, false);
        }

    }
        return new PhaseUpdateResult(false, false);
}
}


