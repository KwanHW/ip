import java.util.Scanner;

public class Duke {
    public static final String BYE = "bye";
    public static final String LIST = "list";
    public static final String MARK = "mark";
    public static final String UNMARK = "unmark";
    public static final String MAKE_TODO = "todo";
    public static final String MAKE_EVENT = "event";
    public static final String MAKE_DEADLINE = "deadline";

    private static Task[] tasks;
    private static int index;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        init();

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Duke\nWhat can I do for you?");
        System.out.println("____________________________________________________________");

        String inputTxt;
        while (input.hasNext()) {
            inputTxt = input.nextLine();
            processInput(inputTxt);

            if (inputTxt.startsWith("bye")) {
                break;
            }
        }
    }

    private static void init() {
        index = 0;
        tasks = new Task[100];
    }

    public static void processInput(String inputTxt) {
        String[] split = inputTxt.split(" ");
        String command = split[0].toLowerCase();
        String commandArgs = inputTxt.substring(command.length()).trim();
        int toDelete;
        Task task;
        try {
            switch (command) {
                case BYE:
                    printMessage("Bye. Hope to see you again soon!");
                    break;

                case LIST:
                    printMessage(listTasks(tasks, index));
                    break;

                case MARK:
                    if (index == 0) {
                        throw new DukeException("☹ OOPS!!! Please make sure you have something in the list before marking a task as done!");
                    }

                    if (split.length == 1) {
                        throw new DukeException("☹ OOPS!!! Please make sure you did specify which item I should be marking as done.");
                    }
                    toDelete = Integer.valueOf(commandArgs) - 1;
                    task = tasks[toDelete];
                    task.markAsDone();
                    printMessage( String.format("Nice! I marked this task as done:\n %s", task));
                    break;

                case UNMARK:
                    if (index == 0) {
                        throw new DukeException("☹ OOPS!!! Please make sure you have something in the list before marking a task as undone!");
                    }

                    if (split.length == 1) {
                        throw new DukeException("☹ OOPS!!! Please make sure you did specify which item I should be marking as undone.");
                    }
                    toDelete = Integer.valueOf(commandArgs) - 1;
                    task = tasks[toDelete];
                    task.markAsUndone();
                    printMessage( String.format("OK, I've marked this task as not done yet:\n %s", task));
                    break;

                case MAKE_DEADLINE:
                case MAKE_EVENT:
                case MAKE_TODO:
                    task = processTask(command, commandArgs);
                    tasks[index] = task;
                    index++;
                    printMessage(String.format("Got it. I've added this task:\n\t %s\n Now you have %d tasks in the list",
                            task,
                            index));
                    break;

                default:
                    throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            printMessage(e.getMessage());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            printMessage("☹ OOPS!!! I don't think you gave me a valid number.");
        } catch (NullPointerException e) {
            printMessage("☹ OOPS!!! I think you may have given me something that's out of range.");
        }
    }

    public static String listTasks(Task[] tasks, int toIndex) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < toIndex; i++) {
            sb.append(String.format("\n%d.%s", i + 1, tasks[i]));
        }

        return sb.toString();
    }

    public static Task processTask(String command, String args) throws DukeException {
        if (command.equals(MAKE_TODO)) {
            if (args.equals("")) {
                throw new DukeException("☹ OOPS!!! Make sure the task is not empty!");
            }
            return new Todo(args);
        } else {
            String delimiter = getDelimiter(command);
            String[] taskParams = args.split(delimiter);

//            Checks for syntax correctness
            if (taskParams.length == 1) {
                String errorMsg = String.format("☹ OOPS!!! Make sure your command follows this format: %s <task>%s<time>",command,delimiter);
                throw new DukeException(errorMsg);
            }

//            At this point it can only be a deadline or an event
            if (command.equals(MAKE_DEADLINE)) {
                return new Deadline(taskParams[0], taskParams[1]);
            } else {
                return new Event(taskParams[0], taskParams[1]);
            }
        }
    }

    public static String getDelimiter(String action) throws DukeException {
        switch (action) {
            case MAKE_DEADLINE:
                return " /by ";
            case MAKE_EVENT:
                return " /at ";
            default:
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't think there's a delimiter for that..");
        }
    }

    public static void printMessage(String inputTxt) {
        System.out.println("____________________________________________________________");
        System.out.println(inputTxt);
        System.out.println("____________________________________________________________");
    }
}
