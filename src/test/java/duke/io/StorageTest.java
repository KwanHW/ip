package duke.io;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import duke.parser.DukeException;
import duke.task.Deadline;
import duke.task.TaskStore;
import duke.task.Todo;

public class StorageTest {

    @BeforeAll
    public static void deleteFilesAndDirectory() {
        File f = new File(Storage.PATH);
        f.delete();
        f.getParentFile().delete();
    }

    @Test
    public void importTasksNoDirNoFile() throws IOException, DateTimeParseException {
        try {
            assertEquals(0, new Storage().importTasks().getSize(),
                    "Failed: Importing non-existent file supposed to be empty (size 0).");
        } catch (DukeException e) {
            fail("Failed: Clashing dates for task creation");
        }
    }

    @Test
    public void importTasksEmptyFile() throws IOException, DateTimeParseException {
        try {
            assertEquals(0, new Storage().importTasks().getSize(),
                    "Failed: Importing task file (empty file) supposed to be empty (size 0).");
        } catch (DukeException e) {
            fail("Failed: Clashing dates for task creation");
        }
    }

    @Test
    public void importTasksFileHasContent() throws IOException, DateTimeParseException {
        TaskStore ts = new TaskStore();
        try {
            ts.addTask(new Todo("read book"));
            ts.addTask(new Deadline("return book", LocalDate.parse("2022-01-31")));
            new Storage().writeToFile(ts);

            assertEquals(2, new Storage().importTasks().getSize(),
                    "Failed: Importing task file with content supposed to have 2 tasks.");
        } catch (DukeException e) {
            fail("Failed: Clashing dates for task creation");
        }
    }
}
