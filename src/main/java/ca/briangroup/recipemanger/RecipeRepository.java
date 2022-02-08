package ca.briangroup.recipemanger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Modifying
    @Transactional
    @Query("update Recipe r set r.costTotal = :costTotal where r.id = :id")
    void updateCostTotal(@Param(value = "id") Long id, @Param(value = "costTotal") BigDecimal costTotal);

    @Modifying
    @Transactional
    @Query("update Recipe r set r.ingredientCount = :count where r.id = :id")
    void updateIngredientCount(@Param(value = "id") Long id, @Param(value = "count") Integer count);

}
