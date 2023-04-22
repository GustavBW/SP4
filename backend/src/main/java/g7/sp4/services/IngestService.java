package g7.sp4.services;

import g7.sp4.batchProcessing.BatchIngest;
import g7.sp4.batchProcessing.ProcessChain;
import g7.sp4.batchProcessing.ProcessController;
import g7.sp4.common.models.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngestService implements IIngestService{

    private final BatchIngest ingest = new BatchIngest();

    @Autowired
    private IEventLoggingService eventService;

    @Override
    public boolean accept(ProcessChain chain) {

        boolean state = ingest.accept(chain);
        if(state){

            eventService.createNewEvent(
                    chain.getBatch(),
                    "Batch Accepted In Ingest",
                    false,
                    0.0f,
                    "The Batch has been successfully recieved and is queued in the ingest."
            );

            ProcessController.onNewBatchInIngest();
        }
        return state;
    }

    @Override
    public ProcessChain recieveNext() {
        return ingest.recieveNext();
    }
}
