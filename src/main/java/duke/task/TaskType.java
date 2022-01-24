package duke.task;

public enum TaskType {
    TODO("T"),
    EVENT("E"),
    DEADLINE("D");

    private final String type;

    TaskType(String type) {
        this.type = type;
    }

    public static TaskType fromString(String from) {
        for (TaskType tt : TaskType.values())      {
            if (tt.toString().equals(from)) {
                return tt;
            }
        }

        throw new IllegalArgumentException("ERROR!!! No such TaskType exists");
    }

    @Override
    public String toString() {
        return this.type;
    }
}
