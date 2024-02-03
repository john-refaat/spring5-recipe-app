package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author john
 * @since 01/02/2024
 */
@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand  {

    private Long id;
    private String name;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;

}
