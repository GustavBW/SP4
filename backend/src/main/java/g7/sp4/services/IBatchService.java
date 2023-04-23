package g7.sp4.services;

import g7.sp4.common.models.Batch;
import g7.sp4.util.responseUtil.BatchResponse;
import org.springframework.stereotype.Service;

@Service
public interface IBatchService {

    /**
     * Verifies that the batch is a valid batch.
     * @return an error message if any, else null
     */
    String verify(Batch batch);
    BatchResponse responseOf(Batch batch);

}
