package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;

public class TransportToAssemblerPhase extends Phase{
    @Override
    public PhaseUpdateResult update(Batch batch, BatchPart currentPart) {
        return new PhaseUpdateResult(true, false);
    }
}
