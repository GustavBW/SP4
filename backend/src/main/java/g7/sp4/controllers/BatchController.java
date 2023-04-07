package g7.sp4.controllers;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchEvent;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BatchController {

    @PostMapping("/order")
    public ResponseEntity<String> placeBatch(@RequestBody Batch batch)
    {
        return new ResponseEntity<>("Not implemented", HttpStatusCode.valueOf(500));
    }


    @GetMapping("/batch/{id}/events")
    public ResponseEntity<List<BatchEvent>> getBatchEvents(@PathVariable Long id)
    {
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
    }



}
