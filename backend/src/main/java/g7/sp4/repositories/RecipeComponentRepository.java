package g7.sp4.repositories;

import g7.sp4.common.models.RecipeComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeComponentRepository extends JpaRepository<RecipeComponent, Long> {
}
