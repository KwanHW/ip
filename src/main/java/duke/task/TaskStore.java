package duke.task;

import duke.parser.DukeException;
import duke.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Represents a collection of the tasks stored in the program.
 */
public class TaskStore {
    private ArrayList<Task> tasks;

    public TaskStore() {
        this.tasks = new ArrayList<>(100);
    }

    public boolean getIsEmpty() {
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

    /**
     * Adds a new task to the <code>TaskStore</code>. Once complete, it will return the task that has been added.
     * @param command Type of task to create.
     * @param args The entire sting of parameters to create the task (e.g. task description or time of task).
     * @return The created <code>Task</code>.
     * @throws DukeException If there is an error creating the task due to syntax error (i.e. missing parameters).
     * @throws DateTimeParseException If the time parameter given cannot be parsed (Applies to <code>Timeable</code> tasks).
     */
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

    /**
     * Finds all tasks in the <code>TaskStore</code> that falls on the given date.
     * If there are no tasks that fall on that date, a message saying that there are no tasks will be sent instead.
     * @param date The selected date to search.
     * @return A complete message of the tasks or a message saying that there are no tasks that fall on the given date
     */
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
            return String.format("Here are your tasks on %s\n%s",dateString,tasksToPrint);
        }
    }

    /**
     * Search the <code>TaskStore</code> and generates the task information if the keyword is in the description.
     * Returns a message if there are no matching tasks in the list.
     * @param keyword Keyword to search in all task descriptions.
     * @return A message containing all the task information that matches the keyword.
     */
    public String getTaskWithKeyword(String keyword) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (Task t : this.tasks) {
            if (t.getDescription().contains(keyword)) {
//              Task Format: <count>.<task>\n
                sb.append(String.format("%d.",count++)).append(t).append("\n") ;
            }
        }

        String tasksToPrint = sb.toString();
        if (tasksToPrint.isEmpty()) {
            return "There are no matching tasks in your list";
        } else {
           return String.format("Here are the matching tasks in your list:\n%s",tasksToPrint);
        }
    }

    /**
     * Creates a <code>Task</code> with a given command and its arguments.
     * @param command The type of task to create.
     * @param args The respective arguments required to create the task.
     * @return The created <code>Task</code> specified by the arguments.
     * @throws DukeException If there is an error creating the task due to syntax error (i.e. missing parameters).
     * @throws DateTimeParseException If the supplied date in args cannot be parsed as a <code>LocalDate</code> instance.
     */
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
