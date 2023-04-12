package g7.sp4.services;

import g7.sp4.common.models.Batch;
import org.springframework.stereotype.Service;

@Service
public class BatchService implements IBatchService{
    @Override
    public String verify(Batch batch) {

        if(batch == null || batch.isHasCompleted()) return "invalid batch";
        if(batch.getParts().isEmpty()) return "empty batch";
        if(batch.getEmployeeId().isBlank()) return "please provide id";

        return null;
    }
}
