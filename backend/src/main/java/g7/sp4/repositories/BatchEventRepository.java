package g7.sp4.repositories;

import g7.sp4.common.models.BatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BatchEventRepository extends JpaRepository<BatchEvent, Long> {

    @Query("SELECT be FROM BatchEvent be WHERE be.batch.id = :batchId")
    List<BatchEvent> findByBatchId(@Param("batchId") Long batchId);

}
