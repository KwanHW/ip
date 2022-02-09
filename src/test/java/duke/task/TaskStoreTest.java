package duke.task;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import duke.parser.DukeException;

public class TaskStoreTest {
    @Test
    public void init_emptyTaskStore() {
        assertTrue(new TaskStore().getIsEmpty(), "Failed: Task Store not empty when initialised");
    }

    @Test
    public void noTasksOnDate() {
        TaskStore ts = new TaskStore();
        LocalDate date1 = LocalDate.parse("2022-01-31");
        LocalDate date2 = LocalDate.parse("2022-01-30");
        try {
            ts.addTask(new Deadline("Complete Assignment", date1));
            ts.addTask(new Event("Project Meeting", date2));
            assertEquals("You don't have any tasks on Jan 29 2022", ts.getTasksOn(LocalDate.parse("2022-01-29")),
                    "Failed: TaskStore contains tasks that do not fall on that date");
        } catch (DukeException e) {
            fail("There are matching dates in the file. Could be a seed file error");
        }
    }

    @Test
    public void noTasksOnDate_noTimeables() {
        TaskStore ts = new TaskStore();
        LocalDate date = LocalDate.parse("2022-01-31");
        try {
            ts.addTask(new Todo("Complete Assignment"));
            ts.addTask(new Todo("Project Meeting"));
            assertEquals("You don't have any tasks on Jan 29 2022", ts.getTasksOn(LocalDate.parse("2022-01-29")),
                    "Failed: TaskStore only contains non-timeable instances.");
        } catch (DukeException e) {
            fail("There are matching dates in the file. Could be a seed file error");
        }
    }

    @Test
    public void tasksOnDate() {
        TaskStore ts = new TaskStore();
        LocalDate date1 = LocalDate.parse("2022-01-31");
        LocalDate date2 = LocalDate.parse("2022-01-30");
        try {
            ts.addTask(new Deadline("Complete Assignment", date1));
            ts.addTask(new Event("Project Meeting", date2));
            String expected = "Here are your tasks on Jan 31 2022\n" + ts.getTask(0).toString() + "\n";
            assertEquals(expected, ts.getTasksOn(date1),
                    "Failed: TaskStore should output the 1 task that fall on that day.");
        } catch (DukeException e) {
            fail("There are matching dates in the file. Could be a seed file error");
        }
    }

}
