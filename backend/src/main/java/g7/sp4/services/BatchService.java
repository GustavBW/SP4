package g7.sp4.services;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.Part;
import g7.sp4.repositories.RecipeRepository;
import g7.sp4.util.responseUtil.BatchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BatchService implements IBatchService{

    @Autowired
    private RecipeRepository recipeRepo;

    @Override
    public String verify(Batch batch) {

        if(batch == null || batch.isHasCompleted()) return "invalid batch";
        if(batch.getParts().isEmpty()) return "empty batch";
        if(batch.getEmployeeId().isBlank()) return "please provide id";

        for(BatchPart part : batch.getParts()){
            if(recipeRepo.findById(part.getPartId()).orElse(null) == null){
                return "No recipe available for part id: " + part.getPartId();
            }
        }

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
