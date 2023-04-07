package g7.sp4.services;

import g7.sp4.common.models.Batch;
import g7.sp4.common.models.BatchEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IEventLoggingService {

    BatchEvent createNewEvent(Batch batch, String name, boolean faulty, float progression, String description);

    BatchEvent getNewest(Batch batch);

    List<BatchEvent> getForBatch(Batch batch);

    List<BatchEvent> getNewestForEachBatch(int count);

    void deleteEvent(BatchEvent event);
}
