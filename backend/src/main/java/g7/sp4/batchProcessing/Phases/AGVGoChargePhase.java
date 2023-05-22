package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.protocolHandling.Flag;

public class AGVGoChargePhase extends Phase{
    private int stateTracker = 0;
    private Flag agvGoChargeFlag;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {

        switch (stateTracker) {
            case 0 -> {
                if(agvGoChargeFlag == null) {
                    eventService.createNewEvent(
                            batch,
                            "AGV going to charge",
                            false,
                            "The AGV is on way to charging station."
                    );
                    agvGoChargeFlag = agvConnector.moveToCharger();
                }

                if(agvGoChargeFlag.get()){
                    stateTracker++;
                }

                if(agvGoChargeFlag.hasError()) {
                    throwErrorEvent(agvGoChargeFlag, batch);
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 1 -> {
                return new PhaseUpdateResult(true, false);
            }
        }
        return PhaseUpdateResult.FALSE_FALSE;
    }


//        return new PhaseUpdateResult(true, false);
    }

