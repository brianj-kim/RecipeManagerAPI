package ca.briangroup.recipemanger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "created_at"));
    }

    @PostMapping("/recipes")
    public Recipe createRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @PutMapping("/recipes/{recipeId}")
    public Recipe updateRecipe(@PathVariable Long recipeId, @Valid @RequestBody Recipe recipeRequest) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            recipe.setCategory(recipeRequest.getCategory());
            recipe.setDescription(recipeRequest.getDescription());
            recipe.setName(recipeRequest.getName());
            return recipeRepository.save(recipe);
        }).orElseThrow(() -> new ResourceNotFoundException("RecipeId " + recipeId + " not found"));
    }

    @DeleteMapping("/recipe/{recipeId}")
    public ResponseEntity<?> deletePost(@PathVariable Long recipeId) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            recipeRepository.delete(recipe);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("RecipeId " + recipeId + " not found"));
    }
}
