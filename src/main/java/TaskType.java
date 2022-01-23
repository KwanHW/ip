public enum TaskType {
    TODO("T"),
    EVENT("E"),
    DEADLINE("D");

    private final String type;

    TaskType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
