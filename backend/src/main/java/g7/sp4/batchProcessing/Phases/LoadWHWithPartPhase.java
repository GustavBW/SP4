package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.Part;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.repositories.PartRepository;
import g7.sp4.services.IEventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoadWHWithPartPhase extends Phase{

    //loading the WH with the part created by the assembler

    private int stateTracker = 0;

    private Flag agvAtWHFlag, agvHasPutDownPartFlag, whHasPickedUpPartFlag;

    private float progression = -1;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {
        switch (stateTracker) {
            case 0 -> { // assure that the AGV is at the WH
                if(agvAtWHFlag == null){
                    //locally deriving the current progression. Since this is the last phase for each part,
                    //the progression is just what fraction of parts has been gone through
                    eventService.createNewEvent(
                            batch,
                            "Storing Assembled Part",
                            false,
                            (8f / 9f) * (batch.getParts().indexOf(currentPart) + 1) / batch.getParts().size(),
                            "Assuring that the AGV is in the right spot to leave the newly assembled Part."
                    );
                    agvAtWHFlag = agvConnector.moveToWarehouse();
                }

                if(agvAtWHFlag.get()){
                    stateTracker++;
                }

                if(agvAtWHFlag.hasError()){
                    throwErrorEvent(agvAtWHFlag, batch);
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 1 -> { //now put forth the part from the agv
                if(agvHasPutDownPartFlag == null){
                    eventService.createNewEvent(
                            batch,
                            "Storing Assembled Part",
                            false,
                            (8.33f / 9f) * (batch.getParts().indexOf(currentPart) + 1) / batch.getParts().size(),
                            "The AGV is currently leaving the item at the Warehouse."
                    );
                    agvHasPutDownPartFlag = agvConnector.putItemAtWarehouse();
                }

                if(agvHasPutDownPartFlag.get()){
                    stateTracker++;
                }

                if(agvHasPutDownPartFlag.hasError()) {
                    throwErrorEvent(agvHasPutDownPartFlag, batch);
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 2 -> { //pickup the item
                if(whHasPickedUpPartFlag == null){
                    Part asPart = partRepo.findById(currentPart.getId()).orElse(null);
                    if(asPart == null){
                        //cannot store a part that does not exist
                        eventService.createNewEvent(
                                batch,
                                "Storing Assembled Part",
                                true,
                                (8.66f / 9f) * (batch.getParts().indexOf(currentPart) + 1) / batch.getParts().size(),
                                "While trying to store the part that was assembled, no such part existed. Batch aborted."
                        );
                        return new PhaseUpdateResult(false, true);
                    }else{
                        eventService.createNewEvent(
                                batch,
                                "Storing Assembled Part",
                                false,
                                (8.66f / 9f) * (batch.getParts().indexOf(currentPart) + 1) / batch.getParts().size(),
                                "The Warehouse is currently storing the newly assembled part."
                        );
                        whHasPickedUpPartFlag = whConnector.autoStore(asPart);
                    }
                }

                if(whHasPickedUpPartFlag.get()){
                    stateTracker++;
                }

                if(whHasPickedUpPartFlag.hasError()){
                    //some error occurred, abort.
                    throwErrorEvent(whHasPickedUpPartFlag, batch);
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 3 -> { //and we're done.
                return new PhaseUpdateResult(true, false);
            }
        }
        return PhaseUpdateResult.FALSE_FALSE;
    }
}
