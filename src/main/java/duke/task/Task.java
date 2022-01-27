package duke.task;

/**
 * Represents a general task which the user has inserted.
 */
public class Task {
    private String description;
    private boolean isDone;
    private TaskType taskType;

    public Task(TaskType type, String description) {
        this(type,false,description);
    }

    public Task(TaskType type, boolean isDone, String description) {
        this.description = description;
        this.isDone = isDone;
        this.taskType = type;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public String writeToFile() {
        String isDone = this.isDone ? "1" : "0";
        return String.join(" | ", this.taskType.toString(), isDone, this.description);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s",this.taskType.toString() ,this.getStatusIcon(),this.description);
    }
}
