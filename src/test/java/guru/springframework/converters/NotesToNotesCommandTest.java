package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 02/02/2024
 */
class NotesToNotesCommandTest {

    private final Long NOTES_ID = new Long(1L);
    private final String NOTES = "some notes";
    private NotesToNotesCommand notesToNotesCommand;

    @BeforeEach
    void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void testNullObject() {
        assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        notes.setContent(NOTES);
        NotesCommand notesCommand = notesToNotesCommand.convert(notes);
        assertNotNull(notesCommand);
        assertEquals(NOTES_ID, notesCommand.getId());
        assertEquals(NOTES, notesCommand.getRecipeNotes());
    }
}