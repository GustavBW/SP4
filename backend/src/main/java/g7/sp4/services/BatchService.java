package g7.sp4.services;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.util.responseUtil.BatchResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BatchService implements IBatchService{
    @Override
    public String verify(Batch batch) {

        if(batch == null || batch.isHasCompleted()) return "invalid batch";
        if(batch.getParts().isEmpty()) return "empty batch";
        if(batch.getEmployeeId().isBlank()) return "please provide id";

        return null;
    }

    public BatchResponse responseOf(Batch batch){
        Map<Long, Integer> partCountMap = new HashMap<>();

        for(BatchPart part : batch.getParts()){
            partCountMap.put(part.getPartId(), part.getCount());
        }

        return new BatchResponse(batch.getId(), batch.isHasCompleted(), batch.getEmployeeId(), partCountMap);
    }
}
