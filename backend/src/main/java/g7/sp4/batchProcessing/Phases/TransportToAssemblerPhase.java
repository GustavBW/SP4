package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.protocolHandling.Flag;

public class TransportToAssemblerPhase extends Phase{
    private int stateTracker = 0;
    private Flag agvMoveToAssemblerFlag;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {
        switch (stateTracker) {
            case 0 -> {
                if(agvMoveToAssemblerFlag == null) {
                    eventService.createNewEvent(
                            batch,
                            "AGV moving to Assembler",
                            false,
                            "The AGV is on way towards the assembler."
                    );
                    agvMoveToAssemblerFlag = agvConnector.moveToAssembly();
                }

                if(agvMoveToAssemblerFlag.get()){
                    stateTracker++;
                }

                if(agvMoveToAssemblerFlag.hasError()) {
                    throwErrorEvent(agvMoveToAssemblerFlag, batch);
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 1 -> {

                return new PhaseUpdateResult(true, false);
            }
        }
        return PhaseUpdateResult.FALSE_FALSE;
    }

}
