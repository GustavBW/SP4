package g7.sp4.controllers;

import g7.sp4.batchProcessing.ProcessChain;
import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchEvent;
import g7.sp4.repositories.BatchRepository;
import g7.sp4.services.IBatchService;
import g7.sp4.services.IEventLoggingService;
import g7.sp4.services.IIngestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BatchController {

    @Autowired
    private BatchRepository batchRepo;
    @Autowired
    private IEventLoggingService eventService;
    @Autowired
    private IIngestService ingestService;
    @Autowired
    private IBatchService batchService;

    @PostMapping(path="/batch", produces = "application/json")
    public ResponseEntity<String> placeBatch(@RequestBody Batch batch)
    {
        String error = batchService.verify(batch);
        if(error != null)
            return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);

        batch = batchRepo.save(batch);
        ingestService.accept(new ProcessChain(batch));

        return new ResponseEntity<>("Batch Queued", HttpStatus.OK);
    }


    @GetMapping(path="/batch/{id}/events", produces="application/json")
    public ResponseEntity<List<BatchEvent>> getBatchEvents(@PathVariable Long id)
    {
        return new ResponseEntity<>(eventService.getForBatch(id), HttpStatus.OK);
    }

    @GetMapping(path="/batch/events/newest", produces="application/json")
    public ResponseEntity<List<BatchEvent>> getNewestEventForNBatches(@RequestParam int amount)
    {
        return new ResponseEntity<>(eventService.getNewestForEachBatch(amount),HttpStatus.OK);
    }

    @GetMapping(path="/batch/{id}/events/newest", produces="application/json")
    public ResponseEntity<BatchEvent> getNewestForBatch(@PathVariable long id)
    {
        return new ResponseEntity<>(eventService.getNewest(id), HttpStatus.OK);
    }

    @GetMapping(path="/batch/active", produces="application/json")
    public ResponseEntity<List<Batch>> getIncompleteBatches()
    {
        return new ResponseEntity<>(batchRepo.findByIncomplete(), HttpStatus.OK);
    }

    @GetMapping(path="/batch/inactive", produces="application/json")
    public ResponseEntity<List<Batch>> getCompleteBatches()
    {
        return new ResponseEntity<>(batchRepo.findByCompleted(), HttpStatus.OK);
    }



}
