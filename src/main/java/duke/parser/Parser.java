package duke.parser;

import duke.io.Storage;
import duke.ui.Ui;
import duke.task.Task;
import duke.task.TaskStore;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    public static final String BYE = "bye";
    public static final String LIST = "list";
    public static final String MARK = "mark";
    public static final String UNMARK = "unmark";
    public static final String DELETE = "delete";
    public static final String MAKE_TODO = "todo";
    public static final String MAKE_EVENT = "event";
    public static final String MAKE_DEADLINE = "deadline";
    public static final String TASKS_ON = "taskon";
    public static final String FIND="find";

    private Ui ui;
    private Storage storage;

    public Parser(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
    }

    public void processInput(String inputTxt,TaskStore tasks) {
        String[] split = inputTxt.split(" ");
        String command = split[0].toLowerCase();
        String commandArgs = inputTxt.substring(command.length()).trim();
        Task task;
        try {
            switch (command) {
                case BYE:
                    this.ui.bye();
                    break;

                case LIST:
                    this.ui.printMessage(tasks.toString());
                    break;

                case MARK:
                    task = validateMutation(command,commandArgs,tasks);
                    task.markAsDone();
                    this.ui.printTaskMarking(task);
                    break;

                case UNMARK:
                    task = validateMutation(command,commandArgs,tasks);
                    task.markAsUndone();
                    this.ui.printTaskMarking(task);
                    break;

                case DELETE:
                    task = validateMutation(command,commandArgs,tasks);
                    tasks.removeTask(task);
                    this.ui.printTaskDelete(task,tasks);
                    break;

                case MAKE_DEADLINE:
                case MAKE_EVENT:
                case MAKE_TODO:
                    task = tasks.addTask(command,commandArgs);
                    this.ui.printTaskAdd(task,tasks);
                    break;

                case TASKS_ON:
                    LocalDate date = LocalDate.parse(commandArgs);
                    this.ui.printMessage(tasks.getTasksOn(date));
                    break;

                case FIND:
                    if (commandArgs.equals("")) {
                        throw new DukeException("Please enter a keyword for me to find.");
                    }
                    this.ui.printMessage(tasks.getTaskWithKeyword(commandArgs));
                    break;

                default:
                    throw new DukeException("I'm sorry, but I don't know what that means :-(");
            }

//            Write the new changes to file (commands that are not bye, list and taskon)
            if (!(command.equals(BYE) || command.equals(LIST) || command.equals(TASKS_ON) || command.equals(FIND))){
                this.storage.writeToFile(tasks);
            }

        } catch (DukeException e) {
            this.ui.printError(e.getMessage());
        } catch (NumberFormatException e) {
            this.ui.printError("I don't think you gave me a valid number.");
        } catch (IndexOutOfBoundsException e) {
            this.ui.printError("I think you may have given me something that's out of range.");
        } catch (IOException e) {
            this.ui.printError("Unable to write to file");
        } catch (DateTimeParseException e) {
            this.ui.printError("Sorry I don't understand that format. Make sure its in yyyy-mm-dd.");
        }
    }

    public static Task validateMutation(String command,String commandArgs,TaskStore tasks) throws DukeException, NumberFormatException, IndexOutOfBoundsException{
        if (tasks.getIsEmpty()) {
            throw new DukeException("Please make sure you have something in the list before performing this operation!");
        }

        if (commandArgs.isEmpty()) {
            throw new DukeException(String.format("Please make sure your command follows this format: %s <number>",command));
        }

        int toMark = Integer.parseInt(commandArgs) - 1;
        return tasks.getTask(toMark);
    }

    public static String getDelimiter(String action) throws DukeException {
        switch (action) {
            case MAKE_DEADLINE:
                return " /by ";
            case MAKE_EVENT:
                return " /at ";
            default:
                throw new DukeException("I'm sorry, but I don't think there's a delimiter for that..");
        }
    }
}
