package g7.sp4;

import g7.sp4.common.models.Component;
import g7.sp4.common.models.Part;
import g7.sp4.common.models.Recipe;
import g7.sp4.common.models.RecipeComponent;
import g7.sp4.repositories.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class DBLoader {

    //Fills the db with recipes and stuff
    private final ComponentRepository compRepo;
    private final RecipeRepository recipeRepo;
    private final PartRepository partRepo;
    private final RecipeComponentRepository recCompRepo;

    public DBLoader(ComponentRepository compRepo, RecipeRepository recipeRepo, PartRepository partRepo,RecipeComponentRepository recCompRepo)
    {
        this.compRepo =  Objects.requireNonNull(compRepo);
        this.recipeRepo = Objects.requireNonNull(recipeRepo);
        this.partRepo = Objects.requireNonNull(partRepo);
        this.recCompRepo = Objects.requireNonNull(recCompRepo);
    }

    public void run()
    {
        System.out.println("DBLoader running");

        Component comp1 = new Component();
        comp1.setName("Component 1");
        comp1 = compRepo.save(comp1);

        Recipe recipe1 = recipeRepo.save(new Recipe());

        Part part1 = new Part();
        part1.setCount(1);
        part1.setName("Part 1");
        part1.setRecipe(recipe1);
        part1.setDescription("Description for Part 1");
        part1 = partRepo.save(part1);

        recipe1.setPartMade(part1);
        recipe1 = recipeRepo.save(recipe1);

        RecipeComponent recComp1 = new RecipeComponent();
        recComp1.setRecipe(recipe1);
        recComp1.setComponent(comp1);
        recComp1.setCount(1);
        recComp1 = recCompRepo.save(recComp1);

        recipe1.setComponentsRequired(new HashSet<>(Set.of(recComp1)));
        recipeRepo.save(recipe1);
    }






}
