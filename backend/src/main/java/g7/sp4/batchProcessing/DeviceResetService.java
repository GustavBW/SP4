package g7.sp4.batchProcessing;

import g7.sp4.batchProcessing.Phases.DeviceResetPhase;
import g7.sp4.batchProcessing.Phases.Phase;
import g7.sp4.batchProcessing.Phases.PhaseUpdateResult;

public class DeviceResetService {

    private Phase resetSequence;

    public DeviceResetService(){

    }

    public PhaseUpdateResult update(){
        if(resetSequence == null){
            this.resetSequence = new DeviceResetPhase();
        }
        return evaluatePhaseUpdateResult(resetSequence.update(null, null));
    }


    private PhaseUpdateResult evaluatePhaseUpdateResult(PhaseUpdateResult result){
        if(result.hasFinished() || result.fatalError()){
            this.resetSequence = null;
        }
        return result;
    }

}
