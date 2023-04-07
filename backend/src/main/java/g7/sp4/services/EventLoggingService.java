package g7.sp4.services;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchEvent;
import g7.sp4.repositories.BatchEventRepository;
import g7.sp4.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class EventLoggingService implements IEventLoggingService{

    @Autowired
    private BatchEventRepository eventRepository;
    @Autowired
    private BatchRepository batchRepository;

    /**
     * Creates a new event in DB with the timestamp: now (ms).
     * @param batch
     * @param name
     * @param faulty
     * @param progression
     * @param description
     */
    @Override
    public BatchEvent createNewEvent(Batch batch, String name, boolean faulty, float progression, String description) {
        return eventRepository.save(
                new BatchEvent(batch, name, description, faulty, progression, System.currentTimeMillis())
        );
    }

    /**
     * Finds the newest event for a given batch
     * @param batch for which to find the newest event.
     * @return newest BatchEvent or null.
     */
    @Override
    public BatchEvent getNewest(Batch batch) {
        List<BatchEvent> batchEvents = getForBatch(batch);
        return batchEvents.stream().max(Comparator.comparingLong(BatchEvent::getTimestamp))
                .orElse(null);
    }

    @Override
    public List<BatchEvent> getForBatch(Batch batch) {
        return eventRepository.findByBatchId(batch.getId());
    }

    @Override
    public List<BatchEvent> getNewestForEachBatch(int count) {
        List<BatchEvent> eventsFound = new ArrayList<>();
        for (Batch b : batchRepository.findAll()){
            eventsFound.add(getForBatch(b));
        }
        //findAll
        //foreach batch
        //find all events for batch
        //sort on timestamp
        //add newest to external list

        return null;
    }

    @Override
    public void deleteEvent(BatchEvent event) {
        eventRepository.delete(event);
    }
}
