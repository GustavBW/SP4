package g7.sp4.services;

import g7.sp4.common.models.BatchPart;
import g7.sp4.common.models.Component;
import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.util.responseUtil.RecipeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface IRecipeService {

    Recipe getRecipeFor(BatchPart batchPart);

    Recipe create(Part partMade, List<Component> componentsRequired);
    void delete(Recipe recipe);
    Recipe update(Recipe recipe, Part partMade, List<Component> componentsRequired);

    /**
     * Recursion safe response type
     * @param recipe
     * @return
     */
    RecipeResponse responseOf(Recipe recipe);
}


