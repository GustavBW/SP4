package g7.sp4.services;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.repositories.ComponentRepository;
import g7.sp4.repositories.RecipeRepository;
import g7.sp4.util.responseUtil.ComponentResponse;
import g7.sp4.util.responseUtil.RecipeResponse;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService implements IRecipeService{


    @Autowired
    private RecipeRepository recipeRepo;
    @Autowired
    private ComponentRepository compRepo;
    @Autowired
    private IPartService partService;
    @Autowired
    private ComponentService compService;

    /**
     * Creates a new Recipe and stores it in the DB.
     * @param partMade the part that is made using this recipe
     * @param componentsRequired the components that are needed and the amount of which in a Map
     * @return the new recipe as stored in the db
     */
    @Override
    public Recipe create(Part partMade, Iterable<Component> componentsRequired) {
        Recipe newRecipe = new Recipe(partMade,null);
        if(componentsRequired != null) {
            List<Component> asSavedSet = compRepo.saveAll(componentsRequired);
            newRecipe.setComponentsRequired(new ArrayList<>(asSavedSet));
        }
        return recipeRepo.save(newRecipe);
    }

    @Override
    public void delete(Recipe recipe) {
        compRepo.deleteAll(recipe.getComponentsRequired());
        recipeRepo.delete(recipe);
    }

    @Override
    public Recipe update(Recipe recipe, Part partMade, List<Component> componentsRequired) {
        if(partMade != null)
            recipe.setPartMade(partMade);
        if(componentsRequired != null && !componentsRequired.isEmpty()){
            List<Component> asSavedList = compRepo.saveAll(componentsRequired);
            recipe.setComponentsRequired(asSavedList);
        }
        return recipeRepo.save(recipe);
    }

    @Override
    public RecipeResponse responseOf(Recipe recipe){
        List<ComponentResponse> componentsAsList = new ArrayList<>();

        for(Component comp: recipe.getComponentsRequired()){
            componentsAsList.add(
                    compService.responseOf(comp)
            );
        }

        return new RecipeResponse(
                recipe.getId(), partService.responseOf(recipe.getPartMade()), componentsAsList
        );
    }
}
