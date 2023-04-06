package g7.sp4.services;

import g7.sp4.batchProcessing.BatchIngest;
import g7.sp4.batchProcessing.ProcessChain;
import g7.sp4.batchProcessing.ProcessController;
import g7.sp4.common.models.Batch;
import org.springframework.stereotype.Service;

@Service
public class IngestService implements IIngestService{


    private final BatchIngest ingest = new BatchIngest();

    @Override
    public boolean accept(ProcessChain chain) {

        boolean state = ingest.accept(chain);
        if(state){
            ProcessController.onNewBatchInIngest();
        }
        return state;
    }

    @Override
    public ProcessChain recieveNext() {
        return ingest.recieveNext();
    }
}
