package g7.sp4.controllers;

import g7.sp4.common.models.Recipe;
import g7.sp4.repositories.RecipeRepository;
import g7.sp4.services.IRecipeService;
import g7.sp4.services.PartService;
import g7.sp4.util.responseUtil.PartResponse;
import g7.sp4.util.responseUtil.RecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    private IRecipeService recipeService;

    /**
     * Returns all Recipes in the database.
     * Json Example:
     * [
     *     {
     * 		"id": 1,
     * 		"part": {
     * 			"id": 1,
     * 			"name": "Drone Assembly 0",
     * 			"count": 0,
     * 			"description": "Magical Drone Assembly containing: Gyroscope, Power Distribution Board, Motor, Gyroscope.",
     * 			"batches": []
     *        },
     * 		"components": [
     *            {
     * 				"name": "Gyroscope",
     * 				"id": 14
     *            },
     *            {
     * 				"name": "Power Distribution Board",
     * 				"id": 11
     *            },
     *            {
     * 				"name": "Motor",
     * 				"id": 1
     *            },
     *            {
     * 				"name": "Gyroscope",
     * 				"id": 14
     *            }
     * 		]
     *    },
     *    ...
     * ]
     * @return
     */
    @GetMapping(path="/recipes")
    public ResponseEntity<List<RecipeResponse>> getRecipies()
    {
        List<RecipeResponse> responseList = new ArrayList<>();

        for(Recipe rec: recipeRepo.findAll()){
            responseList.add(recipeService.responseOf(rec));
        }


        return new ResponseEntity<>(responseList, HttpStatusCode.valueOf(200));
    }


}
