import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] tasks = new String[100];
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

            switch (inputTxt) {
                case "bye":
                    inputTxt = "Bye. Hope to see you again soon!";
                    canContinue = false;
                    break;

                case "list":
                    inputTxt = listTasks(tasks,index);
                    break;

                default:
                    tasks[index] = inputTxt;
                    index++;
                    inputTxt = String.format("added: %s",inputTxt);
                    break;
            }

            System.out.println("____________________________________________________________");
            System.out.println(inputTxt);
            System.out.println("____________________________________________________________");
        }
    }

    public static String listTasks(String[] tasks, int toIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < toIndex; i++) {
            sb.append(String.format("%d. %s",i+1,tasks[i]));

            if (i != toIndex - 1)  {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
