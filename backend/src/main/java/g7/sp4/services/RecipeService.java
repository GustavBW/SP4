package g7.sp4.services;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.common.models.RecipeComponent;
import g7.sp4.repositories.RecipeComponentRepository;
import g7.sp4.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService implements IRecipeService{

    @Autowired
    private RecipeComponentRepository recCompRepo;
    @Autowired
    private RecipeRepository recipeRepo;

    /**
     * Creates a new Recipe and stores it in the DB.
     * @param partMade the part that is made using this recipe
     * @param componentsRequired the components that are needed and the amount of which in a Map
     * @return the new recipe as stored in the db
     */
    @Override
    public Recipe create(Part partMade, Map<Component, Integer> componentsRequired) {
        Recipe newRecipe = new Recipe(partMade,null);
        if(componentsRequired != null) {
            List<RecipeComponent> asSavedSet = recCompRepo.saveAll(fromMapToSet(componentsRequired, newRecipe));
            newRecipe.setComponentsRequired(new HashSet<>(asSavedSet));
        }
        return recipeRepo.save(newRecipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recCompRepo.deleteAll(recipe.getComponentsRequired());
        recipeRepo.delete(recipe);
    }

    @Override
    public Recipe update(Recipe recipe, Part partMade, Map<Component, Integer> componentsRequired) {
        if(partMade != null)
            recipe.setPartMade(partMade);
        if(componentsRequired != null && !componentsRequired.isEmpty()){
            List<RecipeComponent> asSavedList = recCompRepo.saveAll(fromMapToSet(componentsRequired, recipe));
            recipe.setComponentsRequired(new HashSet<>(asSavedList));
        }
        return recipeRepo.save(recipe);
    }

    private Set<RecipeComponent> fromMapToSet(Map<Component,Integer> componentCountMap, Recipe recipe)
    {
        Set<RecipeComponent> asRecipeComponents = new HashSet<>();
        for(Component c : componentCountMap.keySet())
            asRecipeComponents.add(new RecipeComponent(c,recipe,componentCountMap.get(c)));
        return asRecipeComponents;
    }
}
