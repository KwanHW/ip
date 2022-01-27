package duke.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskStoreTest {
    @Test
    public void init_emptyTaskStore() {
        assertTrue(new TaskStore().getIsEmpty(), "Failed: Task Store not empty when initialised");
    }

    @Test
    public void noTasksOnDate() {
        TaskStore ts = new TaskStore();
        LocalDate date = LocalDate.parse("2022-01-31");
        ts.addTask(new Deadline("Complete Assignment", date));
        ts.addTask(new Event("Project Meeting", date));
        assertEquals("You don't have any tasks on Jan 29 2022", ts.getTasksOn(LocalDate.parse("2022-01-29")), "Failed: TaskStore contains tasks that do not fall on that date");
    }

    @Test
    public void noTasksOnDate_noTimeables() {
        TaskStore ts = new TaskStore();
        LocalDate date = LocalDate.parse("2022-01-31");
        ts.addTask(new Todo("Complete Assignment"));
        ts.addTask(new Todo("Project Meeting"));
        assertEquals("You don't have any tasks on Jan 29 2022", ts.getTasksOn(LocalDate.parse("2022-01-29")), "Failed: TaskStore only contains non-timeable instances.");
    }

    @Test
    public void tasksOnDate() {
        TaskStore ts = new TaskStore();
        LocalDate date = LocalDate.parse("2022-01-29");
        ts.addTask(new Deadline("Complete Assignment", date));
        ts.addTask(new Event("Project Meeting", date));
        String expected = "Here are your tasks on Jan 29 2022\n" + ts.getTask(0).toString() + "\n" + ts.getTask(1).toString() + "\n";
        assertEquals(expected, ts.getTasksOn(date), "Failed: TaskStore should output the 2 tasks that fall on that day.");
    }

}
