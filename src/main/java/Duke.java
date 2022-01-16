import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

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

            if (inputTxt.equals("bye")) {
                inputTxt = "Bye. Hope to see you again soon!";
                canContinue = false;
            }

            System.out.println("____________________________________________________________");
            System.out.println(inputTxt);
            System.out.println("____________________________________________________________");
        }
    }
}
