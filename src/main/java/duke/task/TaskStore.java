package duke.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;

import duke.parser.DukeException;
import duke.parser.Parser;

/**
 * Represents a collection of the tasks stored in the program.
 */
public class TaskStore {
    private final ArrayList<Task> tasks;

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
     *
     * @param command Type of task to create.
     * @param args    The entire sting of parameters to create the task (e.g. task description or time of task).
     * @return The created <code>Task</code>.
     * @throws DukeException          If there is an error creating the task due to syntax error (i.e. missing
     *                                parameters).
     * @throws DateTimeParseException If the time parameter given cannot be parsed (Applies to <code>Timeable</code>
     *                                tasks).
     */
    public Task addTask(String command, String args) throws DukeException, DateTimeParseException {
        Task t = this.createTask(command, args);
        assert t != null : "Assertion failed on TaskStore.addTask(): Task should be created";

        this.addTask(t);
        return t;
    }

    /**
     * Adds task to tasklist. If the task is a <code>Timeable</code>, it will check if there are clashing dates first
     * and an DukeException is thrown if there is.
     * @param t The task to add to the tasklist
     * @throws DukeException If the task is a <code>Timeable</code> and it clashes with an existing task in the list
     */
    public void addTask(Task t) throws DukeException {
        if (t instanceof Timeable) {
            if (this.hasClashingDate((Timeable) t)) {
                throw new DukeException("There is a task that falls on this day.");
            }
        }

        this.tasks.add(t);
    }

    /**
     * Removes the tasks specified in the arguments. If an argument is invalid (not a number or not in range), it will
     * be skipped.
     * @param args The arguments in the form of a String array.
     * @return The tasks that were deleted.
     */
    public ArrayList<Task> removeTasks(String[] args) {
        ArrayList<Integer> indexList = this.getIndices(args);

        // Sorts the indices in descending order to prevent false index deletion
        indexList.sort(Collections.reverseOrder());

        ArrayList<Task> deletedTasks = new ArrayList<>();
        for (int index: indexList) {
            Task deletedTask = this.tasks.remove(index);
            deletedTasks.add(deletedTask);
        }

        return deletedTasks;
    }

    public void removeAllTasks() {
        this.tasks.clear();
    }

    public void markAllTasks(boolean isDone) {
        this.tasks.forEach((Task t) -> t.markTask(isDone));
    }

    /**
     * Marks the specified tasks in args as done or undone depending on the command.
     * If an argument is invalid (not a number or not in range), it will be skipped.
     * @param args The arguments in the form of a String array.
     * @param isDone Indicator to mark the task as done or undone.
     * @return The tasks that were marked to be done or undone.
     */
    public ArrayList<Task> markTasks(String[] args, boolean isDone) {
        ArrayList<Integer> indexList = this.getIndices(args);

        ArrayList<Task> markedTasks = new ArrayList<>();
        for (int index: indexList) {
            Task markedTask = this.tasks.get(index);

            // Ignore marking if task is already done/not done
            if (markedTask.getIsDone() == isDone) {
                continue;
            }

            markedTask.markTask(isDone);
            markedTasks.add(markedTask);
        }

        return markedTasks;
    }

    /**
     * Finds all tasks in the <code>TaskStore</code> that falls on the given date. If there are no tasks that fall on
     * that date, a message saying that there are no tasks will be sent instead.
     *
     * @param date The selected date to search.
     * @return A complete message of the tasks or a message saying that there are no tasks that fall on the given date
     */
    public String getTasksOn(LocalDate date) {
        StringBuilder sb = new StringBuilder();
        for (Task t : this.tasks) {
            // Find all timeable tasks
            if (t instanceof Timeable) {
                Timeable timeableTask = (Timeable) t;

                // Checks if the date is the same as input
                if (timeableTask.isSameDate(date)) {
                    sb.append(t).append("\n");
                }
            }
        }

        String tasksToPrint = sb.toString();
        String dateString = date.format(Timeable.getPrintableFormat());

        if (tasksToPrint.isEmpty()) {
            return String.format("You don't have any tasks on %s", dateString);
        } else {
            return String.format("Here are your tasks on %s\n%s", dateString, tasksToPrint);
        }
    }

    /**
     * Checks the task list of there are any clashing dates with another task
     * @param t The timeable task to be checked
     * @return true if there exists a task where the dates are the same. Otherwise, return false.
     */
    public boolean hasClashingDate(Timeable t) {
        for (Task task : this.tasks) {
            if (task instanceof Timeable) {
                Timeable timeableTask = (Timeable) task;

                if (timeableTask.isSameDate(t.getDate())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Integer> getIndices(String[] args) {
        ArrayList<Integer> indexList = new ArrayList<>();

        for (String arg : args) {
            // Catch invalid numbers (non-digital characters or index out of range)
            try {
                int index = Integer.parseInt(arg) - 1;
                if (index < 0 || index >= this.tasks.size()) {
                    continue;
                }
                indexList.add(index);
            } catch (NumberFormatException e) {
                continue;
            }
        }

        return indexList;
    }

    /**
     * Searches the <code>TaskStore</code> for tasks that contains the keyword.
     * <br>
     * Returns a formatted message containing information of the tasks if there is at least 1 match. Else return
     * a message if there are no matching tasks in the list.
     *
     * @param keyword Keyword to search in all task descriptions.
     * @return A message containing all the task information that matches the keyword.
     */
    public String getTaskWithKeyword(String keyword) {
        StringBuilder sb = new StringBuilder();

        // Numbers list from 1
        int count = 1;
        for (Task t : this.tasks) {
            if (t.getDescription().contains(keyword)) {
                // Task Format: <count>.<task>\n
                sb.append(String.format("%d.", count++)).append(t).append("\n");
            }
        }

        String tasksToPrint = sb.toString();
        if (tasksToPrint.isEmpty()) {
            return "There are no matching tasks in your list";
        } else {
            return String.format("Here are the matching tasks in your list:\n%s", tasksToPrint);
        }
    }

    /**
     * Creates a <code>Task</code> with a given command and its arguments.
     *
     * @param command The type of task to create.
     * @param args    The respective arguments required to create the task.
     * @return The created <code>Task</code> specified by the arguments.
     * @throws DukeException          If there is an error creating the task due to syntax error (i.e. missing
     *                                parameters).
     * @throws DateTimeParseException If the supplied date in args cannot be parsed as a <code>LocalDate</code>
     *                                instance.
     */
    public Task createTask(String command, String args) throws DukeException, DateTimeParseException {
        if (command.equals(Parser.MAKE_TODO)) {
            if (args.isEmpty()) {
                throw new DukeException("Make sure the task is not empty!");
            }
            return new Todo(args);
        } else {
            String delimiter = Parser.getDelimiter(command);
            String[] taskParams = args.split(delimiter);

            // Checks for syntax correctness
            if (taskParams.length == 1) {
                String errorMsg = String.format("Make sure your command follows this format: %s <task>%s<time>",
                        command, delimiter);
                throw new DukeException(errorMsg);
            }

            LocalDate date = Timeable.of(taskParams[1]);

            // At this point it can only be a deadline or an event
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
