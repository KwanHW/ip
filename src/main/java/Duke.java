import java.util.Arrays;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int index = 0;

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
        boolean canContinue = true;
        while (canContinue) {
            inputTxt = input.nextLine();
            String[] split = inputTxt.split(" ");

            int toDelete;
            Task task;
            switch (split[0]) {
                case "bye":
                    inputTxt = "Bye. Hope to see you again soon!";
                    canContinue = false;
                    break;

                case "list":
                    inputTxt = listTasks(tasks,index);
                    break;

                case "mark":
                    toDelete = Integer.valueOf(split[1]) - 1;
                    task = tasks[toDelete];
                    task.markAsDone();
                    inputTxt = String.format("Nice! I marked this task as done:\n %s", task);
                    break;

                case "deadline":
                case "event":
                case "todo":
                    String[] taskDetails = processTask(split);
                    task = buildTask(split[0],taskDetails);
                    tasks[index]  = task;
                    index++;
                    inputTxt = String.format("Got it. I've added this task:\n\t %s\n Now you have %d tasks in the list",task,index);
                    break;

                case "unmark":
                    toDelete = Integer.valueOf(split[1]) - 1;
                    task = tasks[toDelete];
                    task.markAsUndone();
                    inputTxt = String.format("OK, I've marked this task as not done yet:\n %s", task);
                    break;

                default:
                    tasks[index] = new Task(inputTxt);
                    index++;
                    inputTxt = String.format("added: %s",inputTxt);
                    break;
            }

            System.out.println("____________________________________________________________");
            System.out.println(inputTxt);
            System.out.println("____________________________________________________________");
        }
    }

    public static String listTasks(Task[] tasks, int toIndex) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < toIndex; i++) {
            sb.append(String.format("\n%d.%s",i+1,tasks[i]));

//            if (i != toIndex - 1)  {
//                sb.append("\n");
//            }
        }

        return sb.toString();
    }

    public static String[] processTask(String[] input) {
        int index = 1;

        String delimiter = getDelimiter(input[0]);
        if (!delimiter.equals("")) {
            for (int i = 1; i < input.length; i++) {
                if (input[i].equals(delimiter)) {
                    index = i;
                    break;
                }
            }

            String description = String.join(" ",Arrays.copyOfRange(input,1,index));
            String time = String.join(" ",Arrays.copyOfRange(input,index+1,input.length));

            String[] processedTask = {description,time};
            return processedTask;
        } else {
            String[] processedTask = {String.join(" ",Arrays.copyOfRange(input,1,input.length)) };
           return processedTask;
        }

    }

    public static String getDelimiter(String action) {
        switch (action) {
            case "deadline":
                return "/by";
            case "event":
                return "/at";
            default:
                return "";
        }
    }

    public static Task buildTask(String action, String[] input) {
        switch (action) {
            case "deadline":
                return new Deadline(input[0],input[1]);
            case "event":
                return new Event(input[0],input[1]);
            default:
                return new Todo(input[0]);
        }
    }
}
