package guru.springframework.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author john
 * @since 01/02/2024
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class NotesCommand {
    private Long id;
    private String recipeNotes;

}
