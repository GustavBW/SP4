package g7.sp4.repositories;

import g7.sp4.common.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    @Query("SELECT b FROM Batch b WHERE b.hasCompleted = true")
    List<Batch> findCompletedBatches();
}
