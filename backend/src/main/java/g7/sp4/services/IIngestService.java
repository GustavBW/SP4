package g7.sp4.services;

import g7.sp4.batchProcessing.ProcessChain;
import g7.sp4.common.models.Batch;
import org.springframework.stereotype.Service;

@Service
public interface IIngestService {

    boolean accept(ProcessChain chain);
    ProcessChain recieveNext();

}
