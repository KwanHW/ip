package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event that is happening on a certain date.
 */
public class Event extends Task implements Timeable{
    protected LocalDate at;

    public Event(String description, LocalDate at) {
        this(description,false, at);
    }

    public Event(String description,boolean isDone, LocalDate at ) {
        super(TaskType.EVENT,isDone,description);
        this.at = at;
    }

    /**
     * Generates a writable format of the deadline. The format is as such:
     * <br>
     * E | 1 | Description of task | Deadline of task (in yyyy-mm-dd)
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
        return this.at;
    }

    @Override
    public String getDateString(DateTimeFormatter dateTimeFormat) {
        return this.at.format(dateTimeFormat);
    }

    @Override
    public boolean isSameDate(LocalDate date) {
        return this.at.equals(date);
    }

    @Override
    public String toString() {
        return String.format("%s (at: %s)",super.toString(),this.getDateString(Timeable.getPrintableFormat()));
    }

}
