package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.services.IEventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoadAGVFromAssemblerPhase extends Phase{

    private int stateTracker = 0;

    private Flag agvLoadedFromAssmFlag;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {
        switch (stateTracker) {
            case 0 -> {
                if (agvLoadedFromAssmFlag==null) {
                    eventService.createNewEvent(
                            batch,
                            "Loading AGV from Assm",
                            false,
                            (5f / 9f) * (batch.getParts().indexOf(currentPart)  + 1) / batch.getParts().size(),
                            "The AGV is being loaded at the Assembly Station"
                    );
                    agvLoadedFromAssmFlag=agvConnector.pickUpAtAssembly();
                }
                if(agvLoadedFromAssmFlag.get()) {
                    stateTracker++;
                }

                if(agvLoadedFromAssmFlag.hasError()) {
                    throwErrorEvent(agvLoadedFromAssmFlag, batch);
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
