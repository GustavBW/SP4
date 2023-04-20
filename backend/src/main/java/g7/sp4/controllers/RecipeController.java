package g7.sp4.controllers;

import g7.sp4.common.models.Recipe;
import g7.sp4.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecipeController {

    @GetMapping(path="/recipes")
    public ResponseEntity<List<Recipe>> getRecipies()
    {
        return new ResponseEntity<>(recipeRepo.findAll(), HttpStatusCode.valueOf(200));
    }
    @Autowired
    private RecipeRepository recipeRepo;

}
