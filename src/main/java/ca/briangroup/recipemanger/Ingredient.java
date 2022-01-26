package ca.briangroup.recipemanger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Ingredient extends AuditModel {
    @Id
    @Column(name = "ingredient_id")
    @SequenceGenerator(
            name = "ingredient_sequence",
            sequenceName = "ingredient_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ingredient_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Recipe recipe;

    @NotNull
    @Size(max = 100)
    private String ingredient;

    @Size(max = 250)
    private String description;

    private double quantity;

    @Column(length = 5)
    private String unit;

    private BigDecimal unitPrice;
    private BigDecimal loss;
    private BigDecimal portionPrice;

    @Override
    public String toString() {
        return "Ingredient{" +
                "id = " + id +
                "recipe = " + recipe +
                "ingredient = " + ingredient +
                "description = " + description +
                "unit = " + unit +
                "unit_price = " + unitPrice +
                "loss = " + loss +
                "portion_pirce = " + portionPrice +
                "}";
    }
}
