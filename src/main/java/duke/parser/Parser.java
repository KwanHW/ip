package duke.parser;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskStore;
import duke.ui.Ui;

/**
 * Represents the logic which would make sense of the user's commands.
 */
public class Parser {
    public static final String EMPTY_LIST_ERROR =
            "Please make sure you have something in the list before performing this operation!";
    public static final String BYE = "bye";
    public static final String LIST = "list";
    public static final String MARK = "mark";
    public static final String UNMARK = "unmark";
    public static final String DELETE = "delete";
    public static final String MAKE_TODO = "todo";
    public static final String MAKE_EVENT = "event";
    public static final String MAKE_DEADLINE = "deadline";
    public static final String TASKS_ON = "taskon";
    public static final String FIND = "find";

    /**
     * Processes the raw input from the user and performs the action on the task store. If the executed action
     * encounters an error, it will display the error message.
     *
     * @param inputTxt The raw input containing the command and parameters.
     * @param tasks    The task store which the user wishes to query or update.
     * @return A boolean which determines if the input was successfully processed
     */
    public boolean processInput(String inputTxt, Ui ui, TaskStore tasks) {
        String[] split = inputTxt.split(" ");
        String command = split[0].toLowerCase();
        String commandArgs = inputTxt.substring(command.length()).trim();

        Task task;
        try {
            validateCommand(command, commandArgs, tasks);

            switch (command) {
            case BYE:
                ui.bye();
                System.exit(0);
                break;

            case LIST:
                if (tasks.getIsEmpty()) {
                    ui.printMessage("There are no tasks in your list.");
                } else {
                    ui.printMessage(tasks.toString());
                }
                break;

            case MARK:
                //Fallthrough
            case UNMARK:
                boolean isMarkCommand = command.equals(MARK);

                if (commandArgs.matches("all")) {
                    tasks.markAllTasks(isMarkCommand);
                    String markAction = isMarkCommand ? "Marked" : "Unmarked";
                    String message = String.format("%s all tasks.", markAction);
                    ui.printMessage(message);
                } else {
                    ArrayList<Task> markedTasks = tasks.markTasks(commandArgs.split(" "), isMarkCommand);
                    ui.printTaskMarking(markedTasks, isMarkCommand);
                }
                break;

            case DELETE:
                if (commandArgs.matches("all")) {
                    tasks.removeAllTasks();
                    ui.printMessage("Deleted all tasks.");
                } else {
                    ArrayList<Task> deletedTasks = tasks.removeTasks(commandArgs.split(" "));
                    ui.printTaskDelete(deletedTasks, tasks);
                }
                break;

            case MAKE_DEADLINE:
                // Fallthrough
            case MAKE_EVENT:
                // Fallthrough
            case MAKE_TODO:
                task = tasks.addTask(command, commandArgs);
                ui.printTaskAdd(task, tasks);
                break;

            case TASKS_ON:
                LocalDate date = LocalDate.parse(commandArgs);
                ui.printMessage(tasks.getTasksOn(date));
                break;

            case FIND:
                ui.printMessage(tasks.getTaskWithKeyword(commandArgs));
                break;

            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
            }

            return true;
        } catch (DukeException e) {
            ui.printError(e.getMessage());
        } catch (NumberFormatException e) {
            ui.printError("I don't think you gave me a valid number.");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("I think you may have given me something that's out of range.");
        } catch (DateTimeParseException e) {
            ui.printError("Sorry I don't understand that format. Make sure its in yyyy-mm-dd.");
        }

        return false;
    }

    /**
     * Validates the input command, arguments and taskStore before executing the function. If the command is "bye" or
     * "list", the validation is skipped.
     * <br>
     * In general, the check ensures that the command and arguments are non-empty and the task store having at least
     * 1 item. Should any of these requirements fail, an exception is thrown.
     *
     * @param command     The action which the user wishes to execute.
     * @param commandArgs The parameters passed to support the action
     * @param tasks       The task store which the user wishes to update
     // * @return The updated task from <code>tasks</code>.
     * @throws DukeException             If there is a syntax error in the command or <code>tasks</code> does not have
     *                                   any tasks.
     // * @throws NumberFormatException     If the provided parameters is not a number.
     * @throws IndexOutOfBoundsException If the provided index exceeds the size of <code>tasks</code> or the index < 0.
     */
    public static void validateCommand(String command, String commandArgs, TaskStore tasks) throws DukeException,
            NumberFormatException, IndexOutOfBoundsException {

        if (command.equals(BYE) || command.equals(LIST)) {
            return;
        }

        if (tasks.getIsEmpty()) {
            throw new DukeException(EMPTY_LIST_ERROR);
        }

        if (commandArgs.isEmpty()) {
            throw new DukeException(String.format("The command %s should have arguments", command));
        }
    }

    /**
     * Obtains a delimiter based on the given action. DukeException will be thrown if the command does not have a
     * delimiter.
     *
     * @param action The command that was given by the user.
     * @return The mapped delimiter for that particular command.
     * @throws DukeException If the command does not have a mapped delimiter.
     */
    public static String getDelimiter(String action) throws DukeException {
        switch (action) {
        case MAKE_DEADLINE:
            return Deadline.DELIMITER;
        case MAKE_EVENT:
            return Event.DELIMITER;
        default:
            throw new DukeException("I'm sorry, but I don't think there's a delimiter for that..");
        }
    }
}
