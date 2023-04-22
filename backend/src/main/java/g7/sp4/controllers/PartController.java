package g7.sp4.controllers;

import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.Part;
import g7.sp4.repositories.PartRepository;
import g7.sp4.services.IPartService;
import g7.sp4.util.responseUtil.PartResponse;
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
    @Autowired
    private IPartService partService;

    @GetMapping(path=path, produces="application/json")
    public ResponseEntity<List<PartResponse>> getParts()
    {
        List<Part> parts = partRepo.findAll();
        List<PartResponse> responseList = new ArrayList<>();

        for(Part part : parts){
            responseList.add(partService.responseOf(part));
        }

        return ResponseEntity.ok().body(responseList);
    }

}
