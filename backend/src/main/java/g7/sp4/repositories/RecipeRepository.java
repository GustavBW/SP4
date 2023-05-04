package g7.sp4.repositories;

import g7.sp4.common.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE r.partMade.id = :partId")
    List<Recipe> findByPartId(@Param("partId") long partId);

}
