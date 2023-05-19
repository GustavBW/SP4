package g7.sp4.batchProcessing;

import g7.sp4.batchProcessing.Phases.DeviceResetPhase;
import g7.sp4.batchProcessing.Phases.Phase;
import g7.sp4.batchProcessing.Phases.PhaseUpdateResult;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.services.IEventLoggingService;

public class DeviceResetService {

    private Phase resetSequence;
    private AGVConnectionService agvConnector;

    public void setAgvConnector(AGVConnectionService agvConnector) {
        this.agvConnector = agvConnector;
    }

    public void setAssmConnector(AssmConnectionService assmConnector) {
        this.assmConnector = assmConnector;
    }

    public void setWhConnector(WHConnectionService whConnector) {
        this.whConnector = whConnector;
    }

    public void setEventService(IEventLoggingService eventService) {
        this.eventService = eventService;
    }

    private AssmConnectionService assmConnector;
    private WHConnectionService whConnector;
    private IEventLoggingService eventService;

    public DeviceResetService(){

    }

    public PhaseUpdateResult update(){
        if(resetSequence == null){
            this.resetSequence = getNewDeviceResetPhase();
        }
        return evaluatePhaseUpdateResult(resetSequence.update(null, null));
    }


    private PhaseUpdateResult evaluatePhaseUpdateResult(PhaseUpdateResult result){
        if(result.hasFinished() || result.fatalError()){
            this.resetSequence = null;
        }
        return result;
    }

    private Phase getNewDeviceResetPhase(){
        Phase phase = new DeviceResetPhase();
        phase.setEventService(eventService);
        phase.setAssmConnector(assmConnector);
        phase.setAgvConnector(agvConnector);
        phase.setWhConnector(whConnector);
        return phase;
    }

}
