import java.time.LocalDate;
import java.util.ArrayList;

public class TaskStore {
    private ArrayList<Task> tasks;

    public TaskStore() {
        this.tasks = new ArrayList<>(100);
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    public int getSize() {
        return this.tasks.size();
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

    public void addTask(Task t) {
        this.tasks.add(t);
    }

    public void removeTask(Task t)  {
        this.tasks.remove(t);
    }

    public ArrayList<Task> getTasksOn(LocalDate date) {
        ArrayList<Task> tasksOnDate = new ArrayList<>();
        for (Task t : this.tasks) {
//            Finds tasks that have a time
            if (t instanceof Timeable) {
                Timeable timeableTask = (Timeable) t;

//                Checks if the date is the same as input
                if (timeableTask.isSameDate(date)) {
                    tasksOnDate.add(t);
                }
            }
        }

        return tasksOnDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.format("\n%d.%s", i + 1, tasks.get(i)));
        }

        return sb.toString();
    }
}
