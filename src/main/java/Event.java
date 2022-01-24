public class Event extends Task{
    protected String at;

    public Event(String description, String at) {
        this(description,false, at);
    }

    public Event(String description,boolean isDone, String at ) {
        super(TaskType.EVENT,isDone,description);
        this.at = at;
    }

    @Override
    public String writeToFile() {
        return super.writeToFile() + " | " + this.at;
    }

    @Override
    public String toString() {
        return String.format("%s (at: %s)",super.toString(),this.at);
    }
}
