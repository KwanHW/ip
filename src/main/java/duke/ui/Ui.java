package duke.ui;

import duke.task.Task;
import duke.task.TaskStore;

/**
 * Represents an interface which will display messages to the user based on the inputs.
 */
public class Ui {
    public static final String TASK_MARKED = "Nice! I marked this task as done:\n %s";
    public static final String TASK_UNMARKED = "OK, I've marked this task as not done yet:\n %s";
    public static final String TASK_DELETE = "Noted. I've removed this task:\n\t %s\n";
    public static final String TASK_ADD = "Got it. I've added this task:\n\t %s\n";
    public static final String TASKS_SIZE = "Now you have %d tasks in the list";

    public Ui() {

    }

    public void greet() {
        String logo = "                _     _       \n"
                + "               | |   | |      \n"
                + " __      ____ _| | __| | ___  \n"
                + " \\ \\ /\\ / / _` | |/ _` |/ _ \\ \n"
                + "  \\ V  V / (_| | | (_| | (_) |\n"
                + "   \\_/\\_/ \\__,_|_|\\__,_|\\___/ \n";
        System.out.println("Hello from\n" + logo);


        this.printMessage("Hello! I'm Waldo\nWhat can I do for you?");
    }

    public void printMessage(String inputTxt) {
        System.out.println("____________________________________________________________");
        System.out.println(inputTxt);
        System.out.println("____________________________________________________________");
    }

    public String getTaskSizeString(TaskStore tasks) {
        return String.format(TASKS_SIZE, tasks.getSize());
    }

    public void printError(String errorMsg) {
        this.printMessage(String.format("☹ OOPS!!! %s", errorMsg));
    }

    public void printTaskAdd(Task task, TaskStore tasks) {
        String template = TASK_ADD + this.getTaskSizeString(tasks);
        this.printMessage(String.format(template, task, tasks.getSize()));
    }

    public void printTaskMarking(Task t) {
        if (t.getIsDone()) {
            this.printMessage(String.format(TASK_MARKED, t));
        } else {
            this.printMessage(String.format(TASK_UNMARKED, t));
        }
    }

    public void printTaskDelete(Task task, TaskStore tasks) {
        String template = TASK_DELETE + this.getTaskSizeString(tasks);
        this.printMessage(String.format(template, task, tasks.getSize()));
    }

    public void bye() {
        this.printMessage("Bye. Hope to see you again soon!");
    }
}
