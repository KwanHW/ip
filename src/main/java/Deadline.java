public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        this(description,false,by);
    }

    public Deadline(String description, boolean isDone, String by) {
        super(TaskType.DEADLINE,isDone, description);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)",super.toString(),this.by);
    }
}
