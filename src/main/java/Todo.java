public class Todo extends Task {
    public Todo(String description) {
        this(description,false);
    }

    public Todo(String description, boolean isDone) {
        super(TaskType.TODO,isDone,description);
    }
}
