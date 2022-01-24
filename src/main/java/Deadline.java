import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task implements Timeable{
    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        this(description,false,by);
    }

    public Deadline(String description, boolean isDone, LocalDate by) {
        super(TaskType.DEADLINE,isDone, description);
        this.by = by;
    }

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
    public String toString() {
        return String.format("%s (by: %s)",super.toString(),this.getDateString(Timeable.getPrintableFormat()));
    }
}
