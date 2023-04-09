package g7.sp4;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.common.models.RecipeComponent;
import g7.sp4.repositories.*;
import g7.sp4.services.IComponentService;
import g7.sp4.services.IPartService;
import g7.sp4.services.IRecipeService;
import org.springframework.stereotype.Service;

import java.util.*;

public class DBLoader {

    //Fills the db with recipes and stuff
    private final IComponentService compService;
    private final IRecipeService recipeService;
    private final IPartService partService;

    public DBLoader(IComponentService compService, IRecipeService recipeService, IPartService partService)
    {
        this.compService =  Objects.requireNonNull(compService);
        this.recipeService = Objects.requireNonNull(recipeService);
        this.partService = Objects.requireNonNull(partService);
    }

    public void run()
    {
        System.out.println("DBLoader running");

        Recipe recipe1 = recipeService.create(null,null);

        Part part1 = partService.create("Part 1", 1, "Description for Part 1", recipe1);

        Map<Component, Integer> recipeComponents = new HashMap<>(
                Map.of(
                        compService.create("Component 1",Set.of(recipe1)),1,
                        compService.create("Component 2", Set.of(recipe1)),2
                )
        );

        recipeService.update(recipe1,part1,recipeComponents);
    }






}
