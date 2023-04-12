package g7.sp4.services;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchEvent;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IEventLoggingService {

    BatchEvent createNewEvent(Batch batch, String name, boolean faulty, float progression, String description);
    BatchEvent createNewEvent(Batch batch, String name, boolean faulty, String description);

    BatchEvent getNewest(Batch batch);
    BatchEvent getNewest(long id);

    List<BatchEvent> getForBatch(Batch batch);
    List<BatchEvent> getForBatch(long id);

    List<BatchEvent> getNewestForEachBatch(int count);

    void deleteEvent(BatchEvent event);
}
