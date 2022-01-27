package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that has to be completed before a certain date.
 */
public class Deadline extends Task implements Timeable{
    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        this(description,false,by);
    }

    public Deadline(String description, boolean isDone, LocalDate by) {
        super(TaskType.DEADLINE,isDone, description);
        this.by = by;
    }

    /**
     * Generates a writable format of the deadline. The format is as such:
     * <br>
     * D | 1 | Description of task | Deadline of task (in yyyy-mm-dd)
     * <br>
     * Note that the second column represents (isDone) and is denoted as 1 for done and 0 for not done.
     * @return The writable format of the task.
     */
    @Override
    public String writeToFile() {
        return super.writeToFile() + " | " + this.getDateString(Timeable.getWritableFormat());
    }

    @Override
    public LocalDate getDate() {
        return this.by;
    }

    @Override
    public String getDateString(DateTimeFormatter dateTimeFormat) {
        return this.by.format(dateTimeFormat);
    }

    @Override
    public boolean isSameDate(LocalDate date) {
        return this.by.equals(date);
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)",super.toString(),this.getDateString(Timeable.getPrintableFormat()));
    }
}
