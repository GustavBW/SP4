package g7.sp4.batchProcessing.Phases;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.Flag;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.services.IEventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public abstract class Phase {

    protected AGVConnectionService agvConnector;
    protected AssmConnectionService assmConnector;
    protected WHConnectionService whConnector;
    protected IEventLoggingService eventService;

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

    public abstract PhaseUpdateResult update(Batch batch, BatchPart currentPart);

    protected void throwErrorEvent(Flag flag, Batch batch){
        eventService.createNewEvent(
                batch,
                flag.getError().name(),
                true,
                flag.getError().description()
        );
    }
}

