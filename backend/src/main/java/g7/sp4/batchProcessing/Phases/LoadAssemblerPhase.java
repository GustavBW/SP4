package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.services.IEventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;

//kenneth og nikolaj
public class LoadAssemblerPhase extends Phase{

    private int stateTracker = 0;

    private Flag agvAtAssemblerFlag, agvHasPutDownPartFlag;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {

        switch (stateTracker) {
            case 0 -> {
                //Check that the AVG have arrived at the assembly
               if (agvAtAssemblerFlag==null) {
                   agvAtAssemblerFlag = agvConnector.moveToAssembly();
               }

                if(agvAtAssemblerFlag.get()){
                    stateTracker++;
                }

                if(agvAtAssemblerFlag.hasError()) {
                    throwErrorEvent(agvAtAssemblerFlag, batch);
                    return new PhaseUpdateResult(false, true);
                }

            }
            case 1 -> {
                //make the AGV put the part or component on the assembler
                if(agvHasPutDownPartFlag == null){
                    eventService.createNewEvent(
                            batch,
                            "Placing part at assembly",
                            false,
                            (3f / 9f) * (batch.getParts().indexOf(currentPart) + 1) / batch.getParts().size(),
                            "The AGV is currently placing the part at the assembler."
                    );
                    agvHasPutDownPartFlag = agvConnector.putItemAtAssembly();
                }

                if(agvHasPutDownPartFlag.get()){
                    stateTracker++;
                }

                if(agvHasPutDownPartFlag.hasError()) {
                    throwErrorEvent(agvHasPutDownPartFlag, batch);
                    return new PhaseUpdateResult(false, true);
                }

            }
            case 2 -> {
                //Finish
                return new PhaseUpdateResult(true, false);
            }

        }
        return PhaseUpdateResult.FALSE_FALSE;
    }
}

