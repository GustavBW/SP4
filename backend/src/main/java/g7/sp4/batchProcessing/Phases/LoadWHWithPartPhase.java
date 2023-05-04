package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.Part;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LoadWHWithPartPhase extends Phase{

    //loading the WH with the part created by the assembler

    private int stateTracker = 0;

    private Flag agvAtWHFlag, agvHasPutDownPartFlag, whHasPickedUpPartFlag;


    @Autowired
    private AGVConnectionService agvConnector;
    @Autowired
    private WHConnectionService whConnector;
    @Autowired
    private PartRepository partRepo;
    
    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {
        switch (stateTracker) {
            case 0 -> { // assure that the AGV is at the WH
                if(agvAtWHFlag == null){
                    agvAtWHFlag = agvConnector.moveToWarehouse();
                }

                if(agvAtWHFlag.get()){
                    stateTracker++;
                }

                if(agvAtWHFlag.hasError()){
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 1 -> { //now put forth the part on the agv
                if(agvHasPutDownPartFlag == null){
                    agvHasPutDownPartFlag = agvConnector.putItemAtWarehouse();
                }

                if(agvHasPutDownPartFlag.get()){
                    stateTracker++;
                }

                if(agvHasPutDownPartFlag.hasError()) {
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 2 -> { //pickup the item
                if(whHasPickedUpPartFlag == null){
                    Part asPart = partRepo.findById(currentPart.getId()).orElse(null);
                    if(asPart == null){
                        //cannot store a part that does not exist
                        return new PhaseUpdateResult(false, true);
                    }else{
                        whHasPickedUpPartFlag = whConnector.autoStore(asPart);
                    }
                }

                if(whHasPickedUpPartFlag.get()){
                    stateTracker++;
                }

                if(whHasPickedUpPartFlag.hasError()){
                    //some error occurred, abort.
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 3 -> { //and we're done.
                return new PhaseUpdateResult(true, false);
            }
        }

        return new PhaseUpdateResult(false, false);
    }
}
