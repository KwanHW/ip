import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public Task addTask(String command, String args) throws DukeException, DateTimeParseException{
        Task t = this.createTask(command,args);
        this.tasks.add(t);
        return t;
    }

    public void addTask(Task t) {
        this.tasks.add(t);
    }

    public void removeTask(Task t)  {
        this.tasks.remove(t);
    }

    public String getTasksOn(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        for (Task t : this.tasks) {
//            Find all timeable tasks
            if (t instanceof Timeable) {
                Timeable timeableTask = (Timeable) t;

//                Checks if the date is the same as input
                if (timeableTask.isSameDate(date)) {
                    sb.append(t).append("\n");
                }
            }
        }

        String tasksToPrint = sb.toString();
        String dateString = date.format(Timeable.getPrintableFormat());

        if (tasksToPrint.isEmpty()) {
            return String.format("You don't have any tasks on %s",dateString);
        } else {
            return String.format("Here are your tasks on%s\n%s",dateString,tasksToPrint);
        }
    }

    public Task createTask(String command, String args) throws DukeException, DateTimeParseException {
        if (command.equals(Parser.MAKE_TODO)) {
            if (args.equals("")) {
                throw new DukeException("Make sure the task is not empty!");
            }
            return new Todo(args);
        } else {
            String delimiter = Parser.getDelimiter(command);
            String[] taskParams = args.split(delimiter);

//            Checks for syntax correctness
            if (taskParams.length == 1) {
                String errorMsg = String.format("Make sure your command follows this format: %s <task>%s<time>",command,delimiter);
                throw new DukeException(errorMsg);
            }

            LocalDate date = Timeable.of(taskParams[1]);

//            At this point it can only be a deadline or an event
            if (command.equals(Parser.MAKE_DEADLINE)) {
                return new Deadline(taskParams[0], date);
            } else {
                return new Event(taskParams[0], date);
            }
        }
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
