package g7.sp4.services;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.common.models.RecipeComponent;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface IRecipeService {

    Recipe create(Part partMade, Map<Component,Integer> componentsRequired);
    void delete(Recipe recipe);
    Recipe update(Recipe recipe, Part partMade, Map<Component, Integer> componentsRequired);

}
