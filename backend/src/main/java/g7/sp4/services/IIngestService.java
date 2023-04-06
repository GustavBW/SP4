package g7.sp4.services;

import g7.sp4.batchProcessing.ProcessChain;
import g7.sp4.common.models.Batch;

public interface IIngestService {

    boolean accept(ProcessChain chain);
    ProcessChain recieveNext();

}
