package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.protocolHandling.Flag;

public class TransportToAssemblerPhase extends Phase{

    private int stateTracker = 0;
    private Flag agvTransportingToAssm = null;

    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {

        if(agvTransportingToAssm == null){
            agvTransportingToAssm = agvConnector.moveToAssembly();
            eventService.createNewEvent(
                    batch,
                    "Transporting Components to Assembler",
                    false,
                    (2f / 9f) * batch.getParts().indexOf(currentPart) / batch.getParts().size(),
                    "The AGV is transporting the components from the Warehouse to the Assembler."
            );
        }

        if(agvTransportingToAssm.hasError()){
            throwErrorEvent(agvTransportingToAssm,batch);

        }

        if(agvTransportingToAssm.get()){
            return new PhaseUpdateResult(true,false);
        }

        return PhaseUpdateResult.FALSE_FALSE;
    }
}
