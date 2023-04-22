package g7.sp4.controllers;

import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.Part;
import g7.sp4.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PartController {

    private final String path = "/parts";

    @Autowired
    private PartRepository partRepo;

    private static record PartResponse(long id, String name, int count, String description, List<Long> batches){}

    @GetMapping(path=path, produces="application/json")
    public ResponseEntity<List<PartResponse>> getParts()
    {
        List<Part> parts = partRepo.findAll();
        List<PartResponse> responseList = new ArrayList<>();

        for(Part part : parts){
            List<Long> batchIds = new ArrayList<>();
            for(BatchPart batch : part.getBatchParts()){
                batchIds.add(batch.getId());
            }
            responseList.add(
                    new PartResponse(part.getId(), part.getName(), part.getCount(), part.getDescription(), batchIds)
            );
        }

        return ResponseEntity.ok().body(responseList);
    }

}
