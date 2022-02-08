package ca.briangroup.recipemanger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @GetMapping("recipes/{recipeId}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long recipeId) {
        Optional<Recipe> recipeData = recipeRepository.findById(recipeId);

        if(recipeData.isPresent()) {
            return new ResponseEntity<>(recipeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/recipes")
    public Recipe createRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @PutMapping("/recipes/{recipeId}")
    public Recipe updateRecipe(@PathVariable Long recipeId, @Valid @RequestBody Recipe recipeRequest) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            recipe.setIngredientCount(recipeRequest.getIngredientCount());
            recipe.setCostTotal(recipeRequest.getCostTotal());
            recipe.setCategory(recipeRequest.getCategory());
            recipe.setDescription(recipeRequest.getDescription());
            recipe.setName(recipeRequest.getName());
            return recipeRepository.save(recipe);
        }).orElseThrow(() -> new ResourceNotFoundException("RecipeId " + recipeId + " not found"));
    }

    @PutMapping("/recipe/{recipeId}/costs/{costs}")
    public void updateRecipeCostTotal(@PathVariable(value = "recipeId") Long recipeId, @PathVariable(value = "costs") BigDecimal costTotal) {
        recipeRepository.updateCostTotal(recipeId, costTotal);
    }

    @PutMapping("/recipe/{recipeId}/counts/{counts}")
    public void updateIngredientsCount(@PathVariable(value = "recipeId") Long recipeId, @PathVariable(value = "counts") Integer counts) {
        recipeRepository.updateIngredientCount(recipeId, counts);
    }

    @DeleteMapping("/recipes/{recipeId}")
    public ResponseEntity<?> deletePost(@PathVariable Long recipeId) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            recipeRepository.delete(recipe);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("RecipeId " + recipeId + " not found"));
    }
}
