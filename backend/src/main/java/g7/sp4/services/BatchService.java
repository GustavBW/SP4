package g7.sp4.services;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.Part;
import g7.sp4.repositories.PartRepository;
import g7.sp4.repositories.RecipeRepository;
import g7.sp4.util.responseUtil.BatchResponse;
import g7.sp4.util.responseUtil.PartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BatchService implements IBatchService{

    @Autowired
    private RecipeRepository recipeRepo;
    @Autowired
    private PartRepository partRepo;
    @Autowired
    private IPartService partService;

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
        Map<PartResponse, Integer> partCountMap = new HashMap<>();
        List<Long> partIds = new ArrayList<>();
        for(BatchPart part : batch.getParts()){
            partIds.add(part.getPartId());
        }

        List<Part> asParts = partRepo.findAllById(partIds);
        for(Part part : asParts){
            partCountMap.put(partService.responseOf(part), part.getCount());

        }

        return new BatchResponse(batch.getId(), batch.isHasCompleted(), batch.getEmployeeId(), partCountMap);
    }
}
