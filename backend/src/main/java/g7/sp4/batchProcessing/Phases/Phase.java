package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import org.springframework.stereotype.Service;

@Service
public abstract class Phase {

    public abstract PhaseUpdateResult update(Batch batch, BatchPart currentPart);
}

