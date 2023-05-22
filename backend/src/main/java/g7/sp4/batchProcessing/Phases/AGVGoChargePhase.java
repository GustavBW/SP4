package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.protocolHandling.Flag;

public class AGVGoChargePhase extends Phase{

    private int stateTracker = 0;
    private Flag agvGoingToChargerFlag = null;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {

        switch (stateTracker){
            //see if the agv got enough battery to go on
            case 1 -> {
                if(agvConnector.getStatus().battery() <= 50){
                    if(agvGoingToChargerFlag == null){
                        agvGoingToChargerFlag = agvConnector.moveToCharger();
                        eventService.createNewEvent(
                                batch,
                                "AGV Battery Low",
                                false,
                                1 / 9f * (batch.getParts().indexOf(currentPart)) / batch.getParts().size(),
                                "Battery levels on the AGV require charging. The AGV is making its way to the charger now."
                        );
                    }
                }else{
                    //if so, skip this phase immediately
                    return new PhaseUpdateResult(true,false);
                }

                if(agvGoingToChargerFlag.get()){
                    stateTracker++;
                }

                if(agvGoingToChargerFlag.hasError()){
                    throwErrorEvent(agvGoingToChargerFlag,batch);
                    return new PhaseUpdateResult(false, true);
                }
            }
            case 2 -> {
                int battery = agvConnector.getStatus().battery();
                if(battery >= 99){
                    eventService.createNewEvent(
                            batch,
                            "AGV Battery Charged",
                            false,
                            1.5f / 9f * (batch.getParts().indexOf(currentPart)) / batch.getParts().size(),
                            "AGV battery has reached an acceptable level: " + battery + " percent"
                    );
                    return new PhaseUpdateResult(true, false);
                }

                if(agvGoingToChargerFlag.hasError()){
                    throwErrorEvent(agvGoingToChargerFlag,batch);
                    return new PhaseUpdateResult(false, true);
                }
            }
        }

        return PhaseUpdateResult.FALSE_FALSE;
    }
}
