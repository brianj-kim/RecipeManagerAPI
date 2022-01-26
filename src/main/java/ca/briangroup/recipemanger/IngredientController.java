package ca.briangroup.recipemanger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/recipes/{recipeId}/ingredients")
    public List<Ingredient> getAllIngredientByRecipeId(@PathVariable (value = "recipeId") Long recipeId) {
        return ingredientRepository.findByRecipeId(recipeId);
    }

    @PostMapping("/recipes/{recipeId}/ingredients")
    public Ingredient createIngredient(@PathVariable (value = "recipeId") Long recipeId, @Valid @RequestBody Ingredient ingredient) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            ingredient.setRecipe(recipe);
            return ingredientRepository.save(ingredient);
        }).orElseThrow(() -> new ResourceNotFoundException("Recipe id " + recipeId + " not found"));
    }

    @PutMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public Ingredient updateIngredient(@PathVariable (value = "recipeId") Long recipeId,
                                       @PathVariable (value = "ingredientId") Long ingredientId,
                                       @Valid @RequestBody Ingredient ingredientRequest) {
        if(!recipeRepository.existsById(recipeId)) {
            throw new ResourceNotFoundException("Reciep id " + recipeId + " not found");
        }

        return ingredientRepository.findById(ingredientId).map(ingredient -> {
            ingredient.setIngredient(ingredientRequest.getIngredient());
            ingredient.setDescription(ingredientRequest.getDescription());
            ingredient.setQuantity(ingredientRequest.getQuantity());
            ingredient.setUnit(ingredientRequest.getUnit());
            ingredient.setLoss(ingredientRequest.getLoss());
            ingredient.setUnitPrice(ingredientRequest.getUnitPrice());
            ingredient.setPortionPrice(ingredientRequest.getPortionPrice());

            return ingredientRepository.save(ingredient);
        }).orElseThrow(() -> new ResourceNotFoundException("Ingredient id " + ingredientId + " not found "));
    }

    @DeleteMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<?> deleteIngredient(@PathVariable (value = "recipeId") Long recipeId,
                                              @PathVariable (value = "ingredientId") Long ingredientId) {
        return ingredientRepository.findByIdAndRecipeId(ingredientId, recipeId).map(ingredient -> {
            ingredientRepository.delete(ingredient);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id " + ingredientId + " and recipeId " + recipeId ));
    }

}
