package guru.springframework.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author john
 * @since 01/02/2024
 */
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String name;
}
