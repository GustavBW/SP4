package g7.sp4.controllers;

import models.Batch;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

    @PostMapping("/order")
    public ResponseEntity<String> placeBatch(@RequestBody Batch batch)
    {
        return new ResponseEntity<>("Not implemented", HttpStatusCode.valueOf(500));
    }



}
