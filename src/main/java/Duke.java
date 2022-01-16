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
                    inputTxt = String.format("Nice! I marked this task as done:\n [%s] %s", task.getStatusIcon(), task);
                    break;

                case "unmark":
                    toDelete = Integer.valueOf(split[1]) - 1;
                    task = tasks[toDelete];
                    task.markAsUndone();
                    inputTxt = String.format("OK, I've marked this task as not done yet:\n [%s] %s", task.getStatusIcon(), task);
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
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < toIndex; i++) {
            sb.append(String.format("%d. [%s] %s",i+1, tasks[i].getStatusIcon() ,tasks[i]));

            if (i != toIndex - 1)  {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
