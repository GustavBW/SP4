package g7.sp4.controllers;

import g7.sp4.common.models.Part;
import g7.sp4.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PartController {

    private final String path = "/parts";

    @Autowired
    private PartRepository partRepo;

    @GetMapping(path=path)
    public ResponseEntity<List<Part>> getParts()
    {
        return new ResponseEntity<>(partRepo.findAll(), HttpStatusCode.valueOf(200));
    }

}
