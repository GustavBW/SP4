package g7.sp4.services;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchEvent;
import g7.sp4.repositories.BatchEventRepository;
import g7.sp4.repositories.BatchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
    @PersistenceContext
    private EntityManager entityManager;

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
     * Like createNewEvent, this makes a BatchEvent entry in the Database.
     * Unlike the normal createNewEvent, this method tries to derive the overall progression for the batch
     * from any previous events. Thusly this method should not be used on a new created batch.
     * If there is no former events for the batch, a progression value of -1f will be used.
     * @return the event as created in the db.
     */
    @Override
    public BatchEvent createNewEvent(Batch batch, String name, boolean faulty, String description){
        BatchEvent newestBeforeThis = getNewest(batch);
        float progression = newestBeforeThis == null ? -1f : newestBeforeThis.getProgression();
        return createNewEvent(batch,name,faulty,progression,description);
    }

    /**
     * Finds the newest event for a given batch
     * @param batch for which to find the newest event.
     * @return newest BatchEvent or null.
     */
    @Override
    public BatchEvent getNewest(Batch batch) {
        if(batch == null) return null;
        List<BatchEvent> batchEvents = getForBatch(batch);
        return batchEvents.stream().max(Comparator.comparingLong(BatchEvent::getTimestamp))
                .orElse(null);
    }

    @Override
    public BatchEvent getNewest(long id) {
        return getNewest(batchRepository.findById(id).orElse(null));
    }

    @Override
    public List<BatchEvent> getForBatch(Batch batch) {
        return eventRepository.findByBatchId(batch.getId());
    }

    @Override
    public List<BatchEvent> getForBatch(long id) {
        return eventRepository.findByBatchId(id);
    }

    /**
     * Finds the newest Events for each Batch, one for each.
     * @param count How many batches should be included.
     * @return A list of the events
     */
    @Override
    public List<BatchEvent> getNewestForEachBatch(int count) {
        List<BatchEvent> eventsFound = new ArrayList<>();

        TypedQuery<Batch> query = entityManager.createQuery("SELECT b FROM Batch b ORDER BY b.id", Batch.class);
        query.setMaxResults(count);
        List<Batch> allBatches =  query.getResultList();

        for (int i = 0; i < allBatches.size(); i++){
            eventsFound.add(getNewest(allBatches.get(i)));
        }

        return eventsFound;
    }

    @Override
    public void deleteEvent(BatchEvent event) {
        eventRepository.delete(event);
    }
}
