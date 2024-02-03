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
class NotesCommandToNotesTest {

    private final Long NOTES_ID = new Long(1L);
    private final String NOTES = "test";

    private NotesCommandToNotes notesCommandToNotes;


    @BeforeEach
    void setUp() {
        notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    void testNullObject() {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }

    @Test
    void convert() {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        notesCommand.setRecipeNotes(NOTES);
        Notes notes = notesCommandToNotes.convert(notesCommand);
        assertNotNull(notes);
        assertEquals(NOTES_ID, notes.getId());
        assertEquals(NOTES, notes.getContent());
    }
}